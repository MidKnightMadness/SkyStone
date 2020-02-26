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
import java.io.PrintWriter;
import java.io.File;

@Disabled
@Autonomous
public class MainAutonomousRed extends LinearOpMode {

    private Drive drive = new NewMechanumWheels();
    private Visual visual = new VuforiaPosition();
    private Navigation navigation = new Navigation(null, drive);
    private Delivery delivery = new Elevator();
    private Grabber grabber = new Claw();
    private FoundationMover foundationMover = new ActualFoundationMover();
    private StoneAngle stoneAngle = new StoneAngle();
    private ElapsedTime runtime = new ElapsedTime();
    
    Rev2mDistanceSensor distanceSensorLeft;
    Rev2mDistanceSensor distanceSensorRight;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Starting");
        telemetry.update();
        Assembly.initialize(telemetry, hardwareMap, drive, visual, navigation, delivery, grabber, foundationMover, stoneAngle);
        telemetry.addLine("Finish Starting");
        telemetry.update();
        distanceSensorLeft = hardwareMap.get(Rev2mDistanceSensor.class, "distanceleftside");
        distanceSensorRight = hardwareMap.get(Rev2mDistanceSensor.class, "distancerightside");
        grabber.rotate(0.5);

        while (!isStarted() && !isStopRequested())
            idle();

        drive.resetPosition(new Position(Distance.fromInches(0), Distance.fromInches(0), Angle.fromDegrees(90)));

        //move to stones
        Position targetPos = new Position(Distance.fromInches(10), Distance.fromInches(-10), Angle.fromDegrees(0));
        
        delivery.setHeight(0.15);
        boolean isAligned = false;
        runtime.reset(); 
        while (runtime.seconds() < 3 && !isStopRequested()) {
            telemetry.addLine("moving to stone");
    
            targetPos.setY(navigation.getPosition().getY().copy().subtract(Distance.fromInches(stoneAngle.stonePosition()[0] - 2.5)));
            drive.setTarget(targetPos, navigation);

            if(delivery.isComplete())
                delivery.setDepthRaw(-3200);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        delivery.setDepthRaw(-3200);
        
        //move sideways and scan
        drive.setDirection(Angle.aTan(-1, 0), 0.25, 0);
        try {
            PrintWriter writer = new PrintWriter(new File("/storage/self/primary/FIRST/java/src/org/firstinspires/ftc/teamcode/autonoumous/log.txt"));
        
        boolean[] isBlack = {false, false};
        while (!isStopRequested()) {
            telemetry.addLine("moving sideways");

            isBlack = visual.isBlack(writer);
            telemetry.addData("isBlackRight", isBlack[0]);
            telemetry.addData("isBlackLeft", isBlack[1]);
            if (isBlack[0] || isBlack[1])
                break;

            drive.setDirection(Angle.aTan(-1, -(stoneAngle.stonePosition()[0] - 2.9) * 0.6 + 0.6), 0.25, 0);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        //align distance to stone
        runtime.reset(); 
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("aligning distance to stone");
            
            targetPos.setX(navigation.getPosition().getX().copy());
            targetPos.setY(navigation.getPosition().getY().copy().subtract(Distance.fromInches(stoneAngle.stonePosition()[0] - 2.9)));
            drive.setTarget(targetPos, navigation);
            
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        //align sideways
        drive.setDirection(Angle.fromDegrees(90), 0, 0);
        drive.update();
        boolean[] oldBlack = isBlack;
        isBlack = visual.isBlack(writer);
        telemetry.clear();
        telemetry.addData("isBlackRight", isBlack[0]);
        telemetry.addData("isBlackLeft", isBlack[1]);
        telemetry.update();
        if ((isBlack[0] && !isBlack[1]) || (isBlack[1] && !isBlack[0]) || (!isBlack[0] && !isBlack[1])) {
            drive.setDirection(Angle.fromDegrees(isBlack[0] ? -80 : isBlack[1] ? 80 : oldBlack[0] ? -80 : 80), 0.45, 0);
            drive.update();
            Thread.sleep(500);
        }
        
        drive.setDirection(Angle.fromDegrees(90), 0, 0);
        drive.update();
        
        
        
        //grab the skystone
        telemetry.addLine("getting stone");
        telemetry.update();
        grabber.release();
        delivery.setHeight(0.03);
        while (!delivery.isComplete() && !isStopRequested()) {
            idle();
        }
        
        //drive backwards
        telemetry.addLine("moving backwards");
        telemetry.update();
        targetPos.setY((navigation.getPosition().getY().copy().add(Distance.fromInches(8))));
        targetPos.setX(navigation.getPosition().getX().copy());
        drive.setTarget(targetPos, navigation);
        drive.update();
        Thread.sleep(500);
        drive.setDirection(Angle.fromDegrees(0), 0, 0);
        drive.update();
        //sensor blocked
        delivery.setHeight(0.02);
        int blocked = stoneAngle.sensorBlocked();
        telemetry.clear();
        telemetry.addData("BLOCKED", blocked);
        telemetry.update();
        //Thread.sleep(1000);
        if(blocked != 0)
            drive.setDirection(Angle.fromDegrees(90 * -blocked), 0.3, 0);
        else
        {   
            drive.setDirection(Angle.fromDegrees(90), 0.35, 0);
            drive.update();
            Thread.sleep(500);
            drive.setDirection(Angle.fromDegrees(-90), 0.4, 0);
        }
        drive.update();
        delivery.setHeight(0.00);
        Thread.sleep(1000);
        drive.setDirection(Angle.fromDegrees(90), 0, 0);
        drive.update();
        
        grabber.grab();
        delivery.setHeight(0.04);
        Thread.sleep(500);

        //turn 90 degrees (left)
        targetPos.setTheta(Angle.fromDegrees(-90));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 1.5 && !isStopRequested()) {
            telemetry.addLine("turning left");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //move sideways
        targetPos.getY().subtract(Distance.fromInches(10));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 1.5 && !isStopRequested()) {
            telemetry.addLine("moving sideways");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        //move down field
        targetPos.setX(Distance.fromInches(-110));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 3.5 && !isStopRequested()) {
            writer.println(runtime.seconds() + ", " + distanceSensorLeft.getDistance(DistanceUnit.INCH));
            telemetry.addLine("moving down field");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        targetPos.setY(navigation.getPosition().getY().copy());
        targetPos.setX(navigation.getPosition().getX().copy());
        targetPos.setTheta(Angle.fromDegrees(0));
        delivery.setHeight(0.15);
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("Rotating");
            idle();
            drive.update();
            delivery.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        /*
        runtime.reset();
        delivery.setHeight(0.015);
        while (runtime.seconds() < 0.5 && !isStopRequested()) {
            telemetry.addLine("moving forwards");
            targetPos.setY(navigation.getPosition().getY().copy().subtract(Distance.fromInches(stoneAngle.stonePosition()[0] - 2)));
            drive.setTarget(targetPos, navigation);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }*/
        
        drive.setDirection(Angle.fromDegrees(0), 0, 0);
        drive.update();
        
        telemetry.addLine("placing stone");
        telemetry.update();
        
        //release stone
        grabber.release();
        delivery.setHeight(0.15);
        Thread.sleep(500);
        
        //turn right
        targetPos.setTheta(Angle.fromDegrees(-90));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("turning right");
            
            if(runtime.seconds() > 1)
                delivery.setHeight(0);
            
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        //move into foundation
        drive.setDirection(Angle.fromDegrees(180), 0.5, 0);
        drive.update();
        Thread.sleep(350);
        drive.setDirection(Angle.fromDegrees(0), 0, 0);
        drive.update();
        
        foundationMover.grab();
        Thread.sleep(100);
        
        //move forwards
        drive.setDirection(Angle.fromDegrees(90), 0.75, 0);
        drive.update();
        Thread.sleep(250);
        drive.setDirection(Angle.fromDegrees(0), 0, 0);
        drive.update();
        
        targetPos.setY(navigation.getPosition().getY().copy());
        targetPos.setX(navigation.getPosition().getX().copy().add(Distance.fromInches(12)));
        targetPos.setTheta(Angle.fromDegrees(180));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 2.5 && !isStopRequested()) {
            telemetry.addLine("Rotating");
            idle();
            drive.update();
            delivery.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        drive.setDirection(Angle.fromDegrees(-20), 0.7, 0);
        drive.update();
        Thread.sleep(1000);
        
        writer.close();
        } catch (Exception e) {
            
        }
        
        foundationMover.reset();
        targetPos.setTheta(Angle.fromDegrees(90));
        targetPos.getX().subtract(Distance.fromInches(-15));
        targetPos.getY().subtract(Distance.fromInches(6));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 3 && !isStopRequested()) {
            telemetry.addLine("Parking");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        /*
        //move down field untill sees foundation
        targetPos.setX(Distance.fromInches(90));
        drive.setTarget(targetPos, navigation);
        delivery.setHeight(0.15);
        while (distanceSensorLeft.getDistance(DistanceUnit.INCH) > 15 && !isStopRequested()) {
            telemetry.addLine("moving down field to foundation");
            telemetry.addData("distLeft",distanceSensorLeft.getDistance(DistanceUnit.INCH));
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //targetPos.setX(navigation.getPosition().getX().copy());
        telemetry.addLine("found foundation");
        telemetry.update();
        drive.setDirection(Angle.fromDegrees(180), 1, 0);
        drive.update();
        Thread.sleep(1000);
        drive.setDirection(Angle.fromDegrees(0), 0, 0);
        drive.update();
        //Thread.sleep(3000);
        
        //move a little further
        //targetPos.setX(navigation.getPosition().getX().copy().add(Distance.fromInches(15)));
        //targetPos.getY().add(Distance.fromInches(5));
        //drive.setTarget(targetPos, navigation);
        //runtime.reset(); 
        //while (runtime.seconds() < 1.5 && !isStopRequested()) {
        //    telemetry.addLine("moving further");
        //    idle();
        //    drive.update();
        //    visual.update();
        //    navigation.update();
        //    telemetry.update();
        //}
        
        //turn right to foundation
        targetPos.setX(navigation.getPosition().getX().copy());
        targetPos.setTheta(Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("turning right");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        //move forwards
        runtime.reset();
        while (runtime.seconds() < 0.5 && !isStopRequested()) {
            telemetry.addLine("moving forwards");
            targetPos.setY(navigation.getPosition().getY().copy().subtract(Distance.fromInches(stoneAngle.stonePosition()[0] - 2)));
            drive.setTarget(targetPos, navigation);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        drive.setDirection(Angle.fromDegrees(0), 0, 0);
        drive.update();
        
        telemetry.addLine("placing stone");
        telemetry.update();
        delivery.setHeight(0.015);
        Thread.sleep(700);
        
        //release stone
        grabber.release();
        delivery.setHeight(0.15);
        Thread.sleep(500);
        
        //turn right
        targetPos.setTheta(Angle.fromDegrees(-90));
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("turning right");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //move left
        targetPos.getY().subtract(Distance.fromInches(6));
        targetPos.setX(navigation.getPosition().getX().copy());
        drive.setTarget(targetPos, navigation);
        runtime.reset(); 
        while (runtime.seconds() < 1 && !isStopRequested()) {
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
        drive.setDirection(Angle.fromDegrees(-20), 1, 0);
        runtime.reset(); 
        while (runtime.seconds() < 4 && !isStopRequested()) {
            telemetry.addLine("move foundation");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        
        //move under bridge
        foundationMover.reset();
        delivery.setHeight(0);
        drive.setDirection(Angle.fromDegrees(-90), 1, 0);
        drive.update();
        runtime.reset(); 
        while (runtime.seconds() < 2 && !isStopRequested()) {
            telemetry.addLine("move under bridge");
            
            if(runtime.seconds() > 0.5)
                drive.setDirection(Angle.fromDegrees(-150), 1, 0);
            
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }*/
        drive.setDirection(Angle.fromDegrees(0), 0, 0);
        drive.update();
        
        
        telemetry.addLine("finished");
        telemetry.update();
        
        visual.stop();
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
