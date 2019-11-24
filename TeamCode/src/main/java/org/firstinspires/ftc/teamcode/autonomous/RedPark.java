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
public class RedPark extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("HELLO! DID YA MISS ME?");
        telemetry.update();

        Drive d = AssemblyManager.newInstance(Drive.class, hardwareMap, telemetry); // Initialize all Assemblies required during the Autonomous program by the interface

        waitForStart();

        telemetry.addLine("HERE'S JOHNNY");
        telemetry.update();


        //Assumes bot is facing away from wall and is on the wall
        d.beginTranslation(Distance.fromInches(10), .5);
        d.setRunToPosition();
        d.beginTranslation(Distance.fromInches(10), .5);

        while (d.isBusy() && !isStopRequested());

        d.beginRotation(Angle.fromDegrees(90), -1, .25);//turn right
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(20), .5);
        while (d.isBusy() && !isStopRequested());






    }
}
