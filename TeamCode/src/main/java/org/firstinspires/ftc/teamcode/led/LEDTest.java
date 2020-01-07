package org.firstinspires.ftc.teamcode.led;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp
public class LEDTest extends OpMode {
    private LEDStrip ledStrip;
    @Override
    public void init() {
        ledStrip = hardwareMap.get(LEDStrip.class, "ledstrip");
        ledStrip.sendHello();
    }

    @Override
    public void loop() {

    }
}
