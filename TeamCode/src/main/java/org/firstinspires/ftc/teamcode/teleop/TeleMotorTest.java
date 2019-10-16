package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TeleMotorTest extends TeleMotor {

    DcMotor motor1;
    DcMotor motor2;


    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        if(gamepad1.left_stick_y > 0){
            motor1.setPower(0.7);
            motor2.setPower(-0.5);
        }
        if(gamepad1.left_stick_y < 0){
            motor1.setPower(-0.7);
            motor2.setPower(0.5);
        }
        if(gamepad1.left_stick_button){
            motor1.setPower(0);
            motor2.setPower(0);
        }
    }
}
