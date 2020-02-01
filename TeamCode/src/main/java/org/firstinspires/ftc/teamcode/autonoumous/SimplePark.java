package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.drive.NewMechanumWheels;
import org.firstinspires.ftc.teamcode.navigation.Navigation;
import org.firstinspires.ftc.teamcode.visual.Visual;
import org.firstinspires.ftc.teamcode.visual.VuforiaPosition;
import org.firstinspires.ftc.teamcode.delivery.Delivery;
import org.firstinspires.ftc.teamcode.delivery.Elevator;

@Autonomous
public class SimplePark extends LinearOpMode {
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
        drive.setTarget(new Position(Distance.fromInches(0), Distance.fromInches(24), Angle.fromDegrees(0)), navigation);
        delivery.setDepthRaw(2000);
        while (!drive.isComplete() && !isStopRequested()) {
            navigation.update();
            drive.update();
            telemetry.update();
        }
    }
}
