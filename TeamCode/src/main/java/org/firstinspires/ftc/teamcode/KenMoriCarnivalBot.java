package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;


@TeleOp
public class KenMoriCarnivalBot extends OpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("leftmotor");
        rightMotor = hardwareMap.dcMotor.get("rightmotor");
        telemetry.addLine("Telemetry has the ability to function!");





    }

    @Override
    public void loop() {
        leftMotor.setPower(gamepad1.left_stick_y/3);
        rightMotor.setPower(-gamepad1.right_stick_y/3);
        telemetry.addData("right joystick x", gamepad1.right_stick_x);
        telemetry.addData("right joystick y", gamepad1.right_stick_y);
        telemetry.addData("left joystick x", gamepad1.left_stick_x);
        telemetry.addData("left joystick y", gamepad1.left_stick_y);
        telemetry.update();

    }
}
