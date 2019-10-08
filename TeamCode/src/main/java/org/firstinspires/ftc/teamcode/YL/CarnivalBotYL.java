package org.firstinspires.ftc.teamcode.YL;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class CarnivalBotYL extends OpMode {
    private DcMotor leftMotor;
    private  DcMotor rightMotor;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("leftmotor");
        rightMotor = hardwareMap.dcMotor.get("rightmotor");


    }

    @Override
    public void loop() {
        leftMotor.setPower(gamepad1.left_stick_y);
        rightMotor.setPower(-gamepad1.right_stick_y);
        telemetry.addData("leftY", gamepad1.left_stick_y);
        telemetry.addData("rightY", gamepad1.right_stick_y);

    }
}
