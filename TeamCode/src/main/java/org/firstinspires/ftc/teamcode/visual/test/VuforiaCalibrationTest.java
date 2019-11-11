package org.firstinspires.ftc.teamcode.visual.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.CameraCalibration;
import com.vuforia.CameraDevice;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.io.File;
import java.util.Arrays;

@TeleOp
@Disabled
public class VuforiaCalibrationTest extends OpMode {

    // The vuforia instance.
    private VuforiaLocalizer vuforia;

    @Override
    public void init() {

        // get the id of the camera view (the UI component) on the robot controller from the hardware map
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId",
                "id", hardwareMap.appContext.getPackageName());

        // Create the initialization parameters instance for vuforia with a preview on the view with id cameraMonitorViewId
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // Give vuforia the license key obtained at developer.vuforia.com
        parameters.vuforiaLicenseKey = "Ae7oRjb/////AAABmV3pkVnpEU9Pv3XaN0o2EZ5ttngvTMliTd5nX0843lAXhah50oPXg63sdsiK9/BFMjXkw9lMippdx4bHQo5kycWr1GcFcv+QlVNEpSclUqu9Zzj4FYVl+J2ScSAXSyuCRWMRWd3AikCfhAtlwFe7dnMIfpVniU8Yr8o3YumS2/5LjNU2wIkiJak5IHlnugT414wsrzyqemO63BHn0Olbi3REkd61RxW3cE4lbSts3OI0GfnT57/Nw6/YfLAZQ69eCz0eEckVjPmbt7evb8lYo5gEpzm+wf5LVPaAzZWVj/gSQywzPKA8zoz4q6hl4zuAd3647Y3smuWVI8PpQzRwt5vP8d07Qt39p+/zEOrcGRDo";

        // Use the built-in rear camera
        //parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        // Use a webcam named "Webcam 1" (only this *or* the builtin camera)
        parameters.cameraName = hardwareMap.get(WebcamName.class,"Webcam 1");

        parameters.webcamCalibrationFiles = new File[]{new File("/storage/self/primary/FIRST/webcamcalibrations/teamwebcamcalibrations.xml")};
        telemetry.addLine("Calibration file added!");
        // Show the axes on the camera view on the robot controller for debugging
        //parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        // Create the vuforia instance with the parameters
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        new VuforiaSkyStone();
    }

    @Override
    public void loop() {
       printCalibration();
    }

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
}
