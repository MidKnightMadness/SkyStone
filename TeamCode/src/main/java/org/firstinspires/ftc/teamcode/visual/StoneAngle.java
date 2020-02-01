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

    //returns a distance (in) and angle (degrees)
    public double[] stonePosition() {
        //sensor readings
        double leftDist = distSensorLeft.getDistance(DistanceUnit.INCH);
        double rightDist = distSensorRight.getDistance(DistanceUnit.INCH);
        telemetry.addData("left", leftDist);
        telemetry.addData("right", rightDist);

        double distance = Math.abs(leftDist - rightDist) > 4 ? Math.min(leftDist, rightDist) : (leftDist + rightDist) / 2;

        //distance and angle
        return new double[] {
            distance,
            Math.atan2(leftDist - rightDist, 12) / Math.PI * 180
        };
    }
    
    public int sensorBlocked()
    {
        double leftDist = distSensorLeft.getDistance(DistanceUnit.INCH);
        double rightDist = distSensorRight.getDistance(DistanceUnit.INCH);
        telemetry.addData("left", leftDist);
        telemetry.addData("right", rightDist);
        if(Math.abs(leftDist - rightDist) > 2)
            if(leftDist>rightDist)
                return 1;
            else
                return -1;
        else 
            return 0;
    }
}
