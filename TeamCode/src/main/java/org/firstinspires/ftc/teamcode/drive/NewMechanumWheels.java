package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.config.HardwareConfig;

public class NewMechanumWheels extends Drive {

    private Angle initialRotation = Angle.fromDegrees(0);
    private Angle currentRotation = Angle.fromDegrees(0); // from same reference angle as initialRotation
    private Angle targetTranslation = Angle.fromDegrees(0);
    private double speed = 0;
    private double rotationalSpeed = 0;
    private Angle targetRotation = Angle.fromDegrees(0);
    private final double K_P_ROTATION = 0.1; // PID P constant

    private DcMotor wheelFL;
    private DcMotor wheelFR;
    private DcMotor wheelBL;
    private DcMotor wheelBR;
    private BNO055IMU imu;


    @Override
    public void moveTo(Position target) {

    }

    public void resetHeading() {
        initialRotation = currentRotation;
    }

    @Override
    public void init() {
        wheelBL = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BL);
        wheelBR = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BR);
        wheelFL = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FL);
        wheelFR = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FR);

        wheelBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        wheelBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        imu.startAccelerationIntegration(new org.firstinspires.ftc.robotcore.external.navigation.Position(), new Velocity(), 50);
        initialRotation = Angle.fromDegrees(imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);
    }

    public void setDirection(Angle theta, double speed, double rotation) {
        targetTranslation = theta;
        this.speed = speed;
        this.rotationalSpeed = speed;
    }

    @Override
    public void update() {
        super.update();
        currentRotation = Angle.fromDegrees(imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);
        Angle theta = targetTranslation.copy().add(currentRotation).subtract(initialRotation);

        double vX = Math.sin(targetTranslation.toRadians());
        double vY = Math.cos(targetTranslation.toRadians());
        telemetry.addData("Current Rotation", currentRotation.toDegrees());
        telemetry.addData("Starting Angle", initialRotation.toDegrees());
        telemetry.addData("Given angle", theta.toDegrees());
        telemetry.addData("Target angle", targetTranslation.toDegrees());
        telemetry.addData("X Velocity", vX);
        telemetry.addData("Y Velocity", vY);

        double powerFL = vY + vX;
        double powerFR = -(vY - vX); // the motors are facing opposite directions
        double powerBL = vY - vX;
        double powerBR = -(vY + vX);

        // add the difference of target rotation and current rotation if rotational speed is none (this keeps the robot straight when translating)
        if (rotationalSpeed != 0) {
            powerFL += rotationalSpeed;
            powerFR += rotationalSpeed;
            powerBL += rotationalSpeed;
            powerBR += rotationalSpeed;
            targetRotation = currentRotation.copy().subtract(initialRotation);
        } else {
            double adjustedRotation = targetRotation.copy().subtract(currentRotation.copy().subtract(initialRotation)).getDegrees() * K_P_ROTATION;
            powerFL += adjustedRotation;
            powerFR += adjustedRotation;
            powerBL += adjustedRotation;
            powerBR += adjustedRotation;
        }

        telemetry.addData("BL: ", powerBL);
        telemetry.addData("BR: ", powerBR);
        telemetry.addData("FL: ", powerFL);
        telemetry.addData("FR: ", powerFR);

        double maxSpeed = Math.max(Math.max(Math.abs(powerFL), Math.abs(powerFR)), Math.max(Math.abs(powerBL), Math.abs(powerBR)));
        powerFL *= (speed / maxSpeed);
        powerFR *= (speed / maxSpeed);
        powerBL *= (speed / maxSpeed);
        powerBR *= (speed / maxSpeed);

        wheelBL.setPower(powerBL);
        wheelBR.setPower(powerBR);
        wheelFL.setPower(powerFL);
        wheelFR.setPower(powerFR);
    }
}
