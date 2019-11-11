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
    private boolean yPressed;
    private boolean xPressed;

    @Override
    public void init() {
        servo = hardwareMap.servo.get("servo");
        rotateservo = hardwareMap.servo.get("rotateservo");
    }

    @Override
    public void loop() {


        telemetry.addData("positionofServo", gamepad1.y);
        telemetry.addData("rotationofServo", gamepad1.x);

    }
}
