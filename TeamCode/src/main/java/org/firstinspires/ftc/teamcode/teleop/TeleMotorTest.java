package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TeleMotorTest extends TeleMotor {

    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;
    public double POWER_CONSTANT = 0.1;

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3 = hardwareMap.dcMotor.get("motor3");
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4 = hardwareMap.dcMotor.get("motor4");
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_left){
            motor1.setPower(POWER_CONSTANT);
            motor2.setPower(POWER_CONSTANT);
            motor3.setPower(-POWER_CONSTANT);
            motor4.setPower(-POWER_CONSTANT);
        }
        if(gamepad1.dpad_right){
            motor1.setPower(-POWER_CONSTANT);
            motor2.setPower(-POWER_CONSTANT);
            motor3.setPower(POWER_CONSTANT);
            motor4.setPower(POWER_CONSTANT);
        }
        if(gamepad1.dpad_down){
            motor1.setPower(-POWER_CONSTANT);
            motor2.setPower(POWER_CONSTANT);
            motor3.setPower(-POWER_CONSTANT);
            motor4.setPower(POWER_CONSTANT);
        }
        if(gamepad1.dpad_up)
            motor1.setPower(POWER_CONSTANT);
            motor2.setPower(-POWER_CONSTANT);
            motor3.setPower(POWER_CONSTANT);
            motor4.setPower(-POWER_CONSTANT);
    }
}
