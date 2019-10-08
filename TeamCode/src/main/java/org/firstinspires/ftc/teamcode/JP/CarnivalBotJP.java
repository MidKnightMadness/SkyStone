package org.firstinspires.ftc.teamcode.JP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class CarnivalBotJP extends OpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("leftmotor");
        rightMotor = hardwareMap.dcMotor.get("rightmotor");
    }

    @Override
    public void loop() {
        leftMotor.setPower(gamepad1.left_stick_y);
        rightMotor.setPower(-gamepad1.right_stick_y);
        telemetry.addData("left joystick vertical", gamepad1.left_stick_y);
        telemetry.addData("right joystick veertical", gamepad1.right_stick_y);
        telemetry.update();
    }
}
