package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

@TeleOp
public class TestSingletonFinalize extends OpMode {
    @Override
    public void init() {
        SingletonFinalize.getInstance();
    }

    @Override
    public void loop() { // I want to generate an error to see if stop() is called
        /*try {
            //Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        telemetry.addLine("Hello");
    }

    @Override
    public void stop() {
        super.stop();
        RobotLog.a("STOPPING! I'M STOPPING! STOPPING NOW! STOPPING! JUST ONE MORE LINE! STOPPING!");
    }
}
