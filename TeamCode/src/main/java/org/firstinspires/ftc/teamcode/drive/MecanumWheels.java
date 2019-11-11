package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.config.HardwareConfig;


public class MecanumWheels extends Drive{
    private double Vx;
    private double Vy;

    //power between 1 and -1
    public double powerFL;
    public double powerFR;
    public double powerBL;
    public double powerBR;

    private DcMotor wheelFL;
    private DcMotor wheelFR;
    private DcMotor wheelBL;
    private DcMotor wheelBR;

    private int distanceY;
    private int distanceX;

    private Position location;
    private Angle angle;

    //imu
    private BNO055IMU imu;
    private double x;
    private double y;
    private double z;


    @Override
    public void init() {
        wheelBL = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BL);
        wheelBR = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BR);
        wheelFL = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FL);
        wheelFR = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FR);

        angle = Angle.fromDegrees(0);

        BNO055IMU.Parameters parameter = new BNO055IMU.Parameters();
        parameter.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameter);
        imu.startAccelerationIntegration(new org.firstinspires.ftc.robotcore.external.navigation.Position(), new Velocity(), 50);
    }

    public void moveTo(Position target){

    }

    public void loop(){
        x = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).firstAngle;
        y = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle;
        z = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

        //telemetry.addData("x: ", x);
        //telemetry.addData("y: ", y);
        //telemetry.addData("z: ", z);
    }

    //theta in degrees, speed between -1 and 1
    public void setPower(double theta, double speed, double rotation){
        //convert theta to normal circle angles b/c idk if cos / sin works with greggo's weird angle measures and cuz idk what else to do;
        if (theta >= 90 && theta <= 180){
            theta = 270 + (180-theta);
        }
        else{
            theta = 90 - theta;
        }

        //theta needs to be in radians
        theta = theta / 180 * Math.PI;
        Vy = speed*Math.sin(theta);
        Vx = speed*Math.cos(theta);

        powerFL = Vy + Vx + rotation*(HardwareConfig.RADIUS_FRONT + HardwareConfig.RADIUS_SIDE);
        powerFR = Vy - Vx - rotation*(HardwareConfig.RADIUS_FRONT + HardwareConfig.RADIUS_SIDE);
        powerBL = Vy - Vx - rotation*(HardwareConfig.RADIUS_FRONT + HardwareConfig.RADIUS_SIDE);
        powerBR = Vy + Vx + rotation*(HardwareConfig.RADIUS_FRONT + HardwareConfig.RADIUS_SIDE);

        //find max power
        double maxPower = powerFL;
        if (powerFR > maxPower){
            maxPower = powerFR;
        }
        else if (powerBL > maxPower){
            maxPower = powerBL;
        }
        else if (powerBR > maxPower){
            maxPower = powerBR;
        }

        //scale all powers
        powerFL = powerFL*speed/maxPower;
        powerFR = powerFR*speed/maxPower;
        powerBR = powerBR*speed/maxPower;
        powerBL = powerBL*speed/maxPower;

        //set power
        wheelBL.setPower(powerBL);
        wheelBR.setPower(powerBR);
        wheelFL.setPower(powerFL);
        wheelFR.setPower(powerFR);

    }

    public void findDistance(double theta, double speed, int dFL, int dFR, int dBL, int dBR){
        Vy = speed*Math.cos(theta);
        Vx = speed*Math.sin(theta);

        //dFR is encoder value for front right motor
        distanceX = (dFL - dFR + dBR - dBL)/4;
        distanceY = (dFL + dFR + dBR + dBL)/4;

        location = new Position(Distance.fromEncoderTicks(distanceX), Distance.fromEncoderTicks(distanceY), angle);
    }
}


