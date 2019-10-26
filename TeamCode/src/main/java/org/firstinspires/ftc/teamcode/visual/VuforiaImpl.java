package org.firstinspires.ftc.teamcode.visual;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.CameraDevice;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Visual Assembly adapted from last year's code
 *
 * Too crazy to comment fully now. Go ahead and try to understand it.
 *
 * Created by Gregory on 9/14/18.
 */

//@TeleOp
public class VuforiaImpl extends Visual {

    private VuforiaLocalizer vuforia;
    private ViewGroup parentView;
    private ImageView cameraView;
    private ImageView resultView;
    private final int RGB565 = 1; // For OnBotJava

    @Override
    public void init() {
        telemetry.addLine("Initializing");
        telemetry.addData("Debug", Visual.DEBUG);
        telemetry.update();
        // Init the VuforiaLocalizer parameters object with the camera View ID
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(); // to see the view, add com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId as param

        // Set the Back camera active
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        // License Key obtained from Vuforia website
        params.vuforiaLicenseKey = "";

        // Display axes on top of visual matches
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        // Create Vuforia instance with params: -- takes 1-2s
        vuforia = ClassFactory.getInstance().createVuforia(params);

        // Save an rgb565 image for further processing each frame and only save current frame
        Vuforia.setFrameFormat(RGB565, true);
        vuforia.setFrameQueueCapacity(1);

        if (Visual.DEBUG) {
            AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parentView = (ViewGroup) AppUtil.getInstance().getActivity().findViewById(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
                    parentView.removeAllViews();
                    cameraView = new ImageView(AppUtil.getInstance().getApplication().getApplicationContext());
                    Bitmap image = Bitmap.createBitmap(1000, 600, Bitmap.Config.RGB_565);
                    image.eraseColor(Color.GREEN);
                    cameraView.setImageBitmap(image);
                    parentView.addView(cameraView);
                    cameraView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    cameraView.setRotation(-90);
                    resultView = new ImageView(AppUtil.getInstance().getApplication().getApplicationContext());
                    resultView.setImageBitmap(image);
                    parentView.addView(resultView);
                    resultView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    resultView.setRotation(-90);
                    parentView.setVisibility(View.VISIBLE);
                }
            });
        }

        if (Visual.SAVE) {
            File mineralFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pictures/minerals/");
            File files[] = mineralFolder.listFiles();
            for (File file : files) {
                file.delete();
            }
        }

        // Flash On:
        //CameraDevice.getInstance().setFlashTorchMode(true);
        // Unable to use Vuforia Targets for minerals :(
    }


    public int isSkystone(boolean save) throws InterruptedException {
        return isSkystone(save, -1, -1);
    }

    @Override
    public int isSkystone(boolean save, int print_x, int print_y) throws InterruptedException {
        return -1;
    }

    public SkystonePosition findSkystone() throws InterruptedException {
        VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
        SkystonePosition pos = SkystonePosition.UNKNOWN;


        // One frame contains multiple image formats. Loop through all formats to find RGB565
        for (int i = 0; i < frame.getNumImages(); i++) {
            if (frame.getImage(i).getFormat() == RGB565) {


            }
        }
        return pos;
    }

    /*private int valueTest(int color) {
        return Color.blue(color);
    }*/

    public void stop() {
        if (Visual.DEBUG) {
            AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parentView.removeView(cameraView);
                    parentView.removeView(resultView);
                }
            });
        }

        // Flash Off
        CameraDevice.getInstance().setFlashTorchMode(false);
    }


    public void loop() {}


}