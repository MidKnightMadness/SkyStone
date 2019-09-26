package org.firstinspires.ftc.teamcode.autonomous.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Config;

@Autonomous
public class HubTest extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor motor = hardwareMap.dcMotor.get(Config.Drive.BACK_LEFT);

        waitForStart();
        motor.setPower(-1);
        while (time < 5) {
            telemetry.addData("Motor ", motor.getCurrentPosition());
            telemetry.update();
            idle();
        }

        waitForStart();
        motor.setPower(1);
        while (time < 10) {
            telemetry.addData("Motor ", motor.getCurrentPosition());
            telemetry.update();
            idle();
        }

        motor.setPower(0);


    }
}