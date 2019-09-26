package org.firstinspires.ftc.teamcode.drive;

import android.text.ParcelableSpan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.common.Config;

@Autonomous
public class EncoderTest extends LinearOpMode {

    DcMotor motorUp;
    DcMotor motorDown;
    DcMotor motorLeft;
    DcMotor motorRight;

    @Override
    public void runOpMode() throws InterruptedException {
        double endTime = 0;
        //Init motors
        motorUp = hardwareMap.dcMotor.get(Config.Drive.FRONT_LEFT);
        motorLeft = hardwareMap.dcMotor.get(Config.Drive.FRONT_RIGHT);
        motorRight = hardwareMap.dcMotor.get(Config.Drive.BACK_RIGHT);
        motorDown = hardwareMap.dcMotor.get(Config.Drive.BACK_LEFT);

        motorUp.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorDown.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorUp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorDown.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Motor Up", new Func<Integer>() {
            @Override
            public Integer value() {
                return motorUp.getCurrentPosition();
            }
        });
        telemetry.addData("Motor Left", new Func<Integer>() {
            @Override
            public Integer value() {
                return motorLeft.getCurrentPosition();
            }
        });
        telemetry.addData("Motor Right", new Func<Integer>() {
            @Override
            public Integer value() {
                return motorRight.getCurrentPosition();
            }
        });
        telemetry.addData("Motor Down", new Func<Integer>() {
            @Override
            public Integer value() {
                return motorDown.getCurrentPosition();
            }
        });

        waitForStart();

        motorUp.setPower(1);
        endTime = time + 2;
        while (endTime > time) {
            telemetry.update();
        }
        motorUp.setPower(-1);
        endTime = time + 2;
        while (endTime > time) {
            telemetry.update();
        }
        motorUp.setPower(0);
        motorLeft.setPower(1);
        endTime = time + 2;
        while (endTime > time) {
            telemetry.update();
        }
        motorLeft.setPower(-1);
        endTime = time + 2;
        while (endTime > time) {
            telemetry.update();
        }
        motorLeft.setPower(0);
        motorRight.setPower(1);
        endTime = time + 2;
        while (endTime > time) {
            telemetry.update();
        }
        motorRight.setPower(-1);
        endTime = time + 2;
        while (endTime > time) {
            telemetry.update();
        }
        motorRight.setPower(0);
        motorDown.setPower(1);
        endTime = time + 2;
        while (endTime > time) {
            telemetry.update();
        }
        motorDown.setPower(-1);
        endTime = time + 2;
        while (endTime > time) {
            telemetry.update();
        }
        motorDown.setPower(0);
    }
}
