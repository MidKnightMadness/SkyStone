package org.firstinspires.ftc.teamcode.drive;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.common.Distance;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TankDrive: A simple Drive implementation for a tank-drive robot.
 *
 * Created by Gregory on 9/10/18.
 */

//@TeleOp
public class TankDrive extends Drive {

    // Declare Motors
    // DO NOT assign them to anything yet because hardwareMap is not necessarily defined until init runs.
    //public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    private volatile boolean ato = false;
    private Thread send;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get(Config.Drive.FRONT_LEFT);    // Retrieve the motor from the hardwareMap with the name set in the Config class
        frontRight = hardwareMap.dcMotor.get(Config.Drive.FRONT_RIGHT);
        backLeft = hardwareMap.dcMotor.get(Config.Drive.BACK_LEFT);
        backRight = hardwareMap.dcMotor.get(Config.Drive.BACK_RIGHT);

        frontLeft.resetDeviceConfigurationForOpMode();
        frontRight.resetDeviceConfigurationForOpMode();
        backLeft.resetDeviceConfigurationForOpMode();
        backRight.resetDeviceConfigurationForOpMode();

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

        send = new Thread(new Runnable() {
            public void run() {
                ato = true;
                telemetry.setAutoClear(false);

                while (ato && !Thread.currentThread().isInterrupted()){
                    telemetry.addData("ato", ato);
                    telemetry.update();
                    try {
                        Thread.sleep(250);
                    } catch (Exception e) {}
                }
            }
        });
        send.start();
    }


    public void init_loop() {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void backward() {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setPower(1);
        frontRight.setPower(-1);
        backLeft.setPower(1);
        backRight.setPower(-1);
    }

    public void stopBack() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int[] currentPos(){
        int[] a = {frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(),
                backRight.getCurrentPosition(), backLeft.getCurrentPosition()};
        return a;
    }


    @Override
    public void loop() {
        telemetry.addData("RIGHT", gamepad1.right_stick_y);
        telemetry.addData("LEFT", gamepad1.left_stick_y);
        if (gamepad1.x) {
            frontLeft.setPower(gamepad1.right_stick_y);
            frontRight.setPower(gamepad1.right_stick_y);
            backLeft.setPower(-gamepad1.left_stick_y);
            backRight.setPower(-gamepad1.left_stick_y);
        } else {
            frontLeft.setPower(gamepad1.left_stick_y);
            frontRight.setPower(-gamepad1.right_stick_y);
            backLeft.setPower(gamepad1.left_stick_y);
            backRight.setPower(-gamepad1.right_stick_y);
        }

        telemetry.update();
    }

    @Override
    public void beginTranslation(Distance distance, double speed) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance.toEncoderTicks());
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + distance.toEncoderTicks());
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - distance.toEncoderTicks());
        backRight.setTargetPosition(backRight.getCurrentPosition() + distance.toEncoderTicks());

        frontLeft.setPower(-speed);
        frontRight.setPower(speed);
        backLeft.setPower(-speed);
        backRight.setPower(speed);

    }

    public void beginTranslationAngled(Distance distance, int sign, double speed) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance.toEncoderTicks() - (int)Math.round(Math.tan(Math.toRadians(sign)))*distance.toEncoderTicks());
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + distance.toEncoderTicks() - (int)Math.round(Math.tan(Math.toRadians(sign)))*distance.toEncoderTicks());
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - distance.toEncoderTicks() + (int)Math.round(Math.tan(Math.toRadians(sign)))*distance.toEncoderTicks());
        backRight.setTargetPosition(backRight.getCurrentPosition() + distance.toEncoderTicks() + (int)Math.round(Math.tan(Math.toRadians(sign)))*distance.toEncoderTicks());

        frontLeft.setPower((speed - 0.1) - (int)Math.round(Math.tan(Math.toRadians(sign)))*speed);
        frontRight.setPower(-(speed - 0.1) - (int)Math.round(Math.tan(Math.toRadians(sign)))*speed);
        backLeft.setPower(-(speed - 0.1) + (int)Math.round(Math.tan(Math.toRadians(sign)))*speed);
        backRight.setPower((speed - 0.1) + (int)Math.round(Math.tan(Math.toRadians(sign)))*speed);

    }

    @Override
    public void beginTranslationSide(Distance distance, double speed) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance.toEncoderTicks());
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - distance.toEncoderTicks());
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + distance.toEncoderTicks());
        backRight.setTargetPosition(backRight.getCurrentPosition() + distance.toEncoderTicks());

        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
    }

    @Override
    public void beginTranslationDiagonal(Distance distance, double speed) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance.toEncoderTicks());
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - distance.toEncoderTicks());
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + distance.toEncoderTicks());
        backRight.setTargetPosition(backRight.getCurrentPosition() + distance.toEncoderTicks());

        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
    }

    @Override
    public void beginRotation(Angle angle, double speed) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - angle.toEncoderTicks()); // Note negative
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - angle.toEncoderTicks());
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - angle.toEncoderTicks());   // Note negative
        backRight.setTargetPosition(backRight.getCurrentPosition() - angle.toEncoderTicks());

        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
    }

    @Override
    public void beginMovement(Distance distance, Angle rotation, double speed) {
        beginTranslation(distance, speed);
        while (isBusy() && !Thread.currentThread().isInterrupted());
        beginRotation(rotation, speed);
    }

    public boolean isBusy() {
        return Math.abs(frontLeft.getCurrentPosition() - frontLeft.getTargetPosition()) > 10;
    }

    public void stop() {
        ato = false;
        send.interrupt();
        try {
            send.join();
        } catch (Exception e) {

        }
        Log.d("STOPPING THREAD", "YAY!");
        Log.d("STOPPING THREAD", "YAY!");
        Log.d("STOPPING THREAD", "YAY!");
        Log.d("STOPPING THREAD", "YAY!");
        Log.d("STOPPING THREAD", "YAY!");
        Log.d("STOPPING THREAD", "YAY!");
        Log.d("STOPPING THREAD", "YAY!");
        Log.d("STOPPING THREAD", "YAY!");

    }
}