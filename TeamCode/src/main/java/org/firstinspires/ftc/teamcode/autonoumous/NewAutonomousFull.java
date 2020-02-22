package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
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
import org.firstinspires.ftc.teamcode.navigation.Navigation;
import org.firstinspires.ftc.teamcode.visual.StoneAngle;
import org.firstinspires.ftc.teamcode.visual.Visual;
import org.firstinspires.ftc.teamcode.visual.VuforiaPosition;

@Autonomous
public class NewAutonomousFull extends LinearOpMode {

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
        telemetry.addLine("Starting");
        telemetry.update();
        Assembly.initialize(telemetry, hardwareMap, drive, visual, navigation, delivery, grabber, foundationMover, stoneAngle);
        telemetry.addLine("Finish Starting");
        telemetry.update();
        distanceSensorLeft = hardwareMap.get(Rev2mDistanceSensor.class, "distanceleftside");
        distanceSensorRight = hardwareMap.get(Rev2mDistanceSensor.class, "distancerightside");
        grabber.rotate(-1);

        drive.resetPosition(new Position(Distance.fromInches(0), Distance.fromInches(0), Angle.fromDegrees(90)));

        while (!isStarted() && !isStopRequested())
            idle();

        //check position
        Visual.SkystoneSetup skystone = visual.findSkystone();

        //move to skystone
        Position targetPos;
        if (skystone == Visual.SkystoneSetup.Center)
            targetPos = new Position(Distance.fromInches(0), Distance.fromInches(-10), Angle.fromDegrees(0));
        else if (skystone == Visual.SkystoneSetup.Right)
            targetPos = new Position(Distance.fromInches(-8.5), Distance.fromInches(-10), Angle.fromDegrees(0));
        else
            targetPos = new Position(Distance.fromInches(8.5), Distance.fromInches(-10), Angle.fromDegrees(0));

        drive.setThreshold(2, 2);

        first = true;
        delivery.setHeight(0.25);
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to stone");

            targetPos.setY(navigation.getPosition().getY().copy().subtract(Distance.fromInches(stoneAngle.stonePosition()[0] - 2.5)));
            drive.setTarget(targetPos, navigation);

            if (delivery.isComplete())
                delivery.setDepthRaw(-3200);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        //extend arm
        first = true;
        while ((!delivery.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("extending");
            delivery.setDepthRaw(Math.max(-2650 - (int) (180 * stoneAngle.stonePosition()[0]), -4000));

            idle();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        //lower arm
        delivery.setHeight(0);
        telemetry.addLine("lowering arm");
        telemetry.update();
        while (!delivery.isComplete() && !isStopRequested()) {
            idle();
        }
        //grab
        //grabber.grab();
        delivery.setHeight(0.04);

        //move back
        first = true;
        targetPos = navigation.getPosition().copy();
        targetPos.getY().add(Distance.fromInches(4));
        drive.setTarget(targetPos, navigation);
        delivery.setDepthRaw(-2730);
        while ((!drive.isComplete() || first) && isStopRequested()) {
            telemetry.addLine("moving back");

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        //turn left
        targetPos.setTheta(Angle.fromDegrees(90));
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() && !isStopRequested()) {
            telemetry.addLine("turning left");

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }

        //move right
        targetPos.getY().subtract(Distance.fromInches(4));
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() && !isStopRequested()) {
            telemetry.addLine("moving right");

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }

        //move forwards
        targetPos.setX(Distance.fromInches(80));        //foundation position at 80
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() && !isStopRequested()) {
            telemetry.addLine("moving forwards to foundation");

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }

        //turn right to place stone
        delivery.setHeight(0.17);
        delivery.setDepth(-4000);
        targetPos.setTheta(Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() && !isStopRequested()) {
            telemetry.addLine("turning right");

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }

        //drop stone
        grabber.release();
        delivery.setHeight(0.2);
        while (!delivery.isComplete() && !isStopRequested()) {
            idle();
        }

        //turn right to move foundation
        targetPos.setTheta(Angle.fromDegrees(-90));
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() && !isStopRequested()) {
            telemetry.addLine("turning right again");

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
        }
    }
}