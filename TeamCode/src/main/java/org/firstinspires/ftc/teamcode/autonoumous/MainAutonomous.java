package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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

@Autonomous
public class MainAutonomous extends LinearOpMode {

    private Drive drive = new NewMechanumWheels();
    private Visual visual = new VuforiaPosition();
    private Navigation navigation = new Navigation(visual, drive);
    private Delivery delivery = new Elevator();
    private Grabber grabber = new Claw();
    private FoundationMover foundationMover = new ActualFoundationMover();
    private StoneAngle stoneAngle = new StoneAngle();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Starting");
        telemetry.update();
        Assembly.initialize(telemetry, hardwareMap, drive, visual, navigation, delivery, grabber, foundationMover, stoneAngle);
        telemetry.addLine("Finish Starting");
        telemetry.update();

        while (!isStarted() || isStopRequested())
            idle();

        drive.resetPosition(new Position(Distance.fromInches(0), Distance.fromInches(0), Angle.fromDegrees(90)));

        //find skystone
        Position targetPos = new Position(Distance.fromInches(0), Distance.fromInches(-33), Angle.fromDegrees(0));
        Distance stoneOffset = Distance.fromInches(8);

        switch (visual.findSkystone()) {
            case Left:
                targetPos.getY().add(stoneOffset);
                break;
            case Center:
                break;
            case Right:
                targetPos.getY().subtract(stoneOffset);
                break;
        }

        //move to stone and extend arm
        drive.setTarget(targetPos, navigation);
        delivery.setHeight(0.15);
        while (!drive.isComplete() || isStopRequested()) {
            telemetry.addLine("moving to stone");
            targetPos.getY().subtract(Distance.fromInches(visual.getSkystoneOffset()));
            drive.setTarget(targetPos, navigation);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        //move slightly back
        targetPos = new Position(Distance.fromInches(0), Distance.fromInches(-30), Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() || isStopRequested()) {
            telemetry.addLine("moving backwards");
            targetPos.getY().subtract(Distance.fromInches(visual.getSkystoneOffset()));
            drive.setTarget(targetPos, navigation);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }

        //grab the skystone
        telemetry.addLine("getting stone");
        telemetry.update();
        delivery.setDepth(0.75 + stoneAngle.stonePosition()[0]);
        grabber.release();
        Thread.sleep(3000);
        delivery.setHeight(0);
        Thread.sleep(1500);
        grabber.grab();
        delivery.setHeight(0.03);

        telemetry.clear();
        /*targetPos = new Position(Distance.fromInches(30), Distance.fromInches(-15), Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() || isStopRequested()) {
            telemetry.addLine("moving");
            targetPos.getY().subtract(Distance.fromInches(visual.getSkystoneOffset()));
            drive.setTarget(targetPos, navigation);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
        targetPos = new Position(Distance.fromInches(50), Distance.fromInches(-15), Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() || isStopRequested()) {
            telemetry.addLine("moving");
            targetPos.getY().subtract(Distance.fromInches(visual.getSkystoneOffset()));
            drive.setTarget(targetPos, navigation);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }*/

        telemetry.addLine("finished");
        telemetry.update();
        Thread.sleep(5000);
        visual.stop();
    }
}

//changed files:


//files that need to be updated on Android Studio:
//NewMechanumWheels
//PositionLoopTest
//MainAutonomous
//Delivery
//Elevator
//Visual
//VuforiaPosition
//ManualReset
