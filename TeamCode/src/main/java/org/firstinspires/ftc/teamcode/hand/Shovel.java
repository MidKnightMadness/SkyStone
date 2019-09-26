package org.firstinspires.ftc.teamcode.hand;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.Config;
//@TeleOp
public class Shovel extends Hand {

    private Servo tiltServo;
    
    //Direction to tilt/rotate in, the POV is facing along from the back to front of the hand
    public int TILT_LEFT = 1;
    public int TILT_RIGHT = -1;
    public int TILT_CENTER = 0;
    
    //The (concluded) position to be as close as possile to being parallel to the beam it is mounted on
    public double CENTER_POSITION = 0.38;
    

    @Override
    public void init() {
        //Points tiltServo to the Servo in class Config
        //tiltServo = hardwareMap.servo.get(Config.Hand.HAND_SERVO);
        //Tilts the hand to the Center Position
        tiltHand(TILT_CENTER);
    }

    public void tiltHand(int direction){
        //The 0.25 is the speed in which it rotates
        tiltServo.setPosition(CENTER_POSITION + (0.25 * direction));
    }

    //private double pos = 0.5;
    @Override

    public void loop() {

        if(gamepad2.dpad_left){
            //pos -= 0.005;
            tiltHand(TILT_LEFT);
        } else if(gamepad2.dpad_right){
            //pos += 0.005;
            tiltHand(TILT_RIGHT);
        } else if(gamepad2.dpad_down) {
            tiltHand(TILT_CENTER);
        }
        
        //Debugging
        //tiltServo.setPosition(pos);
        //telemetry.addData("Position", pos);
        //telemetry.update();


    }
}
