package org.firstinspires.ftc.teamcode.visual;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.HINT;
import com.vuforia.State;
import com.vuforia.TrackerManager;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.visual.webcam.VuforiaPhoneCameraManager;

import java.util.concurrent.ExecutionException;

public class VuforiaPosition extends Visual {
    private VuforiaPhoneCameraManager cameraManager = new VuforiaPhoneCameraManager();

    @Override
    public void init() {
        //cameraManager.startCaptureWithViews(hardwareMap.appContext);
        cameraManager.startCapture();
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public SkystoneSetup findSkystone() {
        Bitmap currentFrame = cameraManager.getCurrentFrame().copy(Bitmap.Config.RGB_565, true);
        Bitmap scaledFrame = Bitmap.createScaledBitmap(
                Bitmap.createBitmap(currentFrame, 0, 240, 1280, 420), 100, 50, false);

        //find skystone
        //counts the black pixels in a 3*3 area
        double[] hsv = new double[3];
        double[] blackPixels = new double[3];
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++)
                for (int y = 0; y < 3; y++) {
                    VuforiaPhoneCameraManager.colorToHSV(scaledFrame.getPixel(30 + i * 20 + x, 25 + y), hsv);

                    blackPixels[i] += hsv[1];
                }
        }
        //set other stones magenta
        //scaledFrame.setPixel(30, 25, 0xFF00FF);
        //scaledFrame.setPixel(50, 25, 0xFF00FF);
        //scaledFrame.setPixel(70, 25, 0xFF00FF);
        //cameraManager.updatePreviewBitmap(scaledFrame);

        //set the blacker stone green
        if (blackPixels[0] < blackPixels[1]) {
            if (blackPixels[0] < blackPixels[2]) {
                //scaledFrame.setPixel(30, 25, 0x00FF00);
                return SkystoneSetup.Left;  //1st stone
            } else {
                //scaledFrame.setPixel(50, 25, 0x00FF00);
                return SkystoneSetup.Center;  //2nd stone
            }
        } else if (blackPixels[1] < blackPixels[2]) {
            //scaledFrame.setPixel(50, 25, 0x00FF00);
            return SkystoneSetup.Center;  //2nd stone
        } else {
            //scaledFrame.setPixel(70, 25, 0x00FF00);
            return SkystoneSetup.Right;  //3rd stone
        }

        //display the modified bitmap
        //cameraManager.updatePreviewBitmap(scaledFrame);
    }

    public void stop() {
        cameraManager.stopCapture();
    }
}
