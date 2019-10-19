package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TeleMotorTest extends TeleMotor {

    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;
    public double POWER_CONSTANT = 1;

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor1 is front left
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor2 is front right
        motor3 = hardwareMap.dcMotor.get("motor3");
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor3 is back left
        motor4 = hardwareMap.dcMotor.get("motor4");
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motor4 is back right
    }

    @Override
    public void loop() {
        if (gamepad1.left_bumper) {
            motor1.setPower(POWER_CONSTANT);
            motor2.setPower(POWER_CONSTANT);
            motor3.setPower(POWER_CONSTANT);
            motor4.setPower(POWER_CONSTANT);
        }
        if (gamepad1.dpad_left) {
            motor1.setPower(POWER_CONSTANT);
            motor2.setPower(POWER_CONSTANT);
            motor3.setPower(-POWER_CONSTANT);
            motor4.setPower(-POWER_CONSTANT);
        }
        if (gamepad1.dpad_right) {
            motor1.setPower(-POWER_CONSTANT);
            motor2.setPower(-POWER_CONSTANT);
            motor3.setPower(POWER_CONSTANT);
            motor4.setPower(POWER_CONSTANT);
        }
        if (gamepad1.dpad_up) {
            motor1.setPower(-POWER_CONSTANT);
            motor2.setPower(POWER_CONSTANT);
            motor3.setPower(-POWER_CONSTANT);
            motor4.setPower(POWER_CONSTANT);
        }
        if (gamepad1.dpad_down) {
            motor1.setPower(POWER_CONSTANT);
            motor2.setPower(-POWER_CONSTANT);
            motor3.setPower(POWER_CONSTANT);
            motor4.setPower(-POWER_CONSTANT);
        }
        if (gamepad1.a){
            motor1.setPower(0);
            motor2.setPower(0);
            motor3.setPower(0);
            motor4.setPower(0);
        }
        telemetry.addLine(String.valueOf(motor1.getPower()));
        telemetry.addLine(String.valueOf(motor2.getPower()));
        telemetry.addLine(String.valueOf(motor3.getPower()));
        telemetry.addLine(String.valueOf(motor4.getPower()));
        telemetry.update();
    }
}
