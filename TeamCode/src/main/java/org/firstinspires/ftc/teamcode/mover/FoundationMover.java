package org.firstinspires.ftc.teamcode.mover;


import com.qualcomm.robotcore.hardware.Servo;

public class FoundationMover extends Mover {

    private Servo servoLeft;
    private Servo servoRight;

    @Override
    public void init() {
        servoLeft = hardwareMap.servo.get("left");
        servoRight = hardwareMap.servo.get("right");

    }

    @Override
    public void loop() {
        if (gamepad1.a){
            holdFoundation();
        } else if (gamepad1.b){
            releaseFoundation();
        }
    }

    //****************************************
    //Assumes that 0 position is down and 1 position is up
    //TODO: Check the hardware and find which way is which
    //****************************************

    public void holdFoundation(){
        servoLeft.setPosition(0);
        servoRight.setPosition(0);
    }

    public void releaseFoundation(){
        servoLeft.setPosition(1);
        servoRight.setPosition(1);
    }
}
