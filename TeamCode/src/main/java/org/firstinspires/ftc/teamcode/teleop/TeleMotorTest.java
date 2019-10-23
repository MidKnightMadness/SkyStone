package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.security.Policy;

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
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //motor1 is front left
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //motor2 is front right
        motor3 = hardwareMap.dcMotor.get("motor3");
        motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //motor3 is back left
        motor4 = hardwareMap.dcMotor.get("motor4");
        motor4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //motor4 is back right
    }

    @Override
    public void loop() {
        //turn left (counter-clockwise)
        if (gamepad1.left_trigger != 0) {
            motor1.setPower(POWER_CONSTANT);
            motor2.setPower(POWER_CONSTANT);
            motor3.setPower(POWER_CONSTANT);
            motor4.setPower(POWER_CONSTANT);
        }
        //turn right (clockwise)
        if (gamepad1.right_trigger != 0) {
            motor1.setPower(-POWER_CONSTANT);
            motor2.setPower(-POWER_CONSTANT);
            motor3.setPower(-POWER_CONSTANT);
            motor4.setPower(-POWER_CONSTANT);
        }
        //scoot left
        if (gamepad1.dpad_left) {
            motor1.setPower(POWER_CONSTANT);
            motor2.setPower(POWER_CONSTANT);
            motor3.setPower(-POWER_CONSTANT);
            motor4.setPower(-POWER_CONSTANT);
        }
        //scoot right
        if (gamepad1.dpad_right) {
            motor1.setPower(-POWER_CONSTANT);
            motor2.setPower(-POWER_CONSTANT);
            motor3.setPower(POWER_CONSTANT);
            motor4.setPower(POWER_CONSTANT);
        }
        //forward
        if (gamepad1.dpad_up) {
            motor1.setPower(-POWER_CONSTANT);
            motor2.setPower(POWER_CONSTANT);
            motor3.setPower(-POWER_CONSTANT);
            motor4.setPower(POWER_CONSTANT);
        }
        //backward
        if (gamepad1.dpad_down) {
            motor1.setPower(POWER_CONSTANT);
            motor2.setPower(-POWER_CONSTANT);
            motor3.setPower(POWER_CONSTANT);
            motor4.setPower(-POWER_CONSTANT);
        }
        //stop
        if (gamepad1.a){
            motor1.setPower(0);
            motor2.setPower(0);
            motor3.setPower(0);
            motor4.setPower(0);
        }
        if (gamepad1.b){
            motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
        telemetry.addLine(String.valueOf(motor1.getCurrentPosition()));
        telemetry.addLine(String.valueOf(motor2.getCurrentPosition()));
        telemetry.addLine(String.valueOf(motor3.getCurrentPosition()));
        telemetry.addLine(String.valueOf(motor4.getCurrentPosition()));
        telemetry.update();
    }
}
