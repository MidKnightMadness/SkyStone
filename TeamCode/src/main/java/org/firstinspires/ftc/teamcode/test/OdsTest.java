package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

@TeleOp
public class OdsTest extends OpMode {

    private OpticalDistanceSensor ods;

    @Override
    public void init() {
        ods = hardwareMap.opticalDistanceSensor.get("ods");
    }

    @Override
    public void loop() {
        telemetry.addData("light",ods.getLightDetected());
        telemetry.addData("rawlight",ods.getRawLightDetected());
        telemetry.addData("rawlightmax",ods.getRawLightDetectedMax());
    }
}
