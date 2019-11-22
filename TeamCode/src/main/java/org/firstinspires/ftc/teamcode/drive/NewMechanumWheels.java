package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.config.HardwareConfig;
import org.firstinspires.ftc.teamcode.navigation.Navigation;

public class NewMechanumWheels extends Drive {

    private Angle initialRotation = Angle.fromDegrees(0);
    private Angle currentRotation = Angle.fromDegrees(0); // from same reference angle as initialRotation
    private Angle targetTranslation = Angle.fromDegrees(0);
    private double speed = 0;
    private double rotationalSpeed = 0;
    private static final double MAX_VELOCITY = 2380;
    private Position currentPosition = new Position(Distance.fromEncoderTicks(0), Distance.fromEncoderTicks(0), Angle.fromDegrees(0));

    private DcMotorEx wheelFL;
    private DcMotorEx wheelFR;
    private DcMotorEx wheelBL;
    private DcMotorEx wheelBR;
    private int lastPositionFL;
    private int lastPositionFR;
    private int lastPositionBL;
    private int lastPositionBR;
    private BNO055IMU imu;
    private Position targetPosition;
    private Navigation navigation;

    @Override
    public Position getPosition() {
        return currentPosition;
    }

    @Override
    public void resetPosition(Position realPosition) {
        currentPosition = realPosition;
        lastPositionBL = wheelBL.getCurrentPosition();
        lastPositionBR = wheelBR.getCurrentPosition();
        lastPositionFL = wheelFL.getCurrentPosition();
        lastPositionFR = wheelFR.getCurrentPosition();
    }

    public void resetHeading() {
        initialRotation = currentRotation;
    }

    @Override
    public void init() {
        wheelBL = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BL);
        wheelBR = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BR);
        wheelFL = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FL);
        wheelFR = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FR);


        wheelBL.resetDeviceConfigurationForOpMode();
        wheelBR.resetDeviceConfigurationForOpMode();
        wheelFL.resetDeviceConfigurationForOpMode();
        wheelFR.resetDeviceConfigurationForOpMode();

        wheelBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // changed to run using encoder...

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
        lastPositionBL = wheelBL.getCurrentPosition();
        lastPositionBR = wheelBR.getCurrentPosition();
        lastPositionFL = wheelFL.getCurrentPosition();
        lastPositionFR = wheelFR.getCurrentPosition();
    }

    public void setDirection(Angle theta, double speed, double rotation) {
        internalSetDirection(theta, speed, rotation);
        targetPosition = null;
    }

    private void internalSetDirection(Angle theta, double speed, double rotation) {
        targetTranslation = theta;
        this.speed = speed;
        this.rotationalSpeed = rotation;
    }

    public void setTarget(Position position, Navigation navigation) {
        targetPosition = position;
        this.navigation = navigation;
    }

    public boolean isComplete() {
        return navigation == null;
    }

    public Angle getCurrentRotation() {
        return currentRotation.copy().subtract(initialRotation);
    }


    private double trim(double value) {
        return value < -1 ? -1 : value > 1 ? 1 : value;
    }

    private static final double K_P_TRANSLATION = 4;
    private static final double K_P_ROTATION = 4;

    private void updateTarget() {
        if (targetPosition != null) { // we're navigating somewhere specific

            double fieldX = targetPosition.getX().toInches() - navigation.getPosition().getX().toInches() / 4;
            double fieldY = targetPosition.getY().toInches() - navigation.getPosition().getY().toInches() / 4;
            double fieldRot = targetPosition.getTheta().copy().subtract(getCurrentRotation()).getDegrees() / 4;
            if (Math.abs(fieldX) < 1 / K_P_TRANSLATION && Math.abs(fieldY) < 1 / K_P_TRANSLATION && fieldRot < 1 / K_P_ROTATION) {
                targetPosition = null;
            } else {
                internalSetDirection(
                        Angle.aTan(fieldX, fieldY),
                        trim(fieldX + fieldY),
                        trim(fieldRot)
                );
            }
        }
    }
    @Override
    public void update() {
        super.update();
        updatePosition();
        updateTarget();
        updateSpeed();
    }

    private void updateSpeed() {
        currentRotation = Angle.fromDegrees(imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);
        Angle theta = targetTranslation.copy().add(currentRotation).subtract(initialRotation);

        double vX = Math.sin(theta.toRadians());
        double vY = Math.cos(theta.toRadians());
        telemetry.addData("Current Rotation", currentRotation.toDegrees());
        telemetry.addData("Starting Angle", initialRotation.toDegrees());
        telemetry.addData("Given angle", theta.toDegrees());
        telemetry.addData("Target angle", targetTranslation.toDegrees());
        telemetry.addData("X Velocity", vX);
        telemetry.addData("Y Velocity", vY);


        vX *= Math.sqrt(2);
        double translateFL = vY + vX;
        double translateFR = -(vY - vX); // the motors are facing opposite directions
        double translateBL = vY - vX;
        double translateBR = -(vY + vX);

        double maxSpeed = Math.max(Math.max(Math.abs(translateFL), Math.abs(translateFR)), Math.max(Math.abs(translateBL), Math.abs(translateBR)));

        translateFL *= speed / maxSpeed;
        translateFR *= speed / maxSpeed;
        translateBL *= speed / maxSpeed;
        translateBR *= speed / maxSpeed;

        double rotateFL = rotationalSpeed;
        double rotateFR = rotationalSpeed;
        double rotateBL = rotationalSpeed;
        double rotateBR = rotationalSpeed;

        double velocityFL = rotateFL + translateFL;
        double velocityFR = rotateFR + translateFR;
        double velocityBL = rotateBL + translateBL;
        double velocityBR = rotateBR + translateBR;

        if (Math.abs(velocityFL) > 1 || Math.abs(velocityFR) > 1 || Math.abs(velocityBL) > 1 || Math.abs(velocityBR) > 1) {
            double maxVelocity = Math.max(Math.max(Math.abs(translateFL), Math.abs(translateFR)), Math.max(Math.abs(translateBL), Math.abs(translateBR)));

            velocityFL /= maxVelocity;
            velocityFR /= maxVelocity;
            velocityBL /= maxVelocity;
            velocityBR /= maxVelocity;
        }

        telemetry.addData("BL: ", velocityBL);
        telemetry.addData("BR: ", velocityBR);
        telemetry.addData("FL: ", velocityFL);
        telemetry.addData("FR: ", velocityFR);

        wheelBL.setVelocity(velocityBL * MAX_VELOCITY);
        wheelBR.setVelocity(velocityBR * MAX_VELOCITY);
        wheelFL.setVelocity(velocityFL * MAX_VELOCITY);
        wheelFR.setVelocity(velocityFR * MAX_VELOCITY);
    }

    private void updatePosition() {
        double a = wheelBL.getCurrentPosition() - lastPositionBL;
        double b = wheelBR.getCurrentPosition() - lastPositionBR;
        double c = wheelFL.getCurrentPosition() - lastPositionFL;
        double d = wheelFR.getCurrentPosition() - lastPositionFR;

        lastPositionBL += a;
        lastPositionBR += b;
        lastPositionFL += c;
        lastPositionFR += d;

        double rot = (a + b + c + d) / 4;


        a -= rot;
        b -= rot;
        c -= rot;
        d -= rot;

        double transY = (a + b - c - d) / 4;

        double transX = (a - b + c - d) / (4 * Math.sqrt(2));

        telemetry.addData("Translation X", transX);
        telemetry.addData("Translation Y", transY);
        telemetry.addData("Rotation", rot);


        Angle theta = currentRotation.copy().subtract(initialRotation);
        double fieldX = transY * Math.sin(theta.toRadians()) + transX * Math.cos(theta.toRadians());
        double fieldY = transY * Math.cos(theta.toRadians()) + transX * Math.sin(theta.toRadians());

        currentPosition.add(Distance.fromEncoderTicks((int) fieldX), Distance.fromEncoderTicks((int) fieldY), Angle.fromDegrees(0));
        currentPosition.setTheta(theta);
        telemetry.addData("a", a);
        telemetry.addData("b", b);
        telemetry.addData("c", c);
        telemetry.addData("d", d);

        telemetry.addData("fieldX", fieldX);
        telemetry.addData("fieldY", fieldY);
        telemetry.addData("fieldX", currentPosition.getX().toInches());
        telemetry.addData("fieldY", currentPosition.getY().toInches());
        telemetry.addData("theta", theta.toDegrees());
    }
}
