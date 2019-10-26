package org.firstinspires.ftc.teamcode.TestMZ;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name = "PieCrusts", group = "Testing")
public class EncoderTesting extends OpMode {

    private DcMotor testMotor;

    @Override
    public void init() {
        testMotor = hardwareMap.dcMotor.get("testMotor");

        testMotor.setPower(1);
        telemetry.addData("Encoder Position", testMotor.getCurrentPosition());
        telemetry.update();
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }
}
