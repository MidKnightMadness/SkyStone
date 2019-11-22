package org.firstinspires.ftc.teamcode.delivery;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class PIDTest extends OpMode {

    //note: only works on a REV Robotics Expansion Hub
    private DcMotorEx motor;

    private static final double NEW_P = 1.0;
    private static final double NEW_I = 0.1;
    private static final double NEW_D = 0.0;

    private int targetPos;
    private int initPos;

    @Override
    public void init() {
        //get the motor
        motor = (DcMotorEx) hardwareMap.get(DcMotor.class, "elevator");
        initPos = motor.getCurrentPosition();
        motor.setTargetPosition(0);  //set the target position before setting mode
        //set mode to run_to_position
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1);  //set power (so it actually moves)
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //get coefficients
        PIDFCoefficients pidOld = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION);
        //create and set coefficients
        PIDCoefficients pidNew = new PIDCoefficients(NEW_P, NEW_I, NEW_D);
        motor.setPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION, pidNew);
        //make sure coefficients are set
        PIDFCoefficients pidMotor = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION);

        //display the values
        //telemetry.addLine("Old");
        //telemetry.addData("P", pidOld.p);
        //telemetry.addData("I", pidOld.i);
        //telemetry.addData("D", pidOld.d);
        //telemetry.addData("F", pidOld.f);
        //telemetry.addData("algorithm", pidOld.algorithm);
        //telemetry.addLine("New");
        //telemetry.addData("P", pidNew.p);
        //telemetry.addData("I", pidNew.i);
        //telemetry.addData("D", pidNew.d);
        //telemetry.addLine("Motor");
        //telemetry.addData("P", pidMotor.p);
        //telemetry.addData("I", pidMotor.i);
        //telemetry.addData("D", pidMotor.d);
        //telemetry.addData("F", pidMotor.f);
        //telemetry.addData("algoritm", pidMotor.algorithm);
        //telemetry.update();
    }

    @Override
    public void loop() {
        //set the target position
        //motor.setTargetPosition((int) ((-gamepad1.left_stick_y) * 1000 * (gamepad1.a ? 7 : 1)));
        //motor.setTargetPosition(2000);
        targetPos += -gamepad1.left_stick_y * 10;

        //software limit
        if (targetPos < initPos)
            targetPos = initPos;
        else if (targetPos > 3700 + initPos)
            targetPos = 3700 + initPos;

        if (targetPos > motor.getCurrentPosition())
            motor.setPower(0.5);  //move itself
        else
            motor.setPower(0.5);  //slowly move downwards
        motor.setTargetPosition(targetPos);

        //debug information
        telemetry.addData("target position", motor.getTargetPosition());
        telemetry.addData("current position", motor.getCurrentPosition());
        telemetry.addData("power", motor.getPower());
        telemetry.update();
    }
}
