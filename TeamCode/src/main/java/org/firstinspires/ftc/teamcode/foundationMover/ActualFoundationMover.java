package org.firstinspires.ftc.teamcode.foundationMover;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.HardwareConfig;

public class ActualFoundationMover extends FoundationMover {
    private Servo servo1;
    private Servo servo2;

    @Override
    public void init() {
        servo1 = hardwareMap.servo.get(HardwareConfig.FOUNDATION1);
        servo2 = hardwareMap.servo.get(HardwareConfig.FOUNDATION2);
    }

    @Override
    public void prepare() {

    }

    @Override
    public void reset() {
        servo1.setPosition(0);
        servo2.setPosition(1);

    }

    @Override
    public void grab() {
        servo1.setPosition(1);
        servo2.setPosition(0);
    }
}
