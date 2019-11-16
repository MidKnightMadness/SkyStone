package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.mover.Mover;
import org.firstinspires.ftc.teamcode.skystonevisual.SkystoneVisual;

@Autonomous
public class BlueSkyFirstAllAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("HELLO! DID YA MISS ME?");
        telemetry.update();

        Drive d = AssemblyManager.newInstance(Drive.class, hardwareMap, telemetry); // Initialize all Assemblies required during the Autonomous program by the interface
        SkystoneVisual sv = AssemblyManager.newInstance(SkystoneVisual.class, hardwareMap, telemetry);
        Mover m = AssemblyManager.newInstance(Mover.class, hardwareMap, telemetry);

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

        d.beginTranslation(Distance.fromInches(14), .4); //move to stone
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(2), .2); //move slowly up to stone
        while (d.isBusy() && !isStopRequested());

        //TODO: pick up stone

        d.beginTranslation(Distance.fromInches(-10), .4); //back up from stones
        while (d.isBusy() && !isStopRequested());

        d.beginRotation(Angle.fromDegrees(90), -1, .3);//turn right
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(100), .4);//move under bridge to foundation
        while (d.isBusy() && !isStopRequested());

        d.beginRotation(Angle.fromDegrees(90), 1, .3);//turn left
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(30), .4);//move to foundation
        while (d.isBusy() && !isStopRequested());

        m.holdFoundation();

        sleep(500);

        d.beginTranslation(Distance.fromInches(-40), .4);//drag foundation to zone
        while (d.isBusy() && !isStopRequested());

        m.releaseFoundation();

        sleep(500);

        d.beginTranslationSide(Distance.fromInches(35), 1, .4);//move left away from foundation
        while (d.isBusy() && !isStopRequested());

        d.beginRotation(Angle.fromDegrees(90), 1, .3);//turn left
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(15), .4);//move to park
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
