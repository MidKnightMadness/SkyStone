package org.firstinspires.ftc.teamcode.visual.test;

import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaBase;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.visual.Visual;

public class VuforiaRobotNavigation extends Visual {

    private VuforiaSkyStone vuforia = new VuforiaSkyStone();

    @Override
    public void init() {
        vuforia.initialize(
                // Vuforia License key (developer.vuforia.com):
                "Ae7oRjb/////AAABmV3pkVnpEU9Pv3XaN0o2EZ5ttngvTMliTd5nX0843lAXhah50oPXg63sdsiK9/BFMjXkw9lMippdx4bHQo5kycWr1GcFcv+QlVNEpSclUqu9Zzj4FYVl+J2ScSAXSyuCRWMRWd3AikCfhAtlwFe7dnMIfpVniU8Yr8o3YumS2/5LjNU2wIkiJak5IHlnugT414wsrzyqemO63BHn0Olbi3REkd61RxW3cE4lbSts3OI0GfnT57/Nw6/YfLAZQ69eCz0eEckVjPmbt7evb8lYo5gEpzm+wf5LVPaAzZWVj/gSQywzPKA8zoz4q6hl4zuAd3647Y3smuWVI8PpQzRwt5vP8d07Qt39p+/zEOrcGRDo",

                //VuforiaLocalizer.CameraDirection.BACK, // Phone back camera
                hardwareMap.get(WebcamName.class, "Webcam 1"), // webcam hardware device

                "teamwebcamcalibrations.xml", //the name of the webcam calibration file (Sent to RC using OnBotJava interface)

                false, // extended tracking is not supported by this webcam

                true, // Enable camera monitoring (the axes or teapot)

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

        for (String name : VuforiaSkyStone.TRACKABLE_NAMES) {
            VuforiaBase.TrackingResults track = vuforia.track(name);
            if (track.isVisible) {
                x += track.x;
                y += track.y;
                theta += track.xAngle;
                count++;
            }
        }

        telemetry.addData("x", x);
        telemetry.addData("y", y);
        telemetry.addData("theta", theta);
        telemetry.addData("count", count);

        if (count == 0)
            return null;
        else
            return new Position(Distance.fromMillimeters(x / count), Distance.fromMillimeters(y / count), Angle.fromDegrees(theta / count));
    }

    @Override
    public SkystoneSetup findSkystone() {
        return null;
    }

    @Override
    public double getSkystoneOffset() {
        return 0;
    }
    
    @Override
    public void stop() {

    }

}
