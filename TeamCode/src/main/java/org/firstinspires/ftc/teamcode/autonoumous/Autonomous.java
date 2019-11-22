package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.delivery.Delivery;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.foundationMover.FoundationMover;
import org.firstinspires.ftc.teamcode.grabber.Grabber;
import org.firstinspires.ftc.teamcode.intake.Intake;
import org.firstinspires.ftc.teamcode.visual.Visual;


public class Autonomous extends LinearOpMode {
private Drive drive;
private Visual visual;
private Delivery delivery;
private Intake intake;
private Grabber grabber;
private FoundationMover foundationMover;

    @Override
    public void runOpMode() throws InterruptedException {
    //*********NOTE**********//
        //This is an idea on how autonomous runs, may change later
        //These measurements are not accurate, this is just the general layout on how autonomous is going to function
    //*********AUTONOMOUS START************//
        //Position(x, y, -90)// Move the robot facing the stone
        //MoveTo(Position Target)//Move the robot until the intake is facing the first stone
        //*******NOTE**********//
            // Ground level is set to 0
        //Intake.spin()//Takes the stone
        //Grabber.init()//Grab the stone
        //Position(x, y, 0)//Move the robot so its facing the side with the foundation
        //MoveTo(Position target)//Move the robot to the foundation(Specific Location)
        //Position(x, y, -90)//Robot is facing the foundation
        //********NOTE*********//
            // The foundation height is set to 2)
        //Delivery.setHeight(0)//Delivery moves claw to foundation
        //Delivery.setDepth(0)//Delivery moves claw to foundation
        //Grabber.release()//Release stone on the foundation
        //Delivery.setHeight()//Reset the delivery system
        //Delivery.setDepth()//Reset the delivery system
        //Foundation.ready()//Robot is ready to grab the foundation
        //Foundation.grab()//Robot grabs the foundation
        //Position(90)//Face opposite way
        //MoveTo(Position target)// Move the robot foundation is close to wall
        //Position(x, y, 0)//Foundation is facing the wall
        //Foundation.reset//Drop Foundation
        //Position(x, y, 180)// Robot is facing the opposite side
        //MoveTo(Position target)//Move the robot to the other side until the side is facing the block
        //Position(x, y, -90)//Robot is facing the block
        //MoveTo(Position target)// Move the robot backwards or forwards until the grabber is facing the second block
        //Intake.spin()//Takes in the block
        //Grabber.init()//Grabs the block
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

        //Programming this later when everything is done


//Actual code:

 //   /*******TAKING FIRST SKYSTONE*********/
 //       Position firstSkyStone = null;//visual.findSkystone(); //Move the robot facing the stone
 //       drive.setTarget(firstSkyStone);//Move the robot until the intake is facing the first stone
 //       intake.spin(1);//Takes the stone(NOTE: change the power)
 //       grabber.grab();//Grab the stone


 //   /*******MOVING TO THE FOUNDATION*********/
 //       Position faceFoundationSideFront = AutonomousConstants.FACE_FOUNDATION_SIDE_FRONT;//Move the robot so its facing the side with the foundation
 //       drive.setTarget(faceFoundationSideFront);//Move the robot to the foundation(Specific Location)
 //       Position faceFoundationInitialPlace = AutonomousConstants.FACE_FOUNDATION_INITIAL_PLACE;//Robot is facing the foundation
 //       drive.setTarget(faceFoundationInitialPlace);//Moves to the foundation


 //   /*******DELIVERING STONE ON THE FOUNDATION********/
 //       delivery.setHeight(2);//Delivery moves claw to foundation
 //       delivery.setDepth(12);//Delivery moves claw to foundation
 //       grabber.release();//Release stone on the foundation
 //       delivery.setDepth(0);//Reset the delivery system
 //       delivery.setHeight(0);//Reset the delivery system


 //   /*******MOVING THE FOUNDATION*******/
 //       foundationMover.prepare();//Robot is ready to grab the foundation
 //       foundationMover.grab();//Robot grabs the foundation
 //       Position faceFoundationSideRight = AutonomousConstants.FACE_FOUNDATION_SIDE_RIGHT;//Face opposite way
 //       drive.setTarget(faceFoundationSideRight);// Move the robot foundation is close to wall
 //       foundationMover.reset(); //Drop Foundation
 //       Position faceFoundationSideLeft = AutonomousConstants.FACE_FOUNDATION_SIDE_LEFT;//Face away from placed foundation
 //       drive.setTarget(faceFoundationSideLeft);//Move away from the placed foundation


 //   /********MOVING TO SECOND SKYSTONE********/
 //       Position faceDeportSideFront = AutonomousConstants.FACE_DEPOT_SIDE_FRONT;//Face robot to the depot side
 //       drive.setTarget(faceDeportSideFront);//Move robot to depot side until it is at the second skystone
 //       Position secondSkyStone  = null;//visual.findSkystone();//Face robot to the second skystone
 //       drive.setTarget(secondSkyStone);


 //   /********TAKING SECOND SKYSTONE*******/
 //       intake.spin(1);//Take in the block(Note: change power)
 //       grabber.grab();//Grab the block


 //   /********MOVE TO FOUNDATION*******/
 //       drive.setTarget(faceFoundationSideFront);
 //       Position faceFoundationFinalPlace = AutonomousConstants.FACE_FOUNDATION_FINAL_PLACE;
 //       drive.setTarget(faceFoundationFinalPlace);


 //   /********DELIVERING THE SECOND SKYSTONE ON THE FOUNDATION**********/
 //       delivery.setHeight(2);//Delivery moves claw to foundation(NOTE: change it)
 //       delivery.setDepth(12);//Delivery moves claw to foundation(NOTE: change it)
 //       grabber.release();//release second stone on the first skystone
 //       delivery.setDepth(0);//Reset delivery system
 //       delivery.setHeight(0);//Reset delivery system


 //   /**********PARKING UNDER THE ALLIANCE BRIDGE*********/
 //       Position faceAllianceBridge = AutonomousConstants.SKYBRIDGE_PARKING;
 //       drive.setTarget(faceAllianceBridge);//













    }
}
