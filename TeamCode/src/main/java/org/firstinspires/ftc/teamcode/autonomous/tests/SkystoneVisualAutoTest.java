package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.skystonevisual.SkystoneVisual;

@Disabled
public class SkystoneVisualAutoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Op Mode is Running");
        telemetry.update();
        SkystoneVisual sv = AssemblyManager.newInstance(SkystoneVisual.class, hardwareMap, telemetry);
        waitForStart();
        telemetry.addLine("Point camera at stones!");
        telemetry.update();
        sleep(10000);
        SkystoneVisual.SkystonePosition pos = SkystoneVisual.SkystonePosition.UNKNOWN;
        pos = sv.findSkystone();
        telemetry.addLine(String.valueOf(pos));
        telemetry.update();
        sleep(10000);

    }
}
