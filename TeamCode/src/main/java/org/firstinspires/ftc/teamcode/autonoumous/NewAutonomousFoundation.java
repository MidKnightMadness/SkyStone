package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
import org.firstinspires.ftc.teamcode.navigation.Navigation;
import org.firstinspires.ftc.teamcode.visual.StoneAngle;
import org.firstinspires.ftc.teamcode.visual.Visual;
import org.firstinspires.ftc.teamcode.visual.VuforiaPosition;

@Autonomous
public class NewAutonomousFoundation extends LinearOpMode {

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

        drive.resetPosition(new Position(Distance.fromInches(0), Distance.fromInches(0), Angle.fromDegrees(0)));

        while (!isStarted() && !isStopRequested())
            idle();

        Position targetPos = new Position(Distance.fromInches(0), Distance.fromInches(-10), Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        drive.setThreshold(2, 2);

        first = true;
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving to foundation");

            //targetPos.setY(navigation.getPosition().getY().copy().subtract(Distance.fromInches(distanceSensorLeft.getDistance(DistanceUnit.INCH) - 2.5)));
            //drive.setTarget(targetPos, navigation);

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        foundationMover.grab();
        targetPos = new Position(Distance.fromInches(0), Distance.fromInches(-20), Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        while ((!drive.isComplete() || first) && !isStopRequested()) {
            telemetry.addLine("moving foundation");

            idle();
            drive.update();
            visual.update();
            navigation.update();
            telemetry.update();
            first = false;
        }

        //park
    }
}
