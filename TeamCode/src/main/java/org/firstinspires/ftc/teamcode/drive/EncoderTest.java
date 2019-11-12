package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.config.HardwareConfig;

@Autonomous
public class EncoderTest extends LinearOpMode {
    private DcMotor wheelFL;
    private DcMotor wheelFR;
    private DcMotor wheelBL;
    private DcMotor wheelBR;

    @Override
    public void runOpMode() throws InterruptedException {
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

        waitForStart();

        telemetry.addLine("Front Left Wheel");
        telemetry.update();
        wheelFL.setPower(1);
        while (getRuntime() < 10) {
            telemetry.addData("FL", wheelFL.getCurrentPosition());
            idle();
        }

        wheelFL.setPower(0);

        telemetry.addLine("Front Right Wheel");
        telemetry.update();
        wheelFR.setPower(1);
        while (getRuntime() < 10) {
            telemetry.addData("FR", wheelFR.getCurrentPosition());
            idle();
        }
        wheelFR.setPower(0);

        telemetry.addLine("Back Left Wheel");
        telemetry.update();
        wheelBL.setPower(1);
        while (getRuntime() < 10) {
            telemetry.addData("BL", wheelBL.getCurrentPosition());
            idle();
        }
        wheelBL.setPower(0);

        telemetry.addLine("Back Right Wheel");
        telemetry.update();
        wheelBR.setPower(1);
        while (getRuntime() < 10) {
            telemetry.addData("BR", wheelBR.getCurrentPosition());
            idle();
        }
        wheelBR.setPower(0);
    }
}
