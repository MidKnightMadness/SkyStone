package org.firstinspires.ftc.teamcode.grabber;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//@TeleOp
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
        if(gamepad1.y) {
            if (!yPressed)
                if (rotateservo.getPosition() == 0.24000000000000005)
                    rotateservo.setPosition(0.62);
            else
                rotateservo.setPosition(0.24000000000000005);
            yPressed = true;
    }
        else
            yPressed=false;

        if(gamepad1.x) {
            if (!xPressed)
                if (servo.getPosition() > 0.2)
                    servo.setPosition(0);
                else if (servo.getPosition() < -0.2)
                    servo.setPosition(0.2);
                else
                    servo.setPosition(0.25);
            xPressed = true;
        }
        else
            xPressed=false;


        telemetry.addData("positionofServo", gamepad1.x);
        telemetry.addData("rotationofServo", gamepad1.y);
        telemetry.addData("positionoservo", servo.getPosition());
        telemetry.addData("positionofrotateServo", rotateservo.getPosition());
        telemetry.update();

    }
}
