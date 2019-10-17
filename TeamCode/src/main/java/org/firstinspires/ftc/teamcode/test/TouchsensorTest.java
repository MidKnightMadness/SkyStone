package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp
public class TouchsensorTest extends OpMode {
    private TouchSensor touchsensor;

    @Override
    public void init() {
        touchsensor = hardwareMap.touchSensor.get("bumper1");

    }

    @Override
    public void loop() {
         telemetry.addData("Value", touchsensor.isPressed());

    }
}
