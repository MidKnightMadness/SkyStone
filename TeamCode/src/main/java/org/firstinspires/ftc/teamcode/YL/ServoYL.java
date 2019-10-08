package org.firstinspires.ftc.teamcode.YL;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoYL extends OpMode {
    private Servo servo;
    private CRServo crServo;
    private DcMotor motor;
    private double position = 0;
    private boolean pressed;


    @Override
    public void init() {
        servo = hardwareMap.servo.get("servo1");
        crServo = hardwareMap.crservo.get("crServo");
        motor = hardwareMap.dcMotor.get("motor");
        position = 1;
        pressed = false;

    }

    @Override
    public void loop() {
        if (gamepad1.y && !pressed) {
            pressed = true;
        }
        else if (!gamepad1.y && pressed){
            position *= -1;
            pressed = false;
        }

        servo.setPosition(gamepad1.right_trigger);
        crServo.setPower(position);
        motor.setPower(gamepad1.left_stick_y);


        telemetry.addData("rightTrigger", gamepad1.right_trigger);
        telemetry.addData("leftTrigger", gamepad1.left_trigger);
        telemetry.addData("y", gamepad1.y);

    }
}
