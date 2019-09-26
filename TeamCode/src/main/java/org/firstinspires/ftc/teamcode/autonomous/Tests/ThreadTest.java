package org.firstinspires.ftc.teamcode.autonomous.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//@Autonomous
public class ThreadTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int a = 0;
                    int b = a+1;
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {

                    }
                }
            }).start();
        }
        telemetry.addLine("Finished!");
        telemetry.update();
    }
}
