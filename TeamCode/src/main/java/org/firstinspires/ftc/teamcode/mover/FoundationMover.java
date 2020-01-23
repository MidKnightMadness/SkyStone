package org.firstinspires.ftc.teamcode.mover;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.Config;

@Disabled
public class FoundationMover extends Mover {

    private Servo servoLeft;
    private Servo servoRight;
    private int buttoncounter = 0;

    @Override
    public void init() {
        servoLeft = hardwareMap.servo.get(Config.Mover.MOVER_SERVO_LEFT);
        servoRight = hardwareMap.servo.get(Config.Mover.MOVER_SERVO_RIGHT);

    }

    @Override
    public void loop() {
        if (gamepad1.dpad_left && buttoncounter == 0) {
            holdFoundation();
            buttoncounter = 30;

        } else if (gamepad1.dpad_right && buttoncounter == 0){
            releaseFoundation();
            buttoncounter = 30;
        }

        buttoncounter = Math.max(buttoncounter-1, 0);
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
        servoRight.setPosition(0);
    }

    public void releaseFoundation(){
        servoLeft.setPosition(0);
        servoRight.setPosition(1);
    }
}
