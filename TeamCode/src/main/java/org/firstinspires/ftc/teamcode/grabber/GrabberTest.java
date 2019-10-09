package org.firstinspires.ftc.teamcode.grabber;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class GrabberTest extends OpMode {
    private Grabber grabber;

    @Override
    public void init() {
        grabber.hardwareMap = hardwareMap;
        grabber.telemetry = telemetry;
        grabber.init();
    }

    @Override
    public void loop() {
        if (gamepad1.x) {
            grabber.grab();
        }
        grabber.release();
        grabber.update();//end of loop
    }
}
