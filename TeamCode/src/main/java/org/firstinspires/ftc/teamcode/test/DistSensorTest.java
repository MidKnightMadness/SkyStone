package org.firstinspires.ftc.teamcode.visual.test;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class DistSensorTest extends OpMode {
    Rev2mDistanceSensor distanceSensorLeft;
    Rev2mDistanceSensor distanceSensorRight;
    @Override
    public void init() {
        distanceSensorLeft = hardwareMap.get(Rev2mDistanceSensor.class, "distanceleftside");
        distanceSensorRight = hardwareMap.get(Rev2mDistanceSensor.class, "distancerightside");
    }

    @Override
    public void loop() {
        telemetry.addData("distanceLeft", distanceSensorLeft.getDistance(DistanceUnit.INCH));
        telemetry.addData("distanceRight", distanceSensorRight.getDistance(DistanceUnit.INCH));
    }
}
