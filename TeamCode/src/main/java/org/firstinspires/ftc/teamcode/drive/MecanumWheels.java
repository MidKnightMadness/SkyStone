package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.config.HardwareConfig;


public class MecanumWheels extends Drive{
    private double Vx;
    private double Vy;

    //power between 1 and -1
    private double powerFL;
    private double powerFR;
    private double powerBL;
    private double powerBR;

    private DcMotor wheelFL;
    private DcMotor wheelFR;
    private DcMotor wheelBL;
    private DcMotor wheelBR;

    private int distanceY;
    private int distanceX;

    private Position location;
    private Angle angle;



    @Override
    public void init() {
        wheelBL = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BL);
        wheelBR = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BR);
        wheelFL = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FL);
        wheelFR = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FR);

        angle = Angle.fromDegrees(0);

    }

    public void moveTo(Position target){

    }

    public void setPower(double theta, double speed, double rotation){
        Vy = speed*Math.cos(theta);
        Vx = speed*Math.sin(theta);

        powerFL = Vy + Vx + rotation*(HardwareConfig.RADIUS_FRONT + HardwareConfig.RADIUS_SIDE);
        powerFR = Vy - Vx - rotation*(HardwareConfig.RADIUS_FRONT + HardwareConfig.RADIUS_SIDE);
        powerBL = Vy - Vx - rotation*(HardwareConfig.RADIUS_FRONT + HardwareConfig.RADIUS_SIDE);
        powerBR = Vy + Vx + rotation*(HardwareConfig.RADIUS_FRONT + HardwareConfig.RADIUS_SIDE);

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
