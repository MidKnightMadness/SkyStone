package org.firstinspires.ftc.teamcode.delivery;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.config.HardwareConfig;

public class Elevator extends Delivery {

    private static final double NEW_P = 1.0;
    private static final double NEW_I = 0.1;
    private static final double NEW_D = 0.0;
    private static final double NEW_F = 0.0;

    private DcMotorEx vertical;
    private int initHeight;

    private DcMotor horizontal;
    private int initDepth;

    @Override
    public void init() {
        vertical = (DcMotorEx) hardwareMap.get(DcMotor.class, HardwareConfig.ELEVATOR);
        vertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        initHeight = vertical.getCurrentPosition();
        vertical.setTargetPosition(initHeight);
        vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vertical.setPower(1);
        vertical.setDirection(DcMotor.Direction.REVERSE);
        vertical.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //noinspection deprecation
        vertical.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(NEW_P, NEW_I, NEW_D, NEW_F, MotorControlAlgorithm.LegacyPID));

        horizontal = hardwareMap.dcMotor.get(HardwareConfig.HORIZONTAL);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        initDepth = horizontal.getCurrentPosition();
        horizontal.setTargetPosition(initDepth);
        horizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        horizontal.setPower(0.5);
        horizontal.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void setHeight(double blocks) {
        int targetPos = (int) (blocks * 10000);

        vertical.setTargetPosition(targetPos + initHeight);
    }

    @Override
    public void setDepth(double inches) {
        int targetPos = (int) (inches * 10);  //change later

        horizontal.setTargetPosition(targetPos + initDepth);
    }
    
    @Override
    public void setDepthRaw(int encoderTicks) {
        horizontal.setTargetPosition(encoderTicks + initDepth);
    }

    @Override
    public boolean isComplete() {
        return Math.abs(vertical.getCurrentPosition() - vertical.getTargetPosition()) < 100 ;//&&
                //Math.abs(horizontal.getCurrentPosition() - horizontal.getTargetPosition()) < 1000;
    }
    
    private boolean override = false;
    public void setOverride(boolean override) {
        this.override = override;
        telemetry.addData("Override", override);
        if (override) {
            initDepth = horizontal.getCurrentPosition();
            initHeight = vertical.getCurrentPosition();
        }
    }
    
    public int getDepth() {
        return horizontal.getCurrentPosition() - initDepth;
    }
    
    public int getHeight() {
        return vertical.getCurrentPosition() - initHeight;
    }
    
    public void setDepthPower(double speed) {
        horizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        /*if (speed < 0 && horizontal.getCurrentPosition() < 0) {
            speed = 0;
        } else*/
        
        horizontal.setPower(speed);
        telemetry.addData("depth", getDepth());
    }
    
    public void holdDepth() {
        if (horizontal.getMode() == DcMotor.RunMode.RUN_WITHOUT_ENCODER) {
            horizontal.setPower(0);
            int pos = getDepth();
            horizontal.setTargetPosition(pos + initDepth);
            horizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            horizontal.setPower(0.5);
        }
        telemetry.addData("depth", getDepth());
    }
    
    public void setHeightPower(double speed) {
        vertical.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (!override && speed < 0 && getHeight() < 0) {
            speed = 0;
        }
        vertical.setPower(speed);
        telemetry.addData("height", getHeight());
    }

    public void holdHeight() {
        if (vertical.getMode() == DcMotor.RunMode.RUN_WITHOUT_ENCODER) {
            vertical.setPower(0);
            int pos = getHeight();
            if (pos < 0) pos = 0;
            vertical.setTargetPosition(pos + initHeight);
            vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            vertical.setPower(1);
        }
        telemetry.addData("height", getHeight());
        telemetry.addData("initHeight", initHeight);
    }
    
    @Override
    public void update()
    {
        telemetry.addData("horizontalPosition", horizontal.getCurrentPosition());
    }
}
