package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.Config;

public class BaseAutonomous extends LinearOpMode {
    protected DcMotor frontLeft;
    protected DcMotor frontRight;
    protected DcMotor backLeft;
    protected DcMotor backRight;
    protected DcMotor winchMotor;
    protected DcMotor armMotor;
    protected DcMotor extensionMotor;

    private Servo servo;

    private TouchSensor touchSensor;

    private Visual visual;

    public void runOpMode() {}

    void initialize() {
        frontLeft = hardwareMap.dcMotor.get(Config.Drive.FRONT_LEFT);
        frontLeft.resetDeviceConfigurationForOpMode();
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight = hardwareMap.dcMotor.get(Config.Drive.BACK_RIGHT);
        backRight.resetDeviceConfigurationForOpMode();
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft = hardwareMap.dcMotor.get(Config.Drive.BACK_LEFT);
        backLeft.resetDeviceConfigurationForOpMode();
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight = hardwareMap.dcMotor.get(Config.Drive.FRONT_RIGHT);
        frontRight.resetDeviceConfigurationForOpMode();
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        winchMotor = hardwareMap.dcMotor.get(Config.Lift.WINCH_MOTOR);
        winchMotor.resetDeviceConfigurationForOpMode();
        winchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        winchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winchMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        extensionMotor = hardwareMap.dcMotor.get(Config.Mineral.EXTENSION_MOTOR);
        extensionMotor.resetDeviceConfigurationForOpMode();
        extensionMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor = hardwareMap.dcMotor.get(Config.Mineral.ARM_MOTOR);
        armMotor.resetDeviceConfigurationForOpMode();
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        servo = hardwareMap.servo.get(Config.Marker.MARKER_SERVO);
        Visual.DEBUG = true;
        visual = new Visual();
        visual.telemetry = telemetry;
        visual.hardwareMap = hardwareMap;
        visual.init();

    }
    void moveBotEncoders(int distance, int power)  {
        // ** Will be different for horizontal movement
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() - distance);
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }
    void turnBotEncoders(int distance, double power)  {
        // ** May be changed
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() + distance);
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }
    void moveDiagonalForward(int distance, double power) {
        if (distance > 0) {
            backRight.setTargetPosition(backRight.getCurrentPosition() - distance);
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance);
            backRight.setPower(power);
            frontLeft.setPower(power);
        } else {
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + distance);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + distance);
            backLeft.setPower(power);
            frontRight.setPower(power);
        }
    }

    // --NOT USED
    void moveBot(int time, double speed)  {
        frontLeft.setPower(-speed);
        frontRight.setPower(-speed);
        backLeft.setPower(-speed);
        backRight.setPower(-speed);
        sleep(time);
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    void turnBot(int time, double speed)  {
        frontLeft.setPower(-speed);
        backLeft.setPower(-speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);
        sleep(time);
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    void testMotors(int time, int speed)  {
        frontLeft.setPower(speed);
        sleep(time);
        frontLeft.setPower(0);
        frontRight.setPower(speed);
        sleep(time);
        frontRight.setPower(0);
        backLeft.setPower(speed);
        sleep(time);
        backLeft.setPower(0);
        backRight.setPower(speed);
        sleep(time);
        backRight.setPower(0);
    }

    void holdMarker() { servo.setPosition(0); }

    void dropMarker() { servo.setPosition(1); }

    void setHeight(int target, int power) {
        winchMotor.setTargetPosition(target);
        winchMotor.setPower(power);
    }

    boolean touchSensorPressed() {
        return touchSensor.isPressed();
    }

    boolean seesGold() {
        try {
            return visual.isGoldMineral(false) == 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --NEW DRIVETRAIN
    void moveEncoderVertical (int distance, double power) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() - distance);
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }
    void moveEncoderHorizontal (int distance, double power) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() + distance);
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }
}
