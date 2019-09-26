package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.common.Distance;

//@TeleOp
public class OmniDrive extends Drive {

    private BNO055IMU imu;

    private DcMotor motorUp;
    private DcMotor motorDown;
    private DcMotor motorLeft;
    private DcMotor motorRight;

    //The last initialized position of the IMU
    private Angle startPos = Angle.fromDegrees(0);
    //The rotation of the IMU from the field north
    private Angle theta = Angle.fromDegrees(0);
    private double tempMotors[] = new double[4];
    private double adjustedX = 0;
    private double adjustedY = 0;
    private double adjustedR = 0;
    private double lastKnownRotation = 0;
    private boolean resettingRotation = false;
    private final int BASE_ROTATION_ANGLE = 45;
    private boolean slow = false;
    private boolean bPressed = false;


    private Angle getIMURotation() {
        return Angle.fromDegrees(imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);
    }

    public void init() {
        //Init IMU with parameters and start integration
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit            = BNO055IMU.AngleUnit.DEGREES;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        imu.startAccelerationIntegration(new Position(), new Velocity(), 50);

        //Init motors
        motorUp = hardwareMap.dcMotor.get(Config.Drive.FRONT_LEFT);
        motorDown = hardwareMap.dcMotor.get(Config.Drive.BACK_RIGHT);
        motorLeft = hardwareMap.dcMotor.get(Config.Drive.BACK_LEFT);
        motorRight = hardwareMap.dcMotor.get(Config.Drive.FRONT_RIGHT);

        motorUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorDown.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorUp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorDown.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        resetHeading();
    }

    @Override
    public void beginTranslation(Distance distance, double speed) {

    }

    @Override
    public void beginTranslationSide(Distance distance, double speed) {

    }

    @Override
    public void beginTranslationDiagonal(Distance distance, double speed) {

    }

    @Override
    public void beginRotation(Angle rotation, double speed) {

    }

    @Override
    public void beginMovement(Distance distance, Angle rotation, double speed) throws InterruptedException {

    }

    @Override
    public void backward() {

    }

    @Override
    public void stopBack() {

    }

    @Override
    public boolean isBusy() {
        return false;
    }

    private void resetHeading() {
        startPos = getIMURotation().subtract(Angle.fromDegrees(BASE_ROTATION_ANGLE));
    }

    private void targPow(DcMotor motor, double speed) {
        motor.setPower(speed);
    }

    public void loop() {
        if ((gamepad1.a || gamepad2.a)) {
            resetHeading();
        }
        {
            theta = getIMURotation().subtract(startPos);
            adjustedX = gamepad1.left_stick_x;
            adjustedY = gamepad1.left_stick_y;
            adjustedR = gamepad1.right_stick_x;

            if (gamepad1.y && !bPressed) {
                bPressed = true;
                slow = !slow;
            } else if (!gamepad1.y){
                bPressed = false;
            }

            boolean slower = gamepad1.left_stick_button || gamepad1.right_stick_button;

            double translateScale = Math.pow(Math.hypot(adjustedX, adjustedY), 5) * (1 - Math.min(Math.pow(Math.abs(adjustedR), 2), 0.6)) * (slow ? 0.6 : 1) * (slower ? 0.3 : 1);
            Angle targetDirection = Angle.aTan(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double rotateScale = (Math.pow(Math.abs(adjustedR), 5) * Math.signum(-adjustedR) * (1 - Math.abs(translateScale)) * (slow ? 0.6 : 1) * (slower ? 0.3 : 1));

            telemetry.addData("AdjustedR", adjustedR);
            telemetry.addData("Theta", theta.toDegrees());
            telemetry.addData("Target Direction", targetDirection.toDegrees());

            telemetry.addData("Rotate Scale", rotateScale);
            telemetry.addData("SLOW", slow);
            targetDirection.add(theta);
            telemetry.addData("Target Direction", targetDirection.toDegrees());
            telemetry.addData("Motor 0", Math.cos(targetDirection.getRadians()));
            telemetry.addData("Motor 1", -Math.cos(targetDirection.getRadians()));
            telemetry.addData("Motor 2", Math.sin(targetDirection.getRadians()));
            telemetry.addData("Motor 3", -Math.sin(targetDirection.getRadians()));
            tempMotors[2] = -Math.cos(targetDirection.getRadians());
            tempMotors[3] = Math.cos(targetDirection.getRadians());
            tempMotors[0] = -Math.sin(targetDirection.getRadians());
            tempMotors[1] = Math.sin(targetDirection.getRadians());

            double scale = Math.max(Math.max(Math.abs(tempMotors[0]), Math.abs(tempMotors[1])), Math.max(Math.abs(tempMotors[2]), Math.abs(tempMotors[3])));
            if (scale == 0) {
                scale = 0;
            } else {
                scale = translateScale / (scale);
            }

            tempMotors[0] *= scale;
            tempMotors[1] *= scale;
            tempMotors[2] *= scale;
            tempMotors[3] *= scale;

            tempMotors[0] += rotateScale;
            tempMotors[1] += rotateScale;
            tempMotors[2] += rotateScale;
            tempMotors[3] += rotateScale;

            targPow(motorUp, tempMotors[0]);
            targPow(motorDown, tempMotors[1]);
            targPow(motorLeft, tempMotors[2]);
            targPow(motorRight, tempMotors[3]);

        }
    }

    public void stop() {}

    @Override
    public void beginTranslationAngled(Distance distance, int sign, double speed) {

    }

}