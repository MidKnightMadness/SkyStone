package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.skystonevisual.SkystoneVisual;

@Autonomous
public class BlueSkyFirstAllAuto extends LinearOpMode {

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
        d.beginTranslation(Distance.fromInches(1), .2);
        d.setRunToPosition();
        while (pos == SkystoneVisual.SkystonePosition.UNKNOWN && d.isBusy() && !isStopRequested()) {
            pos = sv.findSkystone();
            telemetry.addLine(pos.toString());
        }
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(14), .4);
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(2), .2);
        while (d.isBusy() && !isStopRequested());


        d.beginTranslation(Distance.fromInches(-10), .4);
        while (d.isBusy() && !isStopRequested());

        d.beginRotation(Angle.fromDegrees(90), -1, .3);//r
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(100), .4);
        while (d.isBusy() && !isStopRequested());

        d.beginRotation(Angle.fromDegrees(90), 1, .5);//l
        while (d.isBusy() && !isStopRequested());

        /*


        //Testing all the directions
        d.beginTranslation(Distance.fromInches(20), .5);
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(-10), .3);
        while (d.isBusy() && !isStopRequested());

        d.beginRotation(Angle.fromDegrees(90), 1, .5);//l
        while (d.isBusy() && !isStopRequested());

        d.beginRotation(Angle.fromDegrees(90), -1, .3);//r
        while (d.isBusy() && !isStopRequested());

        */


    }
}
