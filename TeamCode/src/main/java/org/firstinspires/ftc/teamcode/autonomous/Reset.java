package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.pullup.PullUp;

@Autonomous
public class Reset extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        PullUp p = AssemblyManager.newInstance(PullUp.class, hardwareMap, telemetry);
        //Hand h = AssemblyManager.newInstance(Hand.class, hardwareMap, telemetry);
        //MineralArm m = AssemblyManager.newInstance(MineralArm.class, hardwareMap, telemetry);


        waitForStart();                                               // Wait for Start Button
        p.reset(); // Lower bot from hanging position
        Thread.sleep(5000);
    }
}
