package org.firstinspires.ftc.teamcode.grabber;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GrabberTest extends OpMode {
    private Grabber grabber = new Claw();


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
        else{
            grabber.release();
        }

        grabber.update();//end of loop
    }
}
