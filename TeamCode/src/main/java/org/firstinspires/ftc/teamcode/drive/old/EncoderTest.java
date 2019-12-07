package org.firstinspires.ftc.teamcode.drive.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.config.HardwareConfig;

@Autonomous
public class EncoderTest extends LinearOpMode {
    private DcMotorEx wheelFL;
    private DcMotorEx wheelFR;
    private DcMotorEx wheelBL;
    private DcMotorEx wheelBR;

    @Override
    public void runOpMode() throws InterruptedException {
        wheelBL = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BL);
        wheelBR = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BR);
        wheelFL = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FL);
        wheelFR = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FR);

        wheelBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        wheelBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wheelFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        telemetry.addLine("Front Left Wheel");
        telemetry.update();
        wheelFL.setVelocity(2380);
        while (getRuntime() < 4) {
            telemetry.addData("FL", wheelFL.getCurrentPosition());
            telemetry.addData("FL V", wheelFL.getVelocity());
            telemetry.update();
            idle();
        }
        wheelFL.setPower(0);

        telemetry.addLine("Front Right Wheel");
        telemetry.update();
        wheelFR.setVelocity(2380);
        while (getRuntime() < 7) {
            telemetry.addData("FR", wheelFR.getCurrentPosition());
            telemetry.addData("FR V", wheelFR.getVelocity());
            telemetry.update();
            idle();
        }
        wheelFR.setPower(0);

        telemetry.addLine("Back Left Wheel");
        telemetry.update();
        wheelBL.setVelocity(2380);
        while (getRuntime() < 10) {
            telemetry.addData("BL", wheelBL.getCurrentPosition());
            telemetry.addData("BL V", wheelBL.getVelocity());
            telemetry.update();
            idle();
        }
        wheelBL.setPower(0);

        telemetry.addLine("Back Right Wheel");
        telemetry.update();
        wheelBR.setVelocity(2380);
        while (getRuntime() < 13) {
            telemetry.addData("BR", wheelBR.getCurrentPosition());
            telemetry.addData("BR V", wheelBR.getVelocity());
            telemetry.update();
            idle();
        }
        wheelBR.setPower(0);
    }
}
