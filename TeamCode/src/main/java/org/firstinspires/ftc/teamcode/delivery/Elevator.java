package org.firstinspires.ftc.teamcode.delivery;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.config.HardwareConfig;

public class Elevator extends Delivery {

    private static final double NEW_P = 1.0;
    private static final double NEW_I = 0.1;
    private static final double NEW_D = 0.0;

    private DcMotorEx vertical;
    private int initHeight;

    private DcMotor horizontal;
    private int initDepth;

    @Override
    public void init() {
        vertical = (DcMotorEx) hardwareMap.get(DcMotor.class, HardwareConfig.ELEVATOR);
        initHeight = vertical.getCurrentPosition();
        vertical.setTargetPosition(initHeight);
        vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vertical.setPower(0.5);
        vertical.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        vertical.setPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDCoefficients(NEW_P, NEW_I, NEW_D));

        horizontal = hardwareMap.dcMotor.get(HardwareConfig.HORIZONTAL);
        initDepth = horizontal.getCurrentPosition();
        horizontal.setTargetPosition(initDepth);
        horizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        horizontal.setPower(1);
        horizontal.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void setHeight(double blocks) {
        int targetPos = (int) (blocks * 10000);

        if (targetPos < 0)
            targetPos = 0;

        vertical.setTargetPosition(targetPos + initHeight);
    }

    @Override
    public void setDepth(double inches) {
        int targetPos = (int) (inches * 10000);  //change later

        if (targetPos > 8000)
            targetPos = 8000;
        else if (targetPos < -4500)
            targetPos = -4500;

        horizontal.setTargetPosition(targetPos + initDepth);
    }
}
