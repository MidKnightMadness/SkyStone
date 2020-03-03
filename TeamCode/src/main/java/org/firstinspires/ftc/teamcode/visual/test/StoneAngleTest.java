package org.firstinspires.ftc.teamcode.visual.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.visual.StoneAngle;

@Disabled
@TeleOp
public class StoneAngleTest extends OpMode {
    StoneAngle stoneAngle = new StoneAngle();

    @Override
    public void init() {
        Assembly.initialize(telemetry, hardwareMap, stoneAngle);
    }

    @Override
    public void loop() {
        telemetry.addData("distance", stoneAngle.stonePosition()[0]);
        telemetry.addData("angle", stoneAngle.stonePosition()[1]);
    }
}
