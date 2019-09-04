package org.firstinspires.ftc.teamcode.testingNyquil;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="EncoderTesting", group="Testing")
@Disabled
public class EncoderTesting extends OpMode {

    private DcMotor testMotor;

    @Override
    public void init() {
        testMotor = hardwareMap.dcMotor.get("testMotor");
    }

    public void start() {
        testMotor.setPower(1);
    }

    @Override
    public void loop() {
        telemetry.addData("Encoder Position", testMotor.getCurrentPosition());
        telemetry.update();
    }

    public void stop() {}
}
