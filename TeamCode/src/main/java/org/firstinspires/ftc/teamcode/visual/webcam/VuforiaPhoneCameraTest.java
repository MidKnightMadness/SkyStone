package org.firstinspires.ftc.teamcode.visual.webcam;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;

@TeleOp
public class VuforiaPhoneCameraTest extends OpMode {

    private PhoneManager cameraManager = new PhoneManager();
    private int frameCooldown = 0;  //allows loop to run multiple times between frames
    private int frameCooldownMax = 10;
    private String lines;   //lines for telemetry output
    private long frameStart;    //for calculating the time for a frame to be processed
    private float frameAvg;
    private long frameCount;
    enum SkystonePos
    {
        LEFT,
        CENTER,
        RIGHT,
    }

    public VuforiaPhoneCameraTest()
    {
        msStuckDetectInit = 20000;
        msStuckDetectStop = 20000;
    }

    @Override
    public void init() {
        cameraManager.startCaptureWithViews(telemetry, hardwareMap.appContext);
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
            Bitmap scaledFrame = Bitmap.createScaledBitmap(
                    Bitmap.createBitmap(currentFrame,0,240,1280,420),100,50,false);

            //center pixel
            double[] hsv = new double[3];
            VuforiaPhoneCameraManager.colorToHSV(currentFrame.getPixel(currentFrame.getWidth() /2, currentFrame.getHeight() /2), hsv);
            lines = "h: " + hsv[0] + ", s: " + hsv[1] + ", v: " + hsv[2] + "\n";

            /*
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
                }*/

            //find skystone
            //counts the black pixels in a 3*3 area
            double[] blackPixels = new double[3];
            for (int i = 0; i < 3; i ++) {
                for (int x = 0; x < 3; x++)
                    for (int y = 0; y < 3; y++)
                    {
                        VuforiaPhoneCameraManager.colorToHSV(scaledFrame.getPixel(30 + i * 20 + x,25 + y), hsv);

                        blackPixels[i] += hsv[1];
                    }
            }
            //set other stones magenta
            scaledFrame.setPixel(30,25,0xFF00FF);
            scaledFrame.setPixel(50,25,0xFF00FF);
            scaledFrame.setPixel(70,25,0xFF00FF);

            //set the blacker stone green
            if(blackPixels[0]<blackPixels[1])
            {
                if(blackPixels[0]<blackPixels[2])
                { scaledFrame.setPixel(30,25,0x00FF00); lines += "LEFT\n"; }   //1st stone
                else
                { scaledFrame.setPixel(50,25,0x00FF00); lines += "CENTER\n"; }   //2nd stone
            }
            else if(blackPixels[1]<blackPixels[2])
            { scaledFrame.setPixel(50,25,0x00FF00); lines += "CENTER\n"; }  //2nd stone
            else
            { scaledFrame.setPixel(70,25,0x00FF00); lines += "RIGHT\n"; }  //3rd stone

            //original frame size
            lines += "width: " + currentFrame.getWidth() + ", ";
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
