package org.firstinspires.ftc.teamcode.mineral;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.common.Distance;

//@TeleOp
public class LinearArm extends MineralArm {
    private DcMotor extendMotor;
    private DcMotor rotateMotor;
    private final int TARGET_POSITION_TO_EXTEND = 7550;

    @Override
    public void init() {
        extendMotor = hardwareMap.dcMotor.get(Config.MineralArm.MINERAL_EXTEND_MOTOR);
        //DcMotor test = hardwareMap.dcMotor.get(Config.MineralArm.MINERAL_ROTATE_MOTOR);
        rotateMotor = hardwareMap.dcMotor.get(Config.MineralArm.MINERAL_ROTATE_MOTOR);
        //telemetry.addData("DcMotorEx", DcMotorEx.class.isInstance(test));
        //telemetry.update();
        //rotateMotor = (DcMotorEx) test;
        extendMotor.resetDeviceConfigurationForOpMode();
        rotateMotor.resetDeviceConfigurationForOpMode();


        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotateMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotateMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rotateMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotateMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //rotateMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(15, 0, 1, 0, MotorControlAlgorithm.PIDF));
        //rotateMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION,   new PIDFCoefficients(15, 0, 0,0, MotorControlAlgorithm.PIDF));
        //rotateMotor.setTargetPosition(0);
        //rotateMotor.setPower(1);
        extendMotor.setPower(0);
        rotateMotor.setPower(0);
    }

    private int currentAngle = 0;
    private int extension = 0;
    private int currentAngleOffset = 0;
    private int currentExtensionOffset = 0;

    @Override
    public void loop() {
        if (gamepad2.left_trigger > 0) {
            if (rotateMotor.getCurrentPosition() - currentAngleOffset < 10600) {
                rotateMotor.setPower(gamepad2.left_trigger);
            } else {
                if (gamepad2.b) {
                    rotateMotor.setPower(gamepad2.left_trigger);
                    currentAngleOffset = rotateMotor.getCurrentPosition() - 10600;
                } else {
                    rotateMotor.setPower(0);
                }
            }
        } else if (gamepad2.right_trigger > 0){
            if (rotateMotor.getCurrentPosition() - currentAngleOffset > 0) {
                rotateMotor.setPower(-gamepad2.right_trigger);
            } else {
                if (gamepad2.b) {
                    rotateMotor.setPower(-gamepad2.right_trigger);
                    currentAngleOffset = rotateMotor.getCurrentPosition();
                } else {
                    rotateMotor.setPower(0);
                }
            }
        } else {
            rotateMotor.setPower(0);
        }

        if (gamepad2.right_bumper) {
            if (extendMotor.getCurrentPosition() - currentExtensionOffset < 10600) {
                extendMotor.setPower(1);
            } else {
                if (gamepad2.b) {
                    extendMotor.setPower(1);
                    currentExtensionOffset = extendMotor.getCurrentPosition() - 10600;
                } else {
                    extendMotor.setPower(0);
                }
            }
        } else if (gamepad2.left_bumper){
            if (extendMotor.getCurrentPosition() - currentExtensionOffset > 0) {
                extendMotor.setPower(-1);
            } else {
                if (gamepad2.b) {
                    extendMotor.setPower(-1);
                    currentExtensionOffset = extendMotor.getCurrentPosition();
                } else {
                    extendMotor.setPower(0);
                }
            }
        } else {
            extendMotor.setPower(0);
        }

        //updateRotate();
        telemetry.addData("Current Angle", rotateMotor.getCurrentPosition());
        telemetry.addData("Extension", extendMotor.getCurrentPosition());
        telemetry.addData("Angle Offset", currentAngleOffset);
        telemetry.addData("Extension Offset", currentExtensionOffset);
        telemetry.addData("Time", time);
        telemetry.update();

        //rotateMotor.setTargetPosition(currentAngle);
    }

    public void updateRotate() {
        double power = currentAngle - rotateMotor.getCurrentPosition();
        if (Math.abs(power) < 20) {
            power = 0;
        } else if (Math.abs(power) < 100) {
            power = Math.signum(power) * 0.02;
        } else {
            power = Math.min(Math.max(Math.pow(power / 20.0, 7),-0.5), 0.5);
        }

        telemetry.addData("Power", power);

        rotateMotor.setPower(power);
    }

    @Override
    public void rotate() throws InterruptedException {
        rotateMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rotateMotor.setTargetPosition(2000);
        rotateMotor.setPower(1);
        while (rotateMotor.isBusy() && !Thread.currentThread().isInterrupted());
    }

    public void extend() throws InterruptedException {
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setTargetPosition(5300);
        extendMotor.setPower(1);
        Thread.sleep(2000);
    }
}
