package org.firstinspires.ftc.teamcode.visual.test;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.visual.VuforiaPosition;
import org.firstinspires.ftc.teamcode.visual.webcam.PhoneManager;

@TeleOp
public class SkystoneAlignmentTest extends OpMode {
    VuforiaPosition position = new VuforiaPosition();
    PhoneManager cameraManager = new PhoneManager();

    public double getSkystoneOffset() {
        Bitmap realFrame = cameraManager.getCurrentFrame().copy(Bitmap.Config.RGB_565, false);
        //Bitmap currentFrame = Bitmap.createScaledBitmap(realFrame, 720, 10, false);
        Bitmap currentFrame = Bitmap.createScaledBitmap(Bitmap.createBitmap(realFrame, 0, 240, 1280, 240), 1280, 1, false);
        telemetry.addData("Height", realFrame.getHeight());
        telemetry.addData("Width", realFrame.getWidth());
        double[] hsv = new double[3];

        int lastYellow = -1; // keep track of the last yellow pixel seen
        int blackBounds[] = {-1, -1};

        // (DEBUG) show the skystone and black pixels a special color
        for (int x = 0; x < currentFrame.getWidth(); x++) {
            PhoneManager.colorToHSV(currentFrame.getPixel(x, 0), hsv);
            if (30 < hsv[0] && hsv[0] < 50) {
                if (lastYellow == -1 || x - lastYellow < 6) {
                    lastYellow = x;
                    currentFrame.setPixel(x, 0, 0x00FF00);
                } else {
                    blackBounds[0] = lastYellow - 1;
                    blackBounds[1] = x - 1;
                    lastYellow = x;
                }
            } else if (x - lastYellow < 6) {
                currentFrame.setPixel(x, 0, 0x0000FF);
            } else {
                currentFrame.setPixel(x, 0, 0xFF0000);
            }
        }

        telemetry.addData("Left:", 640 - blackBounds[0]);
        telemetry.addData("Right", blackBounds[1] - 640);


        cameraManager.updatePreviewBitmap(currentFrame);


        // Now we need a proportion of left to right... Just subtracting might work...

        return 0;
    }

    @Override
    public void init() {
        //Assembly.initialize(telemetry, hardwareMap, position);
        //position.cameraManager.startSavingImages(5);
        cameraManager.startCaptureWithViews(telemetry, hardwareMap.appContext);
        //cameraManager.startSavingImages(5);
    }

    @Override
    public void loop() {
        telemetry.addData("Skystone offset: ", getSkystoneOffset());
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
        cameraManager.stopCapture();
    }
}
