package org.firstinspires.ftc.teamcode.something;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous

public class Clown extends LinearOpMode {
    DcMotor motor1;
    DcMotor motor2;
    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.dcMotor.get("vroom vroom");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2 = hardwareMap.dcMotor.get("skrt skrt");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        //motor 1 is negative and motor 2 is positive to go straight
        motor1.setPower(-0.7);
        motor2.setPower(0.5);
        telemetry.addLine("BOTH POSITIVE");
        telemetry.update();
        sleep(3500);
    }
}
