package org.firstinspires.ftc.teamcode.mover;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class FoundationMover extends Mover {

    private Servo servoLeft;
    //private Servo servoRight;

    @Override
    public void init() {
        servoLeft = hardwareMap.servo.get("left");
        //servoRight = hardwareMap.servo.get("right");

    }

    @Override
    public void loop() {
        if (gamepad1.a){
            holdFoundation();
        } else if (gamepad1.b){
            releaseFoundation();
        }
    }

    @Override
    public void init_loop() {

    }
    @Override
    public void start() {

    }

    //****************************************
    //Tested: 0 position is up and 1 position is down
    //****************************************

    public void holdFoundation(){
        servoLeft.setPosition(1);
        //servoRight.setPosition(0);
    }

    public void releaseFoundation(){
        servoLeft.setPosition(0);
        //servoRight.setPosition(1);
    }
}
