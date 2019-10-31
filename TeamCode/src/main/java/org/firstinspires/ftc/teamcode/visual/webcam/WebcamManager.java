package org.firstinspires.ftc.teamcode.visual.webcam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureRequest;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSequenceId;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSession;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraException;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraFrame;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.CameraManagerInternal;
import org.firstinspires.ftc.robotcore.internal.camera.RenumberedCameraFrame;
import org.firstinspires.ftc.robotcore.internal.camera.libuvc.api.UvcApiCameraFrame;
import org.firstinspires.ftc.robotcore.internal.camera.libuvc.nativeobject.UvcFrame;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class WebcamManager implements CameraCaptureSession.StateCallback {
    // A couple constants. Don't change unless you actually know what you're doing
    private final Size FRAME_SIZE = new Size(1920, 1080); // one of the supported frame sizes
    private final int FPS = 6;  // = max fps

    private boolean showsViews = false; // whether or not to show the views on the rc
    private Context appContext; // the app context (from the hardwareMap)

    private Camera camera; // the webcam
    private CameraManager cameraManager; // the manager of the webcam

    private CameraCaptureSession captureSession; // the capture session that gives us images

    // Option to start the capture with views shown on the RC (needs appContext (hardwareMap.appContext) to display the views)
    // ONLY do this if Vuforia is not in use. I'm not sure what that'll do since they use the same views.
    public void startCaptureWithViews(WebcamName webcam, Context appContext) {
        this.appContext = appContext;      // store the app context for later
        showsViews = true;                 // enable showing the views
        initViews();                       // initialize the views
        startCapture(webcam);              // start the webcam capture
    }


    /**
     * Start the webcam capture (if this is called directly, no views will be created on the RC)
     * @param webcam the WebcamName of the webcam to be used eg. hardwareMap.get(WebcamName.class, "")
     */
    public void startCapture(WebcamName webcam) {
        // Get the camera manager that can open webcams
        cameraManager = ClassFactory.getInstance().getCameraManager();

        // request permissions and open the camera (wait a max of 1 minute for permission using the webcam name provided)
        // continuation is null because we don't care about it (and I don't know how to use it)
        camera = cameraManager.requestPermissionAndOpenCamera(new Deadline(1, TimeUnit.MINUTES), webcam, null);

        try {
            // attempt to create a capture session. This could throw a CameraException
            // This is a bit complicated. Essentially we ask the camera manager for it's thread pool (the collection of threads it's currently running)
            // We then create a continuation (a callback that will be called on a thread from that thread pool) using ourself as the callback class
            // That's why we implement StateCallback
            captureSession = camera.createCaptureSession(Continuation.create(((CameraManagerInternal) cameraManager).getSerialThreadPool(), this));

        } catch (CameraException e) {
            RobotLog.ee("ERROR CREATING CAPTURE!", e.getMessage());
        }

    }


    /**
     * Stop the frame capture from the webcam. Must be called during stop() of op mode.
     */
    public void stopCapture()
    {
        // If the capture is not started, it could be null
        if(captureSession != null)
        {
            // We don't need to stop the capture because that's handled in onClosed below
            captureSession.close();        // close the capture (indicate to java we don't care about the data any more)
        }

        cleanUpViews(); // Clean up the views if necessary
    }



    /**** Camera Capture Session ****/

    // The bitmap in which to save the current frame. Must create it the same size as the frame recieved from the camera
    //   Use format ARGB_8888 because that's the only one supported for conversion from YUY2 format which is all the webcam supports.
    //   volatile means it can be edited from another thread and is not guaranteed to be the same as the code runs
    private volatile Bitmap currentFrame = Bitmap.createBitmap(FRAME_SIZE.getWidth(), FRAME_SIZE.getHeight(), Bitmap.Config.RGB_565);

    /**
     * Get the current frame
     * @return a copy of the current frame (since it's volatile and currentFrame could change as we're reading it later if we used it directly)
     */
    public Bitmap getCurrentFrame() {
        // Use ARGB_8888 to be consistent with the frame format
        // Doesn't need to be mutable since we're only ever going to read from it.
        return currentFrame.copy(Bitmap.Config.ARGB_8888, false);
    }

    /**
     * Notice this class implements CameraCaptureSession.StateCallback.
     * This will be called by the capture session once the capture session has been configured.
     * @param session the camera capture session (will be called by the Capture Session itself
     */
    @Override
    public void onConfigured(CameraCaptureSession session) {
        try {
            // Create a capture request for images in the android format YUY2 with the frame size FRAME_SIZE at the fps FPS
            // These parameters need to be compatible with the camera. Check with camera.getCameraName().getCameraCharacteristics()
            // For a list of conversions, see ImageFormatMapper
            // Can throw. (we've experienced this a lot)
            CameraCaptureRequest captureRequest = camera.createCaptureRequest(ImageFormat.YUY2, FRAME_SIZE, FPS);

            // Start the capture! (requires a capture request and a callback)
            // The callback is a CaptureCallback to be called when a new frame becomes available.
            // If you haven't seen this syntax before, you can define and instantiate an anonymous class that implements
            //   an interface or extends abstract class without a full file by putting the necessary code after the constructor
            session.startCapture(captureRequest, new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onNewFrame(CameraCaptureSession session, CameraCaptureRequest request, CameraFrame cameraFrame) {
                    // Copy the camera frame to the currentFrame bitmap for later (MUST BE OF ARGB_8888 format)
                    RobotLog.e("I'M ALIVE!~~!~!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    RobotLog.d(cameraFrame.getClass().getCanonicalName());
                    if(!(cameraFrame instanceof RenumberedCameraFrame))
                        return;
                    RobotLog.e("I'M PARTIALLY ALIVE!~~!~!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    try {
                        Field innerFrameField = RenumberedCameraFrame.class.getDeclaredField("innerFrame");
                        innerFrameField.setAccessible(true);
                        UvcApiCameraFrame uvcApiCameraFrame = (UvcApiCameraFrame)innerFrameField.get(cameraFrame);
                        Field uvcField = UvcApiCameraFrame.class.getDeclaredField("uvcFrame");
                        uvcField.setAccessible(true);
                        UvcFrame uvcFrame = (UvcFrame)uvcField.get(uvcApiCameraFrame);
                        ByteBuffer byteBuffer = uvcFrame.getImageByteBuffer();
                        currentFrame.copyPixelsFromBuffer(byteBuffer);

                    } catch (Exception e)
                    {
                        RobotLog.e("I'M DEAD!~~!~!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        RobotLog.e(e.getMessage());
                    }
                    RobotLog.e("I'M STILL ALIVE!~~!~!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                    // If we need to show the view,
                    if (showsViews)
                        showBitmap(cameraView, currentFrame); // then update it

                    // If we need to save the frames
                    if (shouldSaveFrames)
                        saveFrame(currentFrame); // then save it.
                }
            }, Continuation.create(((CameraManagerInternal) cameraManager).getSerialThreadPool(), new CameraCaptureSession.StatusCallback()
            {
                @Override public void onCaptureSequenceCompleted(CameraCaptureSession session, CameraCaptureSequenceId cameraCaptureSequenceId, long lastFrameNumber)
                {
                    // This is another Continuation using the same thread pool and another callback, this time once the session has been completed
                    // It will give us the sequenceId and the last frame number sent to the callback above.

                    // Print it out to the log
                    RobotLog.d("capture sequence %s reports completed: lastFrame=%d", cameraCaptureSequenceId, lastFrameNumber);
                }
            }));
        } catch (CameraException e) {
            // Another possible camera exception from creating the capture request
            RobotLog.e("ERROR CREATING CAPTURE!");
            RobotLog.e(e.getMessage());
        }

    }

    /**
     * Callback as a part of the StateCallback
     * Will be called when this callback is being closed.
     *
     * Simply stop the capture when we're being closed.
     * @param session the current session that's closing
     */
    @Override
    public void onClosed(CameraCaptureSession session) {
        session.stopCapture(); // Stop the capture
    }





    /**** Views ****/

    private ViewGroup parentView; // The parent view which holds the two image views
    private ImageView cameraView; // The ImageView which will hold the current camera frame
    private ImageView userView; // The ImageView which will hold a user-supplied camera frame

    /**
     * Initialize the views!
     * This is a bit complicated since I had to hack together a UI without the UI builder or any android UI experience.
     */
    private void initViews() {
        // First check if we're really supposed to be doing this.
        if (showsViews) {
            // UI commands can only be called from the UI thread.
            // Nasty errors ensue if you try from the normal thread.
            // So we submit a Runnable to be run on the UI thread.
            AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Get the parent View (this is the view Vuforia uses to display its preview)
                        // The activity (current UI "page" on the phone) could be null, so make sure it's not (Objects.requireNonNull) or throw an error (caught with the try)
                        // Find the view in the current activity by its id we can find from the hardwareMap's appContext.
                        // This is why we need the appContext to make the views
                        // The resource name is "cameraMonitorViewId", the same as with Vuforia
                        parentView = (ViewGroup) Objects.requireNonNull(AppUtil.getInstance().getActivity()).findViewById(appContext.getResources().getIdentifier("cameraMonitorViewId", "id", appContext.getPackageName()));

                        RobotLog.d("Found Parent View!" + parentView.toString()); // logging

                        // Create an image view in the context of this application
                        //  (I'm not sure why android makes me say it's for this application because um, if I'm making
                        //  it in this application it's probably for this application, but oh well
                        cameraView = new ImageView(AppUtil.getInstance().getApplication().getApplicationContext());

                        // Create an empty (green) bitmap as a placeholder until actual frames are inserted into the views
                        // I arbitrarily chose 1000x600 pixels because it seemed to fit.
                        // Make sure you change it elsewhere (in showBitmap) if it's changed here.
                        // Use ARGB_8888 for consistency
                        Bitmap image = Bitmap.createBitmap(1000, 600, Bitmap.Config.ARGB_8888);
                        image.eraseColor(Color.GREEN);     // fill it with green

                        cameraView.setImageBitmap(image);  // set the image of the image view to the bitmap placeholder image

                        parentView.addView(cameraView);    // add the camera view to the parent view

                        RobotLog.d("Created Camera View"); // logging

                        // Scale and rotate it (I'm not sure why I did this, but it probably makes it fit better on screen)
                        cameraView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        cameraView.setRotation(-90);


                        // Make the other view exactly the same way
                        userView = new ImageView(AppUtil.getInstance().getApplication().getApplicationContext());
                        userView.setImageBitmap(image);
                        parentView.addView(userView);
                        userView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        userView.setRotation(-90);
                        RobotLog.d("Created Result View!", "");

                        // Finally, now that all the views are added and initialized, we can show the parent view
                        // (it starts invisible)
                        parentView.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {
                        // The activity was null for some reason... That's weird
                        RobotLog.d("ERROR! NULL POINTER EXCEPTION");
                        RobotLog.d(e.getMessage());
                    }
                }
            });
        }

    }

    /**
     * Remove the views so we don't add multiple to the screen and so other programs can run
     */
    private void cleanUpViews() {
        if (showsViews) {
            AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parentView.removeView(cameraView);
                    parentView.removeView(userView);
                }
            });
        }
    }

    /**
     * Update the bitmap of an image view previously prepared. Intended to be used internally.
     * The params must be final since they're being passed to an inner class.
     *
     * @param view either cameraView or userView
     * @param bitmap the bitmap to update the image view with
     */
    private void showBitmap(final ImageView view, final Bitmap bitmap) {
        // remember UI changes must be on the UI thread
        AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
            @Override
            public void run() {
                // set the image in the image view to a scaled bitmap
                // This keeps the image view from changing size and looking weird.
                view.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 1000, 600, false));
            }
        });
    }


    /**
     * Public method to update the user-supplied bitmap.
     * This is used for debugging to show an intermediate bitmap image after filtering or processing.
     * @param bitmap the bitmap to show
     */
    public void updatePreviewBitmap(final Bitmap bitmap) {
        if (showsViews) // make sure we've actually initialized the views and are ready for a bitmap
            showBitmap(userView, bitmap); // show it!
    }






    /**** SAVE IMAGES ****/

    private int framesToSkip = 5;              // The number of frames to skip in between saving (we probably don't want to save every frame)
    private int framesSaved = 0;               // The total number of frames saved (so we can increment the file names and keep track of if we should save this one or not
    private int userFramesSaved = 0;           // The total number of user frames saved (for incrementing file names)
    private boolean shouldSaveFrames = false;  // Whether or not we should save frames (depends upon folders existing and if anyone's told us to start saving the frames
    private File folder;                       // the root folder to save the frames in
    private File originals;                    // the subfolder to put the original frames in
    private File userSupplied;                 // the subfolder to put processed user frames in

    /**
     * Start saving the frames from now on to the folder.
     * @param framesToSkip the number of frames to skip between saving the frames. I don't suggest 0.
     * @return true if it succeeded in starting, false if it failed.
     */
    public void startSavingImages(int framesToSkip) {
        initFolder(); // Should I save the frames? Only if the folders are ready for them.
        shouldSaveFrames = true;
    }

    /**
     * Initialize the folders and add another one each time this runs,
     *   incrementing the name by one to keep the runs separate
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initFolder() {
        File pictures = new File("/storage/self/primary/Pictures/webcam");         // the Pictures/webcam folder on the RC phone (/storage/self/primary/Pictures/webcam)

        // Get an array of the old folders we've made so we can find the last number we've used.
        File[] oldFolders = pictures.listFiles(new FilenameFilter() { // filter by the filename
            @Override
            public boolean accept(File folder, String name) {
                return name.startsWith("webcamManager"); // if the name starts with webcamManager, it's probably ours.
            }
        });

        // find the last number we used
        int maxFolderIndex = -1; // -1 since we're adding 1 later and want to start at 0 if we find no old folders
        if(oldFolders != null)
            for (File folder : oldFolders) // loop through all the folders that matched our search
                if (Integer.parseInt(folder.getName().split("_")[1]) > maxFolderIndex) // parse the int following the hyphen and compare with the maxFolderIndex
                    maxFolderIndex = Integer.parseInt(folder.getName().split("_")[1]); // If it's bigger than the max, save it

        // the current folder we're working in; Pictures/webcam/webcamManager_#
        folder = new File(pictures, "webcamManager_" + (maxFolderIndex + 1));
        originals = new File(folder, "originals"); // the originals subfolder
        userSupplied = new File(folder, "user");   // the user subfolder

        // now we need to make these fictional folders if they don't exist...
        pictures.mkdir();
        folder.mkdir();
        originals.mkdir();
        userSupplied.mkdir();
    }


    /**
     * Internal. Save the bitmap to a specified folder (originals or userSupplied) using the counter specified
     * @param folder  the folder to save to
     * @param counter the counter for the filename
     * @param bitmap  the bitmap to save
     */
    private void saveBitmapInternal(File folder, int counter, Bitmap bitmap) {
        // make sure we should be doing this...
        if (shouldSaveFrames)
            try {
                // Create an output stream to the png file in the folder using the counter for the filename
                FileOutputStream outStream = new FileOutputStream(new File(folder, counter + ".png"));

                // Compress the bitmap into a PNG image to the FileOutputStream outStream
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);

                outStream.close(); // Close the stream.

            } catch (Exception e) {
                RobotLog.d("Error saving bitmap!");
                RobotLog.d(e.getMessage());
            }
    }

    /**
     * Save a frame direct from the webcam
     * @param frame the frame to save
     */
    private void saveFrame(Bitmap frame) {
        // increment and check if we should save this frame
        // (framesToSkip + 1 % framesSaved should give us 0 if we should save this one
        // eg. framesToSkip is 5, we'll save 0, 6, 12, 18, skipping 5 each time.
        RobotLog.d("FRAMES SAVED:" + framesSaved);
        if ((framesSaved++) % (framesToSkip + 1) == 0) {
            // Save the frame to the originals folder using the counter framesSaved
            saveBitmapInternal(originals, framesSaved, frame);
            RobotLog.d("FRAME SAVED SUCCESSFUL!");
        }
    }


    /**
     * Save a publicly-supplied bitmap
     * @param bitmap the bitmap to save
     */
    public void saveBitmap(Bitmap bitmap) {
        // Save it to the userSupplied folder using and incrementing the userFramesSaved file counter.
        saveBitmapInternal(userSupplied, userFramesSaved++, bitmap);
    }
}
