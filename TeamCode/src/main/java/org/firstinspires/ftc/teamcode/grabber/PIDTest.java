package org.firstinspires.ftc.teamcode.grabber;

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
    //private DcMotor motorThatWorks;

    private static final double NEW_P = 2.5;
    private static final double NEW_I = 0.1;
    private static final double NEW_D = 0.2;

    @Override
    public void init() {
        motor = (DcMotorEx) hardwareMap.get(DcMotor.class, "motor");
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1);

        //motorThatWorks = hardwareMap.get(DcMotor.class, "motor");
        //motorThatWorks.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //motorThatWorks.setPower(1);

        /*
        PIDFCoefficients pidOld = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION);
        PIDCoefficients pidNew = new PIDCoefficients(NEW_P, NEW_I, NEW_D);
        motor.setPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION, pidNew);
        PIDFCoefficients pidMotor = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addLine("Old");
        telemetry.addData("P", pidOld.p);
        telemetry.addData("I", pidOld.i);
        telemetry.addData("D", pidOld.d);
        telemetry.addData("F", pidOld.f);
        telemetry.addData("algorithm", pidOld.algorithm);
        telemetry.addLine("New");
        telemetry.addData("P", pidNew.p);
        telemetry.addData("I", pidNew.i);
        telemetry.addData("D", pidNew.d);
        telemetry.addLine("Motor");
        telemetry.addData("P", pidMotor.p);
        telemetry.addData("I", pidMotor.i);
        telemetry.addData("D", pidMotor.d);
        telemetry.addData("F", pidMotor.f);
        telemetry.addData("algoritm", pidMotor.algorithm);
        telemetry.update();
        */
    }

    @Override
    public void loop() {
        motor.setTargetPosition((int) ((gamepad1.left_stick_x - gamepad1.left_stick_y) * 1000));
        //motorThatWorks.setTargetPosition((int) ((gamepad1.left_stick_x - gamepad1.left_stick_y) * 1000));

        telemetry.addData("port", motor.getPortNumber());
        telemetry.addData("target position", motor.getTargetPosition());
        telemetry.addData("current position", motor.getCurrentPosition());
        telemetry.update();
    }
}
