package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.Config;

@TeleOp
public class MecanumDrive extends OpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    public void init() {
        frontLeft = hardwareMap.dcMotor.get(Config.Drive.FRONT_LEFT);
        frontLeft.resetDeviceConfigurationForOpMode();
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight = hardwareMap.dcMotor.get(Config.Drive.BACK_RIGHT);
        backRight.resetDeviceConfigurationForOpMode();
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft = hardwareMap.dcMotor.get(Config.Drive.BACK_LEFT);
        backLeft.resetDeviceConfigurationForOpMode();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight = hardwareMap.dcMotor.get(Config.Drive.FRONT_RIGHT);
        frontRight.resetDeviceConfigurationForOpMode();
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Front Left", new Func<Integer>() {
            @Override
            public Integer value() {
                return frontLeft.getCurrentPosition();
            }
        });
        telemetry.addData("Front Right", new Func<Integer>() {
            @Override
            public Integer value() {
                return frontRight.getCurrentPosition();
            }
        });
        telemetry.addData("Back Left", new Func<Integer>() {
            @Override
            public Integer value() {
                return backLeft.getCurrentPosition();
            }
        });
        telemetry.addData("Back Right", new Func<Integer>() {
            @Override
            public Integer value() {
                return backRight.getCurrentPosition();
            }
        });


    }

    public void loop() {
        if (Math.abs(gamepad1.left_stick_x) + Math.abs(gamepad1.left_stick_y) > Math.abs(gamepad1.right_stick_x)) {
            if (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y)) {
                frontLeft.setPower(gamepad1.left_stick_x);
                frontRight.setPower(-gamepad1.left_stick_x);
                backLeft.setPower(-gamepad1.left_stick_x);
                backRight.setPower(gamepad1.left_stick_x);
            } else {
                frontLeft.setPower(-gamepad1.left_stick_y);
                backLeft.setPower(-gamepad1.left_stick_y);
                frontRight.setPower(-gamepad1.left_stick_y);
                backRight.setPower(-gamepad1.left_stick_y);
            }
        } else {
            frontLeft.setPower(gamepad1.right_stick_x);
            backLeft.setPower(gamepad1.right_stick_x);
            frontRight.setPower(-gamepad1.right_stick_x);
            backRight.setPower(-gamepad1.right_stick_x);
        }
        telemetry.update();
    }
}