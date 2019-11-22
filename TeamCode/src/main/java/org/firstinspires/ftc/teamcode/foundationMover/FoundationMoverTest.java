package org.firstinspires.ftc.teamcode.foundationMover;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

// press gamepad y to toggle between up and down
//@TeleOp
public class FoundationMoverTest extends OpMode {
    private ActualFoundationMover mover = new ActualFoundationMover();
    private double mode = 1;
    private boolean pressed;

    @Override
    public void init() {
        mover.hardwareMap = hardwareMap;
        mover.telemetry = telemetry;
        mover.init();
        pressed = false;
    }

    @Override
    public void loop() {
        telemetry.addData("y", gamepad1.y);
        if (gamepad1.y && !pressed){
            pressed = true;
        }
        else if (!gamepad1.y && pressed) {
            pressed = false;
            mode *= -1;
        }

        if (mode == 1){
            mover.reset();
        }
        else if (mode == -1){
            mover.grab();
        }
    }
}
