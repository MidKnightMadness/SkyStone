package org.firstinspires.ftc.teamcode.foundationMover;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class FoundationButtonTest extends OpMode {
    private FoundationButtons buttons;
    @Override
    public void init() {
        buttons = new FoundationButtons(
                hardwareMap.touchSensor.get("leftbutton"),
                hardwareMap.touchSensor.get("rightbutton")
        );

    }

    @Override
    public void loop() {
        telemetry.addData("State", buttons.getState().getValue());

    }
}
