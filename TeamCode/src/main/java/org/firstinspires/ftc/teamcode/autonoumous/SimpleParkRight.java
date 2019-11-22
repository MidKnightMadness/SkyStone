package org.firstinspires.ftc.teamcode.autonoumous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.drive.NewMechanumWheels;
import org.firstinspires.ftc.teamcode.navigation.Navigation;
import org.firstinspires.ftc.teamcode.visual.Visual;
import org.firstinspires.ftc.teamcode.visual.VuforiaPosition;

// I hate these robots that can only park, but I might as well...
@Autonomous
public class SimpleParkRight extends LinearOpMode {
    private Drive drive = new NewMechanumWheels();
    private Visual visual = new VuforiaPosition();
    private Navigation navigation = new Navigation(visual, drive);

    @Override
    public void runOpMode() throws InterruptedException {
        Assembly.initialize(telemetry, hardwareMap, drive, visual, navigation);

        waitForStart();

        drive.setTarget(new Position(Distance.fromInches(36), Distance.fromInches(0), Angle.fromDegrees(0)), navigation);
        while (!drive.isComplete()) {
            visual.update();
            navigation.update();
            drive.update();
        }
    }
}
