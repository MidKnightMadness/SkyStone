package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.clamp.Clamp;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.lift.Lift;
import org.firstinspires.ftc.teamcode.mover.Mover;
import org.firstinspires.ftc.teamcode.skystonevisual.SkystoneVisual;

@Autonomous
public class RedSkyFirstAllAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("HELLO! DID YA MISS ME?");
        telemetry.update();


        Drive d = AssemblyManager.newInstance(Drive.class, hardwareMap, telemetry); // Initialize all Assemblies required during the Autonomous program by the interface
        SkystoneVisual sv = AssemblyManager.newInstance(SkystoneVisual.class, hardwareMap, telemetry);
        Mover m = AssemblyManager.newInstance(Mover.class, hardwareMap, telemetry);
        Lift l = AssemblyManager.newInstance(Lift.class, hardwareMap, telemetry);
        Clamp c = AssemblyManager.newInstance(Clamp.class, hardwareMap, telemetry);


        waitForStart();

        telemetry.addLine("");
        telemetry.update();


        SkystoneVisual.SkystonePosition pos = SkystoneVisual.SkystonePosition.UNKNOWN;
        d.beginTranslation(Distance.fromInches(-18), .25);
        d.setRunToPosition();
        d.beginTranslation(Distance.fromInches(-18), .25);
        l.raiseLift(1200);
        while (d.isBusy() && !isStopRequested());

        sleep(100);

        pos = sv.findSkystone();
        telemetry.addLine(pos.toString());

        c.openToFull();

        d.beginRotation(Angle.fromDegrees(90), 1, .25);
        while (d.isBusy() && !isStopRequested());

        int skydist = 0;

        if(pos == SkystoneVisual.SkystonePosition.LEFT){
            skydist = -8;
        } else if (pos == SkystoneVisual.SkystonePosition.CENTER){
            skydist = 0;
        } else if (pos == SkystoneVisual.SkystonePosition.RIGHT){
            skydist = 8;
        }

        d.beginTranslation(Distance.fromInches(skydist), .25); //move to stone
        while (d.isBusy() && !isStopRequested());

        sleep(100);

        d.beginRotation(Angle.fromDegrees(90), 1, .25);//turn to face stone with clamp
        while (d.isBusy() && !isStopRequested());

        sleep(100);

        d.beginTranslation(Distance.fromInches(14), .125); //move slowly up to stone
        while (d.isBusy() && !isStopRequested());

        telemetry.addLine("FINISHED MOVING TO STONE");
        telemetry.update();

        l.lowerLift(0);
        while (l.isBusy() && !isStopRequested()); //drop lift

        telemetry.addLine("FINISHED DROP");
        telemetry.update();

        c.closeToHalf();
        while (c.isBusy() && !isStopRequested()); // pick up stone

        telemetry.addLine("FINISHED CLAMPING");
        telemetry.update();

        sleep(300);

        d.beginTranslation(Distance.fromInches(-13), .25); //back up from stones
        while (d.isBusy() && !isStopRequested());

        l.lowerLift(0);

        d.beginRotation(Angle.fromDegrees(90), -1, .25);//turn right
        while (d.isBusy() && !isStopRequested());

        sleep(300);

        d.beginTranslation(Distance.fromInches(85-skydist), .4);//move under bridge to foundation
        while (d.isBusy() && !isStopRequested());

        sleep(300);

        l.raiseLift(900);

        d.beginRotation(Angle.fromDegrees(90), 1, .25);//turn left
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(30), .35);//move to foundation
        while (d.isBusy() && !isStopRequested());

        m.holdFoundation();

        c.openToFull();

        l.raiseLift(2100);
        while (l.isBusy() && !isStopRequested());


        d.beginTranslation(Distance.fromInches(-40), .4);//drag foundation to zone
        while (d.isBusy() && !isStopRequested());

        m.releaseFoundation();
        sleep(300);

        d.beginRotation(Angle.fromDegrees(90), 1, .25);//turn left
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(50), .4);//move to park
        while (d.isBusy() && !isStopRequested());



    }
}
