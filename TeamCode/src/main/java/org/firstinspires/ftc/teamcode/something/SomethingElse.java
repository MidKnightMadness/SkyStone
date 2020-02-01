package org.firstinspires.ftc.teamcode.something;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous

public class SomethingElse extends LinearOpMode {
    DcMotor motor;
    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.dcMotor.get("vroom vroom");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addLine("READY TO GO");
        telemetry.update();
        waitForStart();
        motor.setPower(-1);
        telemetry.addLine("MOTOR GOING");
        telemetry.update();
        sleep(3000);
        motor.setPower(0.5);
        telemetry.addLine("MOTOR GOING");
        telemetry.update();
        sleep(3000);
    }
}
