package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.delivery.Delivery;
import org.firstinspires.ftc.teamcode.delivery.Elevator;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.drive.NewMechanumWheels;
import org.firstinspires.ftc.teamcode.navigation.Navigation;

@Autonomous
public class SimpleParkLongLeft extends LinearOpMode {
    private Drive drive = new NewMechanumWheels();
    //private Visual visual = new VuforiaPosition();
    private Navigation navigation = new Navigation(null, drive);
    private Delivery delivery = new Elevator();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("starting");
        telemetry.update();
        Assembly.initialize(telemetry, hardwareMap, drive, /*visual,*/ navigation, delivery);
        drive.resetPosition(new Position(Distance.fromInches(0), Distance.fromInches(0), Angle.fromDegrees(90)));
        telemetry.addLine("done");
        telemetry.update();

        waitForStart();

        runtime.reset();
        while(runtime.seconds() < 0)
            idle();

        drive.setThreshold(2, 2);

        Position targetPos = new Position(Distance.fromInches(0), Distance.fromInches(25), Angle.fromDegrees(0));
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() && !isStopRequested()) {
            navigation.update();
            drive.update();
            telemetry.update();
        }

        targetPos.setTheta(Angle.fromDegrees(-90));
        drive.setTarget(targetPos, navigation);
        while (!drive.isComplete() && !isStopRequested()) {
            navigation.update();
            drive.update();
            telemetry.update();
        }

        targetPos.setX(Distance.fromInches(32));
        drive.setTarget(targetPos, navigation);
        //delivery.setDepthRaw(2000);
        while (!drive.isComplete() && !isStopRequested()) {
            navigation.update();
            drive.update();
            telemetry.update();
        }
    }
}
