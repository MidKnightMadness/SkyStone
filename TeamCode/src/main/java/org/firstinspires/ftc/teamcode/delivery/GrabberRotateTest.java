package org.firstinspires.ftc.teamcode.delivery;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class GrabberRotateTest extends OpMode {
    private Servo servo;

    @Override
    public void init() {
        servo = hardwareMap.servo.get("servo");
    }

    @Override
    public void loop() {
        if (gamepad1.a){
            servo.setPosition(1);
        }
        else{
            servo.setPosition(0.5);
        }
        telemetry.addData("Position", servo.getPosition());

    }
}
