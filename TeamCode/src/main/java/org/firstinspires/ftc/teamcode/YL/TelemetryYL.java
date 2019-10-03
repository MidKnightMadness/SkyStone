package org.firstinspires.ftc.teamcode.YL;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TelemetryYL extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        telemetry.addLine("HELLOOOOOOO");
        telemetry.addData("leftY", gamepad1.left_stick_y);
        telemetry.addData("rightY", gamepad1.right_stick_y);


    }
}
