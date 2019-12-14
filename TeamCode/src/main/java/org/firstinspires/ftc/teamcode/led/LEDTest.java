package org.firstinspires.ftc.teamcode.led;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cAddr;

//@TeleOp
public class LEDTest extends OpMode {
    @Override
    public void init() {
        LED.init(hardwareMap.i2cDeviceSynch.get("ledstrip"));
        //LED.setStaticColor(LED.StaticColor.GREEN);
    }

    @Override
    public void loop() {

    }
}
