package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TeleMotorTest extends TeleMotor {

    DcMotor motor;

    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("motor1");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_left){
            motor.setPower(-1);
        }
        if(gamepad1.dpad_right){
            motor.setPower(1);

        }
        if(gamepad1.dpad_down){
            motor.setPower(0);
        }
    }
}
