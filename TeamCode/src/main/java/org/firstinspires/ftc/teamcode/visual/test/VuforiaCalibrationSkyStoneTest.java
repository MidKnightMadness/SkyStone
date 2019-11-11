package org.firstinspires.ftc.teamcode.visual.test;

import android.graphics.Bitmap;
import android.graphics.Picture;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.CameraCalibration;
import com.vuforia.CameraDevice;
import com.vuforia.Image;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaBase;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;

import java.io.File;
import java.io.FileOutputStream;
import java.security.spec.ECField;
import java.util.Arrays;

import static com.vuforia.PIXEL_FORMAT.RGB565;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone.TRACKABLE_NAMES;

@TeleOp
@Disabled
public class VuforiaCalibrationSkyStoneTest extends OpMode {

    // The vuforia instance.
    private VuforiaSkyStone vuforia = new VuforiaSkyStone();
    private int captures = 0;

    @Override
    public void init() {



        // let FTC do the hard work for us...
        vuforia.initialize(
                // Vuforia License key (developer.vuforia.com):
                "Ae7oRjb/////AAABmV3pkVnpEU9Pv3XaN0o2EZ5ttngvTMliTd5nX0843lAXhah50oPXg63sdsiK9/BFMjXkw9lMippdx4bHQo5kycWr1GcFcv+QlVNEpSclUqu9Zzj4FYVl+J2ScSAXSyuCRWMRWd3AikCfhAtlwFe7dnMIfpVniU8Yr8o3YumS2/5LjNU2wIkiJak5IHlnugT414wsrzyqemO63BHn0Olbi3REkd61RxW3cE4lbSts3OI0GfnT57/Nw6/YfLAZQ69eCz0eEckVjPmbt7evb8lYo5gEpzm+wf5LVPaAzZWVj/gSQywzPKA8zoz4q6hl4zuAd3647Y3smuWVI8PpQzRwt5vP8d07Qt39p+/zEOrcGRDo",

                VuforiaLocalizer.CameraDirection.BACK, // Phone back camera
                //hardwareMap.get(WebcamName.class,"Webcam 1"), // webcam hardware device

                //"teamwebcamcalibrations.xml", //the name of the webcam calibration file (Sent to RC using OnBotJava interface)

                false, // extended tracking is not supported by this webcam

                true, // Enable camera monitoring (the axes or teapot)

                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // Show axes, not the teapot

                0, 0, 0, // the position and angle of the phone on the robot
                0, 0, 0,

                true // Use the competition field layout
                );
        vuforia.activate();
        vuforia.getVuforiaLocalizer().setFrameQueueCapacity(1);
        Vuforia.setFrameFormat(RGB565,true);
    }

    public VuforiaCalibrationSkyStoneTest() {
        msStuckDetectInit = 20000;
    }


    @Override
    public void loop() {
       printCalibration();
       if (gamepad1.b) {
           saveImage();
       }
       printPoses();
       telemetry.addData("Captures", captures);
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

    private void printPoses() {
        for (String name : TRACKABLE_NAMES) {
            VuforiaBase.TrackingResults results = vuforia.track(name);
            if (results.isVisible) {
                telemetry.addData("Name", name);
                if (results.matrix != null) {
                    telemetry.addData("Orientation", Arrays.toString(results.matrix.getTranslation().getData()));
                    telemetry.addData("Rotation", Arrays.toString(results.matrix.getData()));
                }
            }
        }
    }

    private void saveImage() {
        try {
            VuforiaLocalizer.CloseableFrame frame = vuforia.getVuforiaLocalizer().getFrameQueue().take();
            for (int i = 0; i < frame.getNumImages(); i++) {
                Image image = frame.getImage(i);
                if (image.getFormat() == RGB565) {
                    FileOutputStream output = new FileOutputStream(new File("/storage/self/primary/Pictures/calibration/" + Math.random() + ".png"));
                    imageToBitmap(image).compress(Bitmap.CompressFormat.PNG, 100, output);
                    captures++;
                }
            }

        } catch (Exception e) {
            RobotLog.e("Error with taking the frame! *************************************************************");
            RobotLog.e(e.getMessage());
        }
    }

    private Bitmap imageToBitmap(Image image) {
        Bitmap bmp = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
        bmp.copyPixelsFromBuffer(image.getPixels());
        return bmp;
    }

}
