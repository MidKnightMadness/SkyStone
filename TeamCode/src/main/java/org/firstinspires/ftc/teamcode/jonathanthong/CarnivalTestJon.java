package org.firstinspires.ftc.teamcode.jonathanthong;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class CarnivalTestJon extends OpMode {
    private DcMotor leftMotor;
    private DcMotor rightMotor;


    @Override
    public void init() {
        telemetry.addLine("Init :: You Started the Program!");
        leftMotor = hardwareMap.dcMotor.get("leftmotor");
        rightMotor = hardwareMap.dcMotor.get("rightmotor");
    }

    @Override
    public void loop() {
        double rightstickY100 = -100*gamepad1.right_stick_y;
        telemetry.addData("Right Stick Y Output", rightstickY100);
        leftMotor.setPower(gamepad1.left_stick_y);
        //if (gamepad1.right_stick_y > 0)
        //    rightMotor.setPower(1);
        //if
        rightMotor.setPower(gamepad1.right_stick_y);

    }

}
