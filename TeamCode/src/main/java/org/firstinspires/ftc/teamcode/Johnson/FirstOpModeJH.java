package org.firstinspires.ftc.teamcode.Johnson;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class FirstOpModeJH extends OpMode {
    @Override
    public void init() {
        telemetry.addLine("Hello");

    }

    @Override
    public void loop() {
telemetry.addData("Girth", gamepad1.right_stick_y);


    }
}

