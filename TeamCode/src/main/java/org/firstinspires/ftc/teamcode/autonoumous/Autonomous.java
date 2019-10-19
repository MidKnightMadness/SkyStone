package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Position;


public class Autonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
    //*********NOTE**********/
        //This is an idea on how autonomous runs, may change later
        //These measurements are not accurate, this is just the general layout on how autonomous is going to function
    //*********AUTONOMOUS START************//
        //Position(x, y, -90)// Move the robot facing the stone
        //MoveTo(Position Target)//Move the robot until the grabber is 20 inches away from the first stone
        //*******NOTE**********//
            // Ground level is set to 0
        //Delivery.height(5)//Move the grabber 5 inches from the ground
        //Delivery.depth(20)//Move the grabber 20 inches to the stone
        //Delivery.height(4)//Move the grabber down an inch until it touches the block
        //Grabber.init()//Grab the stone
        //Position(x, y, 0)//Move the robot so its facing the side with the foundation
        //MoveTo(Position target)//Move the robot to the foundation(Specific Location)
        //Position(x, y, -90)//Robot is facing the foundation
        //********NOTE*********//
            // The foundation height is set to 2)
        //Delivery.setHeight(9)//Move the grabber up until it is 5 inches above the foundation
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
        //MoveTo(Position target)// Move the robot backwards or forwards until the grabber is 20 inches away from the second block
        //Delivery.setHeight(5)//Move grabber 5 inches above the ground
        //Delivery.setDepth(20)//Move grabber 20 inches to the stone
        //Delivery.setDepth(4)//Move the grabber down an inch until it touches the block
        //Grabber.init()//Grabs the stone again
        //Position(x, y, 0)//Robot faces the wall with the foundation
        //MoveTo(Position Target)//Robot moves to foundation
        //Delivery.setHeight(15)//Move the grabber up until it is 13 inches away from the foundation
        //Delivery.setDepth(10)//Move the grabber until it is 8 inches from the robot
        //Grabber.release()//Release stone onto the foundation
        //Delivery.setHeight(0)//Reset delivery system
        //Delivery.setDepth(0)//Reset delivery system
        //Position(x, y, 180)//Robot is now facing opposite direction from Foundation
        //MoveTo(Position target)//Robot must park under the alliance bridge
     //********AUTONOMOUS END***********/



    }
}
