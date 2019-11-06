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
    private long frameStart;    //for calculating the time for a frame to be processed
    private float frameAvg;
    private long frameCount;

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
        telemetry.addData("time",System.nanoTime() /1000000000.0 + "");
        if(frameCooldown > 0)
            frameCooldown--;
        else
        {
            //start counting time
            frameStart = System.nanoTime();

            frameCooldown = frameCooldownMax;
            Bitmap currentFrame = cameraManager.getCurrentFrame().copy(Bitmap.Config.RGB_565,true);
            Bitmap scaledFrame = Bitmap.createScaledBitmap(currentFrame,200,50,false);

            //center pixel
            double[] hsv = new double[3];
            VuforiaPhoneCameraManager.colorToHSV(currentFrame.getPixel(currentFrame.getWidth() /2, currentFrame.getHeight() /2), hsv);
            lines = "h: " + hsv[0] + ", s: " + hsv[1] + ", v: " + hsv[2] + "\n";

            //highlight the spaces between pixels to red
            int prevGreenX = 0;
            //highlights yellow pixels to green
            for (int y = 0; y < scaledFrame.getHeight(); y++)
                for (int x = 0; x < scaledFrame.getWidth(); x++)
                {
                    VuforiaPhoneCameraManager.colorToHSV(scaledFrame.getPixel(x, y), hsv);
                    //33 to 47
                    if(Math.abs(hsv[0] - 40) < 7 && hsv[1] > 0.5)
                    {
                        //set pixel to green
                        scaledFrame.setPixel(x, y, 0x00FF00);

                        //find the distance to the previous green pixel and set red
                        if(Math.abs(x - prevGreenX) <= 4 && Math.abs(x - prevGreenX) >= 2 && x >= 1) {
                            scaledFrame.setPixel(x - 1, y, 0xFF0000);

                            //for (int redX = prevGreenX + 1; redX < x; redX++) {
                            //    scaledFrame.setPixel(redX, y, 0xFF0000);
                            //}
                        }

                        prevGreenX = x;
                    }
                }

            //original frame size
            lines += "width: " + currentFrame.getWidth() + "\n";
            lines += "height: " + currentFrame.getHeight() + "\n";

            //displays the modified bitmap
            cameraManager.updatePreviewBitmap(scaledFrame);

            //end of counting time
            lines += "time taken: " + (System.nanoTime() - frameStart) /1000000000.0 +"\n";
            frameAvg += (System.nanoTime() - frameStart) /1000000000.0;
            frameCount++;
            lines += "time taken avg: " + frameAvg / frameCount +"\n";
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
