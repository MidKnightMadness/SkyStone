package org.firstinspires.ftc.teamcode.visual;

import android.graphics.Bitmap;

import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaBase;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.visual.webcam.PhoneManager;

public class VuforiaPosition extends Visual {
    private PhoneManager cameraManager = new PhoneManager();
    private VuforiaSkyStone vuforia = new VuforiaSkyStone();

    @Override
    public void init() {
        //initialize camera manager
        cameraManager.startCaptureWithViews(telemetry, hardwareMap.appContext);
        //cameraManager.startCapture(telemetry, hardwareMap.appContext);

        //initialize vuforia sksytone
        vuforia.initialize(
                // Vuforia License key (developer.vuforia.com):
                "Ae7oRjb/////AAABmV3pkVnpEU9Pv3XaN0o2EZ5ttngvTMliTd5nX0843lAXhah50oPXg63sdsiK9/BFMjXkw9lMippdx4bHQo5kycWr1GcFcv+QlVNEpSclUqu9Zzj4FYVl+J2ScSAXSyuCRWMRWd3AikCfhAtlwFe7dnMIfpVniU8Yr8o3YumS2/5LjNU2wIkiJak5IHlnugT414wsrzyqemO63BHn0Olbi3REkd61RxW3cE4lbSts3OI0GfnT57/Nw6/YfLAZQ69eCz0eEckVjPmbt7evb8lYo5gEpzm+wf5LVPaAzZWVj/gSQywzPKA8zoz4q6hl4zuAd3647Y3smuWVI8PpQzRwt5vP8d07Qt39p+/zEOrcGRDo",

                //VuforiaLocalizer.CameraDirection.BACK, // Phone back camera
                hardwareMap.get(WebcamName.class, "Webcam 1"), // webcam hardware device

                "teamwebcamcalibrations.xml", //the name of the webcam calibration file (Sent to RC using OnBotJava interface)

                false, // extended tracking is not supported by this webcam

                false, // Enable camera monitoring (the axes or teapot)

                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // Show axes, not the teapot

                0, 0, 0, // the position and angle of the phone on the robot
                0, 0, 0,

                true // Use the competition field layout
        );
        vuforia.activate();
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);
    }

    @Override
    public Position getPosition() {
        double x = 0;
        double y = 0;
        double theta = 0;
        int count = 0;

        //find average position on the field according to each visible trackable
        for (String name : VuforiaSkyStone.TRACKABLE_NAMES) {
            VuforiaBase.TrackingResults track = vuforia.track(name);
            if (track.isVisible) {
                x += track.x;
                y += track.y;
                theta += track.xAngle;
                count++;
            }
        }

        //debugging for now since the Distance class is not finished
        //telemetry.addData("x", x);
        //telemetry.addData("y", y);
        //telemetry.addData("theta", theta);
        //telemetry.addData("count", count);

        //if there is nothing detected, return null
        if (count == 0)
            return null;
        else
            return new Position(Distance.fromMillimeters(x / count), Distance.fromMillimeters(y / count), Angle.fromDegrees(theta / count));
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
                    PhoneManager.colorToHSV(scaledFrame.getPixel(29 + i * 20 + x, 24 + y), hsv);

                    blackPixels[i] += hsv[1];
                }
        }
        //set other stones magenta
        scaledFrame.setPixel(30, 25, 0xFF00FF);
        scaledFrame.setPixel(50, 25, 0xFF00FF);
        scaledFrame.setPixel(70, 25, 0xFF00FF);

        SkystoneSetup result;

        //set the blacker stone green
        if (blackPixels[0] < blackPixels[1]) {
            if (blackPixels[0] < blackPixels[2]) {
                scaledFrame.setPixel(30, 25, 0x00FF00);
                result = SkystoneSetup.Left;  //1st stone
            } else {
                scaledFrame.setPixel(50, 25, 0x00FF00);
                result = SkystoneSetup.Center;  //2nd stone
            }
        } else if (blackPixels[1] < blackPixels[2]) {
            scaledFrame.setPixel(50, 25, 0x00FF00);
            result = SkystoneSetup.Center;  //2nd stone
        } else {
            scaledFrame.setPixel(70, 25, 0x00FF00);
            result = SkystoneSetup.Right;  //3rd stone
        }

        //display the modified bitmap
        cameraManager.updatePreviewBitmap(scaledFrame);
        return result;
    }

    public void stop() {
        cameraManager.stopCapture();
    }
}
