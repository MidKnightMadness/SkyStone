package org.firstinspires.ftc.teamcode.grabber;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Claw extends Grabber  {
    private Servo clawservo;
    private boolean yPressed;
    private boolean motorRunning;

    @Override
    protected void init() {

        clawservo = hardwareMap.servo.get("clawservo");
    }

    @Override
    protected void grab() {

    }


    @Override
    protected void release() {

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

