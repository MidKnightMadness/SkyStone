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
    protected void init() {

        clawservo = hardwareMap.servo.get("clawservo");
    }

    @Override
    protected void grab() {
        if(yPressed){

        } else{
            yPressed = true;
            servoRunning = !servoRunning;
        }

    } else {
        yPressed = false;
    }

        if (servoRunning){
            clawservo.setPosition();
    } else {
        motor.setPower(0);
    }
    }


    @Override
    protected void release() {

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

