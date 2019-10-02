package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.nio.channels.DatagramChannel;

@TeleOp
public class EncoderTestingYL extends OpMode {
    private DcMotor leftmotor;

    @Override
    public void init() {
        leftmotor = hardwareMap.dcMotor.get("leftmotor");
        leftmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftmotor.setPower(0);
        leftmotor.setTargetPosition(1000 + leftmotor.getCurrentPosition());


    }

    @Override
    public void loop() {
        leftmotor.setPower(1);
        telemetry.addData("left motor position", leftmotor.getCurrentPosition());

    }
}
