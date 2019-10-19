package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Position;


public class Autonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
    //*********NOTE**********/
        //This is an idea on how autonomous runs, may change later
    //*********AUTONOMOUS START************/
        //Position(x, y, -90)// Move the robot facing the stone
        //Grabber.init()//Grab the stone
        //Position(x, y, 0)//Move the robot so its facing the side with the foundation
        //MoveTo(Position target)//Move the robot to the foundation(Specific Location)
        //Position(x, y, -90)//Robot is facing the foundation
        //********NOTE*********//
            // The foundation height is set to 0)
        //Delivery.setHeight(7)//Move the grabber up until is it 5 inches above the foundation
        //Delivery.setDepth(10)//Move the grabber until it is 8 inches from the robot
        //Grabber.release()//Release stone on the foundation
        //Delivery.setHeight(0)//Reset delivery system
        //Delivery.setDepth(0)//Reset delivery system
        //Foundation.ready()//Robot is ready to grab the foundation
        //Foundation.grab()//Robot grabs the foundation
        //Position(90)//Face opposite way
        //MoveTo(Position target)// Move the robot foundation is close to wall
        //Position(x, y, 0)//Foundation is facing the wall
        //Foundation.reset//Drop Foundation
        //Position(x, y, 180)// Robot is facing the opposite side
        //MoveTo(Position target)//Move the robot to the other side until the side is facing the block
        //Position(x, y, -90)//Robot is facing the block
        //Grabber.init()//Grabs the block again
        //Position(x, y, 0)//Robot faces the wall with the foundation
        //MoveTo(Position Target)//Robot moves to foundation
        //Delivery.setHeight(13)//Move the grabber up until it is 11 inches away from the foundation
        //Delivery.setDepth(10)//Move the grabber until it is 8 inches from the robot
        //Grabber.release()//Release stone onto the foundation
        //Delivery.setHeight(0)//Reset delivery system
        //Delivery.setDepth(0)//Reset delivery system
        //Position(x, y, 180)//Robot is now facing opposite direction from Foundation
        //MoveTo(Position target)//Robot must park under the alliance bridge
     //********AUTONOMOUS END***********/



    }
}
