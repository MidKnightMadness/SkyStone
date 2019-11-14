package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.skystonevisual.SkystoneVisual;

@Autonomous
public class BluePark extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("HELLO! DID YA MISS ME?");
        telemetry.update();

        Drive d = AssemblyManager.newInstance(Drive.class, hardwareMap, telemetry); // Initialize all Assemblies required during the Autonomous program by the interface
        SkystoneVisual sv = AssemblyManager.newInstance(SkystoneVisual.class, hardwareMap, telemetry);

        waitForStart();

        telemetry.addLine("HERE'S JOHNNY");
        telemetry.update();


        SkystoneVisual.SkystonePosition pos = SkystoneVisual.SkystonePosition.UNKNOWN;



        d.beginTranslationSide(Distance.fromInches(1), -1, .1);
        d.setRunToPosition();
        while (pos == SkystoneVisual.SkystonePosition.UNKNOWN && d.isBusy() && !isStopRequested()) {


        }

        //This just keeps the bot from doing other things while it is moving to its destination
        while (d.isBusy() && !isStopRequested());




        //Assumes bot is facing away from wall and is on the wall
        d.beginTranslationSide(Distance.fromInches(40), -1,.5);
        d.setRunToPosition();
        while (d.isBusy() && !isStopRequested());






    }
}
