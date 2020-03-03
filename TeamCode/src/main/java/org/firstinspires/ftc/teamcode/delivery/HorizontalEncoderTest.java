package org.firstinspires.ftc.teamcode.delivery;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.config.HardwareConfig;

@Disabled
@TeleOp
public class HorizontalEncoderTest extends OpMode{

    private DcMotor horizontal;

    @Override
    public void init() {
        horizontal = hardwareMap.dcMotor.get(HardwareConfig.HORIZONTAL);
    }

    @Override
    public void loop() {
        telemetry.addData("position", horizontal.getCurrentPosition());
    }
}
