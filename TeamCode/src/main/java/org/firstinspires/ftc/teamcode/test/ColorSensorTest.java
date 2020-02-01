package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.hardware.adafruit.AdafruitI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.visual.webcam.PhoneManager;

@TeleOp
public class ColorSensorTest extends OpMode {
    ModernRoboticsI2cColorSensor colorSensor;

    @Override
    public void init() {
        colorSensor = hardwareMap.get(ModernRoboticsI2cColorSensor.class, "colorsensor");
    }

    @Override
    public void loop() {
        double[] hsv = new double[3];
        PhoneManager.colorToHSV(colorSensor.argb(), hsv);
        telemetry.addData("hue", hsv[0]);
        telemetry.addData("saturation", hsv[1]);
        telemetry.addData("value", hsv[2]);
    }
}
