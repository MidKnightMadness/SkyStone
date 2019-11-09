package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp
public class TwoMeterDistanceSensorTest extends OpMode {
    private Rev2mDistanceSensor distanceSensor;
    @Override
    public void init() {
        distanceSensor = hardwareMap.get(Rev2mDistanceSensor.class, "distance_sensor");
        Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)distanceSensor;
        telemetry.addData(">>", "Press start to continue");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("deviceName", distanceSensor.getDeviceName() );
        telemetry.addData("range", String.format("%.01f mm", distanceSensor.getDistance(DistanceUnit.MM)));
        telemetry.addData("range", String.format("%.01f cm", distanceSensor.getDistance(DistanceUnit.CM)));
        telemetry.addData("range", String.format("%.01f m", distanceSensor.getDistance(DistanceUnit.METER)));
        telemetry.addData("range", String.format("%.01f in", distanceSensor.getDistance(DistanceUnit.INCH)));
    }
}
