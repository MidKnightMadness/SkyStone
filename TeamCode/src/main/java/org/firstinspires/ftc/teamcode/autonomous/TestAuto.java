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
public class TestAuto extends LinearOpMode {

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


        m.holdFoundation();

        sleep(3000);

        m.releaseFoundation();

        sleep(3000);




    }
}
