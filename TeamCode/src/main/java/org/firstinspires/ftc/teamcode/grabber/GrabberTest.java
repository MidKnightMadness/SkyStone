package org.firstinspires.ftc.teamcode.grabber;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.GamepadController;

//@TeleOp
public class GrabberTest extends OpMode {
    private Grabber grabber = new Claw();
    private GamepadController gamepadController;



    @Override
    public void init() {
        gamepadController = new GamepadController();

        grabber.hardwareMap = hardwareMap;
        grabber.telemetry = telemetry;
        grabber.init();
    }

    @Override
    public void loop() {
        gamepadController.update(gamepad1, gamepad2);
        gamepadController.processGrabber(grabber);
        grabber.update();//end of loop
    }
}
