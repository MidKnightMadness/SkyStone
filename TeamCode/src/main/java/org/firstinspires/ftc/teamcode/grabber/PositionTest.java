package org.firstinspires.ftc.teamcode.grabber;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class PositionTest extends OpMode {
    private Servo servo;
    private Servo rotateservo;

    @Override
    public void init() {
        servo = hardwareMap.servo.get("servo");
        rotateservo = hardwareMap.servo.get("rotateservo");
    }

    @Override
    public void loop() {
        servo.setPosition(gamepad1.left_stick_y);

        if (servo.getPosition() > 0.6) {
            servo.setPosition(-1);
        }
        if (servo.getPosition() < -1) {
            servo.setPosition(1);
        }


        telemetry.addData("positionofServo", gamepad1.y);
        telemetry.addData("rotationofServo", gamepad1.x);

    }
}
