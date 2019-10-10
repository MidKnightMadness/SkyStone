package org.firstinspires.ftc.teamcode.visual;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.camera.CameraImpl;

@TeleOp
public class VuforiaTest extends OpMode {

    private VuforiaLocalizer vuforia;

    public VuforiaTest() {
        RobotLog.d("qwertyuiopasdfghjklzxcvbnm");
        msStuckDetectInit = 20000;
    }

    @Override
    public void init() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId",
                "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "Ae7oRjb/////AAABmV3pkVnpEU9Pv3XaN0o2EZ5ttngvTMliTd5nX0843lAXhah50oPXg63sdsiK9/BFMjXkw9lMippdx4bHQo5kycWr1GcFcv+QlVNEpSclUqu9Zzj4FYVl+J2ScSAXSyuCRWMRWd3AikCfhAtlwFe7dnMIfpVniU8Yr8o3YumS2/5LjNU2wIkiJak5IHlnugT414wsrzyqemO63BHn0Olbi3REkd61RxW3cE4lbSts3OI0GfnT57/Nw6/YfLAZQ69eCz0eEckVjPmbt7evb8lYo5gEpzm+wf5LVPaAzZWVj/gSQywzPKA8zoz4q6hl4zuAd3647Y3smuWVI8PpQzRwt5vP8d07Qt39p+/zEOrcGRDo";
        //parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.cameraName = hardwareMap.get(WebcamName.class,"webcam");
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables trackables = vuforia.loadTrackablesFromAsset("Skystone");
        trackables.activate();
    }

    @Override
    public void loop() {

    }
}
