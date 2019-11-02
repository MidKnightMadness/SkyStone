package org.firstinspires.ftc.teamcode.visual.webcam;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class VuforiaPhoneCameraTest extends OpMode {

    private VuforiaPhoneCameraManager cameraManager = new VuforiaPhoneCameraManager();
    private int frameCooldown = 0;  //allows loop to run multiple times between frames
    private int frameCooldownMax = 10;
    private String lines;   //lines for telemetry output

    public VuforiaPhoneCameraTest()
    {
        msStuckDetectInit = 20000;
        msStuckDetectStop = 20000;
    }

    @Override
    public void init() {
        cameraManager.startCaptureWithViews(hardwareMap.appContext);
    }

    @Override
    public void loop() {
        telemetry.addLine(System.nanoTime() /1000000000.0 + "");
        if(frameCooldown > 0)
            frameCooldown--;
        else
        {
            frameCooldown = frameCooldownMax;
            Bitmap currentFrame = cameraManager.getCurrentFrame().copy(Bitmap.Config.RGB_565,true);
            Bitmap scaledFrame = Bitmap.createScaledBitmap(currentFrame, 200, 100, false);

            //center pixel
            double[] hsv = new double[3];
            VuforiaPhoneCameraManager.colorToHSV(currentFrame.getPixel(currentFrame.getWidth() /2, currentFrame.getHeight() /2), hsv);
            lines = "h:" + hsv[0] + ", s:" + hsv[1] + ", v:" + hsv[2] + "\n";

            //highlight the spaces between pixels to red
            int prevGreenX = 0;
            //highlights yellow pixels to green
            for (int x = 0; x < scaledFrame.getWidth(); x++)
                for (int y = 0; y < scaledFrame.getHeight(); y++)
                {
                    VuforiaPhoneCameraManager.colorToHSV(scaledFrame.getPixel(x, y), hsv);
                    if(Math.abs(hsv[0] - 43) < 7 && hsv[1] > 0.5)
                    {
                        //set pixel to green
                        //scaledFrame.setPixel(x, y, 0x00FF00);

                        //find the distance to the previous green pixel
                        if(Math.abs(x - prevGreenX) <= 5 && Math.abs(x - prevGreenX) >= 1) {
                            scaledFrame.setPixel(x, y, 0x0000FF);
                            telemetry.addLine("x:"+(x-1)+", y:"+y);
                        }

                        prevGreenX = x;
                    }
                }

            //original frame size
            lines += "width:" + currentFrame.getWidth() + "\n";
            lines += "height:" + currentFrame.getHeight() + "\n";

            //displays the modified bitmap
            cameraManager.updatePreviewBitmap(scaledFrame);
        }
        telemetry.addLine(lines);
        telemetry.update();
    }

    @Override
    public void stop()
    {
        cameraManager.stopCapture();
    }
}
