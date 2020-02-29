package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
import org.firstinspires.ftc.teamcode.navigation.Navigation;
import org.firstinspires.ftc.teamcode.visual.StoneAngle;
import org.firstinspires.ftc.teamcode.visual.Visual;
import org.firstinspires.ftc.teamcode.visual.VuforiaPosition;

@Autonomous
public class NewAutonomousFoundationBlue2 extends LinearOpMode {

    private Drive drive = new NewMechanumWheels();
    private Visual visual = new VuforiaPosition();
    private Navigation navigation = new Navigation(null, drive);
    private Delivery delivery = new Elevator();
    private Grabber grabber = new Claw();
    private FoundationMover foundationMover = new ActualFoundationMover();
    private StoneAngle stoneAngle = new StoneAngle();

    private boolean first;

    Rev2mDistanceSensor distanceSensorLeft;
    Rev2mDistanceSensor distanceSensorRight;

    @Override
    public void runOpMode() throws InterruptedException {
        boolean middle = false;
        telemetry.addLine("Starting");
        telemetry.update();
        Assembly.initialize(telemetry, hardwareMap, drive, visual, navigation, delivery, grabber, foundationMover, stoneAngle);
        telemetry.addLine("Finish Starting");
        telemetry.update();
        distanceSensorLeft = hardwareMap.get(Rev2mDistanceSensor.class, "distanceleftside");
        distanceSensorRight = hardwareMap.get(Rev2mDistanceSensor.class, "distancerightside");
        grabber.rotate(0);

        drive.resetPosition(new Position(Distance.fromInches(0), Distance.fromInches(0), Angle.fromDegrees(0)));

        while (!isStarted() && !isStopRequested())
            idle();

        Position targetPos = new Position(Distance.fromInches(0), Distance.fromInches(-27), Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        drive.setThreshold(2, 2);

        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        targetPos.setTheta(Angle.fromDegrees(-90));
        drive.setTarget(targetPos, navigation);
        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }


        targetPos.getY().add(Distance.fromInches(-5));
        drive.setTarget(targetPos, navigation);
        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        foundationMover.grab();
        Thread.sleep(500);
        targetPos.getX().add(Distance.fromInches(-12));
        drive.setTarget(targetPos, navigation);
        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }
        targetPos.setTheta(Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        foundationMover.reset();
        delivery.setHeight(0.25);
        targetPos.getX().add(Distance.fromInches(22));
        drive.setTarget(targetPos, navigation);
        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        targetPos.getX().add(Distance.fromInches(-4));
        drive.setTarget(targetPos, navigation);
        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");
            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        if (!middle) {
            targetPos.getY().add(Distance.fromInches(28));
            drive.setTarget(targetPos, navigation);
            first = true;
            while ((!drive.isComplete() || first) && !isStopRequested()) {
                telemetry.addLine("moving to foundation");

                idle();
                drive.update();
                visual.update();
                navigation.update();
                telemetry.update();
                first = false;
            }
        }

        delivery.setDepthRaw(-3200);
        targetPos.getX().add(Distance.fromInches(-10));
        targetPos.setTheta(Angle.fromDegrees(-90));
        drive.setTarget(targetPos, navigation);
        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");
            if (delivery.isComplete()) {
                delivery.setHeight(0);
            }

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }
        targetPos.getX().add(Distance.fromInches(-12));
        drive.setTarget(targetPos, navigation);
        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");
            if (delivery.isComplete()) {
                delivery.setHeight(0);
            }

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        visual.stop();
    }
}
