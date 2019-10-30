package org.firstinspires.ftc.teamcode.visual.webcam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureRequest;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSequenceId;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSession;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraFrame;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.CameraManagerInternal;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
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

    public void startCaptureWithViews(WebcamName webcam, Context appContext) {
        this.appContext = appContext;
        showsViews = true;
        initViews();
        startCapture(webcam);
    }

    public void startCapture(WebcamName webcam) {

        //RobotLog.d("MAX FPS"+webcam.getCameraCharacteristics().getMaxFramesPerSecond(ImageFormat.YUY2, FRAME_SIZE));
        cameraManager = ClassFactory.getInstance().getCameraManager();
        camera = cameraManager.requestPermissionAndOpenCamera(new Deadline(1, TimeUnit.MINUTES), webcam, null);
        try {
           captureSession = camera.createCaptureSession(Continuation.create(((CameraManagerInternal) cameraManager).getSerialThreadPool(), this));

        } catch (Exception e) {
            RobotLog.ee("ERROR CREATING CAPTURE!", e.getMessage());
        }

    }

    public void stopCapture()
    {
        if(captureSession != null)
        {
            captureSession.stopCapture();
            captureSession.close();
        }
    }

    /**** Camera Capture Session ****/

    private volatile Bitmap currentFrame = Bitmap.createBitmap(FRAME_SIZE.getWidth(), FRAME_SIZE.getHeight(), Bitmap.Config.ARGB_8888);

    public Bitmap getCurrentFrame() {
        return currentFrame.copy(Bitmap.Config.ARGB_8888, false);
    }

    @Override
    public void onConfigured(CameraCaptureSession session) {
        try {

            CameraCaptureRequest captureRequest = camera.createCaptureRequest(ImageFormat.YUY2, FRAME_SIZE, FPS);
            session.startCapture(captureRequest, new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onNewFrame(CameraCaptureSession session, CameraCaptureRequest request, CameraFrame cameraFrame) {
                    cameraFrame.copyToBitmap(currentFrame);
                    if (showsViews)
                        showBitmap(cameraView, currentFrame);
                    if (shouldSaveFrames)
                        saveFrame(currentFrame);

                }
            }, Continuation.create(((CameraManagerInternal) cameraManager).getSerialThreadPool(), new CameraCaptureSession.StatusCallback()
            {
                @Override public void onCaptureSequenceCompleted(CameraCaptureSession session, CameraCaptureSequenceId cameraCaptureSequenceId, long lastFrameNumber)
                {
                    RobotLog.d("capture sequence %s reports completed: lastFrame=%d", cameraCaptureSequenceId, lastFrameNumber);
                }
            }));
        } catch (Exception e) {
            RobotLog.e("ERROR CREATING CAPTURE!");
            RobotLog.e(e.getMessage());
        }

    }

    @Override
    public void onClosed(CameraCaptureSession session) {
        session.stopCapture();
    }





    /**** Preview image ****/

    private ImageView cameraView;
    private ImageView resultView;

    private void initViews() {
        if (showsViews) {
            AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ViewGroup parentView = Objects.requireNonNull(AppUtil.getInstance().getActivity()).findViewById(appContext.getResources().getIdentifier("cameraMonitorViewId", "id", appContext.getPackageName()));
                        RobotLog.d("Found Parent View!" + parentView.toString());
                        cameraView = new ImageView(AppUtil.getInstance().getApplication().getApplicationContext());
                        Bitmap image = Bitmap.createBitmap(1000, 600, Bitmap.Config.ARGB_8888);
                        image.eraseColor(Color.GREEN);
                        cameraView.setImageBitmap(image);
                        parentView.addView(cameraView);
                        RobotLog.d("Created Camera View");
                        cameraView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        cameraView.setRotation(-90);
                        resultView = new ImageView(AppUtil.getInstance().getApplication().getApplicationContext());
                        resultView.setImageBitmap(image);
                        parentView.addView(resultView);
                        resultView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        resultView.setRotation(-90);
                        RobotLog.d("Created Result View!", "");
                        parentView.setVisibility(View.VISIBLE);
                    } catch (NullPointerException e) {
                        RobotLog.d("ERROR! NULL POINTER EXCEPTION (probably)");
                        RobotLog.d(e.getMessage());
                    }
                }
            });
        }

    }

    private void showBitmap(final ImageView view, final Bitmap bitmap) {
        AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 1000, 600, false));
            }
        });
    }

    public void updatePreviewBitmap(final Bitmap bitmap) {
        showBitmap(resultView, bitmap);
    }






    /**** SAVE IMAGES ****/

    private int framesToSkip = 0;
    private int framesSaved = 0;
    private int userFramesSaved = 0;
    private boolean shouldSaveFrames = false;
    private File folder;
    private File originals;
    private File userSupplied;

    public boolean startSavingImages(int framesToSkip) {
        shouldSaveFrames = initFolder();
        return shouldSaveFrames;
    }

    private boolean initFolder() {
        File pictures = new File(Environment.DIRECTORY_PICTURES);
        File[] oldFolders = pictures.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.startsWith("webcamManager");
            }
        });
        int maxFolderIndex = -1;
        for (File folder : oldFolders) {
            if (Integer.parseInt(folder.getName().split("-")[1]) > maxFolderIndex) {
                maxFolderIndex = Integer.parseInt(folder.getName().split("-")[1]);
            }
        }
        folder = new File(pictures, "webcamManager" + (maxFolderIndex + 1));
        originals = new File(folder, "originals");
        userSupplied = new File(folder, "user");
        return folder.mkdir() && originals.mkdir() && userSupplied.mkdir();
    }

    private void saveBitmapInternal(File folder, int counter, Bitmap bitmap) {
        if (shouldSaveFrames) {
            try {
                FileOutputStream srcStream = new FileOutputStream(new File(folder, counter + ".png"));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, srcStream);
                srcStream.close();
            } catch (Exception e) {
                RobotLog.d("Error saving bitmap!");
                RobotLog.d(e.getMessage());
            }
        }
    }

    private void saveFrame(Bitmap frame) {
        if ((framesToSkip + 1) % (framesSaved++) == 0) {
            saveBitmapInternal(originals, framesSaved, frame);
        }
    }

    public void saveBitmap(Bitmap bitmap) {
        saveBitmapInternal(userSupplied, userFramesSaved++, bitmap);
    }

}
