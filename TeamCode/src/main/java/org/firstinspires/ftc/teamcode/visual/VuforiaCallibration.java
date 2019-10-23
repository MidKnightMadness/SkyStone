package org.firstinspires.ftc.teamcode.visual;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.CameraDevice;
import com.vuforia.HINT;
import com.vuforia.Image;
import com.vuforia.State;
import com.vuforia.Tool;
import com.vuforia.TrackerManager;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.io.File;
import java.io.FileOutputStream;

import static com.vuforia.PIXEL_FORMAT.RGB565;

@TeleOp
public class VuforiaCallibration extends OpMode {

    public VuforiaCallibration() {
        msStuckDetectInit = 20000;
    }

    private VuforiaLocalizer vuforia;
    private VuforiaTrackables trackables;

    private boolean aPressed;
    private String line;

    @Override
    public void internalPreInit() {
        super.internalPreInit();
        msStuckDetectInit = 20000;
        //msStuckDetectLoop = 20000;
    }

    @Override
    public void init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId",
                "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "Ae7oRjb/////AAABmV3pkVnpEU9Pv3XaN0o2EZ5ttngvTMliTd5nX0843lAXhah50oPXg63sdsiK9/BFMjXkw9lMippdx4bHQo5kycWr1GcFcv+QlVNEpSclUqu9Zzj4FYVl+J2ScSAXSyuCRWMRWd3AikCfhAtlwFe7dnMIfpVniU8Yr8o3YumS2/5LjNU2wIkiJak5IHlnugT414wsrzyqemO63BHn0Olbi3REkd61RxW3cE4lbSts3OI0GfnT57/Nw6/YfLAZQ69eCz0eEckVjPmbt7evb8lYo5gEpzm+wf5LVPaAzZWVj/gSQywzPKA8zoz4q6hl4zuAd3647Y3smuWVI8PpQzRwt5vP8d07Qt39p+/zEOrcGRDo";
        //parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
//parameters.addWebcamCalibrationFile("/storage/self/primary/FIRST/webcamcalibrations/teamwebcamcalibrations.xml");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        trackables = vuforia.loadTrackablesFromAsset("Skystone");
        trackables.activate();
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            RobotLog.e("-4");
            if (!aPressed) {
                RobotLog.e("-3");
                try {
                    RobotLog.e("-2");
                    VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
                    RobotLog.e("-1");
                    for (int i = 0; i < frame.getNumImages(); i++)
                        if (frame.getImage(i).getFormat() == RGB565) {
                            RobotLog.e("0");
                            Image image = frame.getImage(i);
                            RobotLog.e("1");
                            Bitmap bmp = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
                            RobotLog.e("2");
                            bmp.copyPixelsFromBuffer(image.getPixels());
                            RobotLog.e("3");
                            FileOutputStream output = new FileOutputStream(new File("/storage/self/primary/" + Math.random() + ".png"));
                            RobotLog.e("4");
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
                            RobotLog.e("success");
                            line = "capture taken";
                        }
                } catch (Exception exception) {
                    line = exception.getMessage();
                }
            }
            aPressed = true;
        } else {
            aPressed = false;
        }

        telemetry.addData("a",gamepad1.a);
        telemetry.addData("aPressed", aPressed);
        telemetry.addLine(line);
        telemetry.update();
    }
}
