package org.firstinspires.ftc.teamcode.grabber;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends Grabber  {
    private Servo clawservo;

    @Override
    public void init() {
        clawservo = hardwareMap.servo.get("clawservo");
    }

    @Override
    public void grab() {
        clawservo.setPosition(1);
    }

    @Override
    public void release() {
        clawservo.setPosition(0.6);
    }

}

