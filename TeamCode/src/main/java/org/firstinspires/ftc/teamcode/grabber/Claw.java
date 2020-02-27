package org.firstinspires.ftc.teamcode.grabber;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.HardwareConfig;

public class Claw extends Grabber {
    private Servo clawservo;
    private Servo rotateservo;

    @Override
    public void init() {
        clawservo = hardwareMap.servo.get(HardwareConfig.GRABBER_SERVO);
        rotateservo = hardwareMap.servo.get(HardwareConfig.ROTATE_SERVO);
    }

    @Override
    public void grab() {
        telemetry.addLine("GRABBING!");
        clawservo.setPosition(0.9);
    }

    @Override
    public void release() {
        clawservo.setPosition(0.4);
    }

    @Override
    public void rotate(double angle) { rotateservo.setPosition(((angle / 1.4) + 1) / 2 + 0.02); }

    @Override
    public double getangle() {
        return rotateservo.getPosition();
    }

    @Override
    public void update() {
        super.update();
        telemetry.addData("clawservoposition", clawservo.getPosition());
        telemetry.addData("rotateservoposition", rotateservo.getPosition());
    }
}

