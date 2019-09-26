package org.firstinspires.ftc.teamcode.common.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//@Autonomous
public class ThreadTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        telemetry.addLine(i + "");
                        telemetry.update();
                    }
                }
            }).start();
        } catch (Exception e) {
            telemetry.addLine(e.getMessage());
            telemetry.update();
        }

        while (!isStopRequested());
    }
}
