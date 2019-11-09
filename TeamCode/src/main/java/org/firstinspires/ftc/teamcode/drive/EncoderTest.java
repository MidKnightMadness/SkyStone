package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class EncoderTest extends Drive {

    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("fl");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor1 is front left
        motor2 = hardwareMap.dcMotor.get("fr");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor2 is front right
        motor3 = hardwareMap.dcMotor.get("bl");
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor3 is back left
        motor4 = hardwareMap.dcMotor.get("br");
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor4 is back right
    }

    @Override
    public void loop() {
        motor1.setPower(gamepad1.left_trigger);
        motor2.setPower(gamepad1.right_trigger);
        motor3.setPower(gamepad2.left_trigger);
        motor4.setPower(gamepad2.right_trigger);
    }


}
