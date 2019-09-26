package org.firstinspires.ftc.teamcode.common.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.drive.TankDrive;

//@TeleOp
public class RotationTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final TankDrive t = AssemblyManager.newInstance(TankDrive.class, hardwareMap, telemetry);
        telemetry.addData("FL: ", new Func<Integer>() {
            @Override
            public Integer value() {
                return t.frontLeft.getCurrentPosition();
            }
        });
        telemetry.addData("FR: ", new Func<Integer>() {
            @Override
            public Integer value() {
                return t.frontRight.getCurrentPosition();
            }
        });
        telemetry.addData("BL: ", new Func<Integer>() {
            @Override
            public Integer value() {
                return t.backLeft.getCurrentPosition();
            }
        });
        telemetry.addData("BR: ", new Func<Integer>() {
            @Override
            public Integer value() {
                return t.backRight.getCurrentPosition();
            }
        });

        waitForStart();
        t.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        t.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        t.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        t.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (!isStopRequested()) {
            t.frontLeft.setPower(gamepad1.right_stick_x);
            t.frontRight.setPower(gamepad1.right_stick_x);
            t.backLeft.setPower(gamepad1.right_stick_x);
            t.backRight.setPower(gamepad1.right_stick_x);



            telemetry.clear();
            telemetry.update();
        }

        //Thread.sleep(5000);
        //t.beginRotation(Angle.fromDegrees(90), 1.0);
        while (t.isBusy() && !isStopRequested());

    }
}
