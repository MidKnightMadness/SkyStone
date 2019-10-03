package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTestingJP extends OpMode {

    private Servo servo;
    private CRServo crServo;
    private boolean yPressed;

    @Override
    public void init() {
        servo = hardwareMap.servo.get("servo");
        crServo = hardwareMap.crservo.get("crservo");
    }

    @Override
    public void loop() {
        if(gamepad1.y) {
            if (!yPressed)
                if (servo.getPosition() == 0)
                    servo.setPosition(1);
                else
                    servo.setPosition(0);
            yPressed = true;
        }
        else
            yPressed=false;


        telemetry.addData("y", gamepad1.y);
        telemetry.addData("servi", servo.getPosition());
        telemetry.update();
    }
}
