package org.firstinspires.ftc.teamcode.visual;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.common.Assembly;

public class StoneAngle extends Assembly {

    private Rev2mDistanceSensor distSensorLeft;
    private Rev2mDistanceSensor distSensorRight;

    @Override
    public void init() {
        distSensorLeft = hardwareMap.get(Rev2mDistanceSensor.class, "distanceleft");
        distSensorRight = hardwareMap.get(Rev2mDistanceSensor.class, "distanceright");
    }

    public double[] stonePosition() {
        //sensor readings
        double leftDist = distSensorLeft.getDistance(DistanceUnit.CM);
        double rightDist = distSensorRight.getDistance(DistanceUnit.CM);
        //telemetry.addData("left", leftDist);
        //telemetry.addData("right", rightDist);

        //distance and angle
        return new double[] {
            (leftDist + rightDist) / 2,
            Math.atan2(leftDist - rightDist, 12) / Math.PI * 180
        };
    }
}
