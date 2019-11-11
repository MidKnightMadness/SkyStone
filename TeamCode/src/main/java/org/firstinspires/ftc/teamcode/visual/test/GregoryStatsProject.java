package org.firstinspires.ftc.teamcode.visual.test;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.CameraCalibration;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaBase;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone.TRACKABLE_NAMES;

@TeleOp
@Disabled
public class GregoryStatsProject extends OpMode {

    // The vuforia instance.
    private VuforiaSkyStone vuforia = new VuforiaSkyStone();
    private PrintWriter writer;
    private int captures = 0;

    @Override
    public void init() {

        // let FTC do the hard work for us...
        vuforia.initialize(
                // Vuforia License key (developer.vuforia.com):
                "Ae7oRjb/////AAABmV3pkVnpEU9Pv3XaN0o2EZ5ttngvTMliTd5nX0843lAXhah50oPXg63sdsiK9/BFMjXkw9lMippdx4bHQo5kycWr1GcFcv+QlVNEpSclUqu9Zzj4FYVl+J2ScSAXSyuCRWMRWd3AikCfhAtlwFe7dnMIfpVniU8Yr8o3YumS2/5LjNU2wIkiJak5IHlnugT414wsrzyqemO63BHn0Olbi3REkd61RxW3cE4lbSts3OI0GfnT57/Nw6/YfLAZQ69eCz0eEckVjPmbt7evb8lYo5gEpzm+wf5LVPaAzZWVj/gSQywzPKA8zoz4q6hl4zuAd3647Y3smuWVI8PpQzRwt5vP8d07Qt39p+/zEOrcGRDo",

                //VuforiaLocalizer.CameraDirection.BACK, // Phone back camera
                hardwareMap.get(WebcamName.class,"Webcam 1"), // webcam hardware device

                "teamwebcamcalibrations.xml", //the name of the webcam calibration file (Sent to RC using OnBotJava interface)

                false, // extended tracking is not supported by this webcam

                true, // Enable camera monitoring (the axes or teapot)

                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // Show axes, not the teapot

                0, 0, 0, // the position and angle of the phone on the robot
                0, 0, 0,

                false // Use the competition field layout
        );
        vuforia.activate();

        telemetry.addLine(new File(Environment.getExternalStorageDirectory(), "gregory/").mkdir() + "");

        // Create file to save the data in
        File file = new File(Environment.getExternalStorageDirectory(), "gregory/statsWebcam1.txt");


        try {
            writer = new PrintWriter(file);

        } catch (FileNotFoundException e) {
            RobotLog.e("ERROR!");
            telemetry.addLine("Error2!");
            RobotLog.e(e.getMessage());
        }
    }

    public GregoryStatsProject() {
        msStuckDetectInit = 20000;
    }


    private boolean bPressed = false;
    @Override
    public void loop() {
        printCalibration();
        if (gamepad1.b && !bPressed) {
            VuforiaBase.TrackingResults results = vuforia.track("Blue Perimeter 1");
            writer.println(results.x);
            captures++;

            bPressed = true;
        } else if (!gamepad1.b) {
            bPressed = false;
        }

        printPoses();
        telemetry.addData("Captures", captures);
    }

    @Override
    public void stop() {
        super.stop();
        writer.close();
    }

    // camera 0.8 in forward

    public void printCalibration() {
        // get the camera calibration from vuforia (CameraDevice is a singleton that referrs to the currently used camera)
        CameraCalibration calibration = CameraDevice.getInstance().getCameraCalibration();

        // get the data from the calibration
        float[] size = calibration.getSize().getData();
        float[] focalLength = calibration.getFocalLength().getData();
        float[] principalPoint = calibration.getPrincipalPoint().getData();
        float[] distortion = calibration.getDistortionParameters().getData();

        // Print it out
        telemetry.addData("Size", Arrays.toString(size));
        telemetry.addData("Focal Length", Arrays.toString(focalLength));
        telemetry.addData("Principal Point", Arrays.toString(principalPoint));
        telemetry.addData("Distortion", Arrays.toString(distortion));
    }

    private void printPoses() {
        for (String name : TRACKABLE_NAMES) {
            VuforiaBase.TrackingResults results = vuforia.track(name);
            if (results.isVisible) {
                telemetry.addData("Name", name);
                if (results.matrix != null) {
                    telemetry.addLine(String.format(Locale.ENGLISH, "Orientation: %.4f %.4f %.4f", results.x, results.y, results.z));
                }
            }
        }
    }

}
