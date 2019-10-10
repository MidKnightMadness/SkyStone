package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.grabber.Grabber;

public class GamepadController {
    private Gamepad gamepad1;
    private Gamepad gamepad2;

    public GamepadController(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public void processGrabber(Grabber grabber){
        if (gamepad1.x) {
            grabber.grab();
        }
        else{
            grabber.release();
        }
    }

}