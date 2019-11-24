package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.clamp.Clamp;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.lift.Lift;
import org.firstinspires.ftc.teamcode.mover.Mover;
import org.firstinspires.ftc.teamcode.skystonevisual.SkystoneVisual;

@Autonomous
public class BlueSkyFirstNoFoundAuto extends LinearOpMode {

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

        telemetry.addLine("HERE'S JOHNNY");
        telemetry.update();


        SkystoneVisual.SkystonePosition pos = SkystoneVisual.SkystonePosition.UNKNOWN;
        d.beginTranslation(Distance.fromInches(-18), .25);
        d.setRunToPosition();
        d.beginTranslation(Distance.fromInches(-18), .25);
        l.raiseLift(1000);
        while (d.isBusy() && !isStopRequested());

        pos = sv.findSkystone();
        telemetry.addLine(pos.toString());

        c.openToFull();
        sleep(4000);
        c.stopMoving();

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

        d.beginRotation(Angle.fromDegrees(90), 1, .25);
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(16), .125); //move slowly up to stone
        while (d.isBusy() && !isStopRequested());

        sleep(300);

        l.lowerLift(0);

        while (l.isBusy() && !isStopRequested());

        c.closeToHalf();
        sleep(1550);
        c.stopMoving();

        l.raiseLift(200);
        while (l.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(-16), .25); //back up from stones
        while (d.isBusy() && !isStopRequested());

        d.beginRotation(Angle.fromDegrees(90), 1, .25);//turn right
        while (d.isBusy() && !isStopRequested());

        d.beginTranslation(Distance.fromInches(85), .4);//move under bridge to foundation
        while (d.isBusy() && !isStopRequested());


        c.openToFull();
        sleep(1500);
        c.stopMoving();

        d.beginTranslation(Distance.fromInches(-40), .4);//drag foundation to zone
        while (d.isBusy() && !isStopRequested());



    }
}
