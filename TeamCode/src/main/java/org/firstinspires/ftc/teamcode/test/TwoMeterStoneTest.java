package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class TwoMeterStoneTest extends OpMode {

    private Rev2mDistanceSensor distSensorLeft;
    private Rev2mDistanceSensor distSensorRight;

    @Override
    public void init() {
        distSensorLeft = hardwareMap.get(Rev2mDistanceSensor.class, "distanceleft");
        distSensorRight = hardwareMap.get(Rev2mDistanceSensor.class, "distanceright");
    }

    @Override
    public void loop() {
        //sensor readings
        double leftDist = distSensorLeft.getDistance(DistanceUnit.CM);
        double rightDist = distSensorRight.getDistance(DistanceUnit.CM);
        telemetry.addData("left", leftDist);
        telemetry.addData("right", rightDist);

        //distance and rotation
        telemetry.addData("distance", (leftDist + rightDist) / 2);
        telemetry.addData("rotation", 0);
    }
}
