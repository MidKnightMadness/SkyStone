package org.firstinspires.ftc.teamcode.K;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class KMEncoderTesting extends OpMode {

    private DcMotor leftMotor;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("leftmotor");
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(0);
        leftMotor.setTargetPosition(1000);
        leftMotor.setTargetPosition(1000 + leftMotor.getCurrentPosition());
    }

    @Override
    public void loop() {
        leftMotor.setPower(1);
        telemetry.addData("Left motor position", leftMotor.getCurrentPosition());
    }
}
