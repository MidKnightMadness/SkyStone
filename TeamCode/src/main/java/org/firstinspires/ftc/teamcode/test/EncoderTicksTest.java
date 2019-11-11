package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class EncoderTicksTest extends OpMode {

    DcMotor motor;

    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("motor");
    }

    @Override
    public void loop() {
        telemetry.addData("encoderTicks",motor.getCurrentPosition());
    }
}
