package org.firstinspires.ftc.teamcode.grabber;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Claw extends Grabber  {
    private Servo clawservo;
    private boolean yPressed;
    private boolean servoRunning;

    @Override
    public void init() {

        clawservo = hardwareMap.servo.get("clawservo");
    }

    @Override
    protected void grab() {
        clawservo.setPosition(1);
    }


    @Override
    protected void release() {
        clawservo.setPosition(0.6);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

