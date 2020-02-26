package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.delivery.Delivery;
import org.firstinspires.ftc.teamcode.delivery.Elevator;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.drive.NewMechanumWheels;
import org.firstinspires.ftc.teamcode.foundationMover.ActualFoundationMover;
import org.firstinspires.ftc.teamcode.foundationMover.FoundationMover;
import org.firstinspires.ftc.teamcode.grabber.Claw;
import org.firstinspires.ftc.teamcode.grabber.Grabber;
import org.firstinspires.ftc.teamcode.intake.Intake;
import org.firstinspires.ftc.teamcode.navigation.Navigation;
import org.firstinspires.ftc.teamcode.visual.StoneAngle;
import org.firstinspires.ftc.teamcode.visual.Visual;
import org.firstinspires.ftc.teamcode.visual.VuforiaPosition;

@Disabled
@Autonomous
public class MainAutonomous_Backup extends LinearOpMode {

    private Drive drive = new NewMechanumWheels();
    private Visual visual = new VuforiaPosition();
    private Navigation navigation = new Navigation(null, drive);
    private Delivery delivery = new Elevator();
    private Grabber grabber = new Claw();
    private FoundationMover foundationMover = new ActualFoundationMover();
    private StoneAngle stoneAngle = new StoneAngle();
    private ElapsedTime runtime = new ElapsedTime();
    
    Rev2mDistanceSensor distanceSensorLeft;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Starting");
        telemetry.update();
        Assembly.initialize(telemetry, hardwareMap, drive, visual, navigation, delivery, grabber, foundationMover, stoneAngle);
        telemetry.addLine("Finish Starting");
        telemetry.update();
        distanceSensorLeft = hardwareMap.get(Rev2mDistanceSensor.class, "distanceleftside");
        grabber.rotate(0.5);

        while (!isStarted() && !isStopRequested())
            idle();

        drive.resetPosition(new Position(Distance.fromInches(0), Distance.fromInches(0), Angle.fromDegrees(90)));

        //find skystone
        Position targetPos = new Position(Distance.fromInches(0), Distance.fromInches(-10), Angle.fromDegrees(0));
        
        //move to stone
        drive.setTarget(targetPos, navigation);
        
        runtime.reset(); 
        while (runtime.seconds() < 0.5 && !isStopRequested()) {
            telemetry.addLine("moving to stone");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        //delivery.setHeight(0.15);
        boolean isAligned = false;
        while (!isStopRequested()) {
            telemetry.addLine("moving to stone");
            
            if(!isAligned)
            {
                visual.getSkystoneOffset();
                double offset = visual.getSkystoneOffset();
                if(offset == -161)
                    offset = visual.getSkystoneOffset();
                if(Math.abs(offset) < 10 || offset == -161)
                {
                    targetPos.setX(navigation.getPosition().getX().copy());
                    isAligned = Math.abs(offset) < 10;
                }
                else
                {
                    targetPos.setX(navigation.getPosition().getX().copy().add(Distance.fromInches(Math.pow(offset, 5) * 0.000000001)));
                    //targetPos.setY(Distance.fromInches(-10));
                }
            }
            if(isAligned)
                targetPos.setY(navigation.getPosition().getY().copy().subtract(Distance.fromInches(stoneAngle.stonePosition()[0] - 2.5)));
                
            if(Math.abs(stoneAngle.stonePosition()[0] - 2.5) < 0.5)
                break;
                
            drive.setTarget(targetPos, navigation);
            telemetry.addData("stoneDist", stoneAngle.stonePosition()[0]);

            //if(delivery.isComplete())
            //    delivery.setDepthRaw(-3200);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }

        //grab the skystone
        telemetry.addLine("getting stone");
        telemetry.update();
        grabber.release();
        while (!delivery.isComplete() && !isStopRequested()) {
            idle();
        }
        delivery.setHeight(0);
        while (!delivery.isComplete() && !isStopRequested()) {
            idle();
        }
        
        //drive backwards
        telemetry.addLine("moving backwards");
        telemetry.update();
        targetPos.setY((navigation.getPosition().getY().copy().add(Distance.fromInches(6))));
        drive.setTarget(targetPos, navigation);
        drive.update();
        Thread.sleep(500);
        
        //grabber.grab();
        //delivery.setHeight(0.03);
/*
        //

        //turn 90 degrees (left)
        targetPos.setTheta(Angle.fromDegrees(90));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("turning left");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //move down field
        targetPos.setX(Distance.fromInches(70));
        targetPos.getY().subtract(Distance.fromInches(5));
        drive.setTarget(targetPos, navigation);
        delivery.setHeight(0.15);
        runtime.reset(); 
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("moving down field");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //move down field
        targetPos.setX(Distance.fromInches(90));
        drive.setTarget(targetPos, navigation);
        while (distanceSensorLeft.getDistance(DistanceUnit.INCH) > 10 && !isStopRequested()) {
            telemetry.addLine("moving down field");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //move down field
        targetPos.getX().add(Distance.fromInches(7));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 0.5 && !isStopRequested()) {
            telemetry.addLine("moving down field");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //turn right to foundation
        targetPos.setX(navigation.getPosition().getX().copy());
        targetPos.setTheta(Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 2.5 && !isStopRequested()) {
            telemetry.addLine("turning right");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //move forwards
        runtime.reset();
        delivery.setDepthRaw(-3350);
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("moving forwards");
            targetPos.setY(navigation.getPosition().getY().copy().subtract(Distance.fromInches(stoneAngle.stonePosition()[0] - 3)));
            drive.setTarget(targetPos, navigation);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        telemetry.addLine("placing stone");
        telemetry.update();
        delivery.setHeight(0.1);
        Thread.sleep(100);
        //release stone
        grabber.release();
        delivery.setHeight(0.15);
        Thread.sleep(500);
        
        //turn right
        targetPos.setTheta(Angle.fromDegrees(-90));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 3 && !isStopRequested()) {
            telemetry.addLine("turning right");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //move left
        targetPos.getY().subtract(Distance.fromInches(10));
        targetPos.setX(navigation.getPosition().getX().copy());
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("move left");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //move foundation
        foundationMover.grab();
        Thread.sleep(100);
        targetPos.getY().add(Distance.fromInches(40));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 5 && !isStopRequested()) {
            telemetry.addLine("move foundation");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        
        //move to a stable position
        delivery.setHeight(0.0);
        while(!drive.isComplete()&&!isStopRequested()) {
            telemetry.addLine("finished");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        Thread.sleep(5000);
        visual.stop();
        */
    }
}

//changed files:
//MainAutonomous
//Visual
//ManualReset
//VuforiaPosition
//SkystoneAlignmentTest
//DistSensorTest
//Claw
//FoundationMoverTest
//ActualFoundationMover
