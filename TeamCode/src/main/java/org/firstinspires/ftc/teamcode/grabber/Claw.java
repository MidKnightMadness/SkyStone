package org.firstinspires.ftc.teamcode.grabber;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.HardwareConfig;

public class Claw extends Grabber  {
    private Servo clawservo;

    @Override
    public void init() {
        clawservo = hardwareMap.servo.get(HardwareConfig.GRABBER_SERVO);
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

