package org.firstinspires.ftc.teamcode.common.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.drive.TankDrive;

//@Autonomous
public class DistanceTest extends LinearOpMode {
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
        t.beginTranslation(Distance.fromInches(24), 1.0);

        while (t.isBusy() && !isStopRequested()) {
            telemetry.clear();
            telemetry.update();
        }

        Thread.sleep(5000);
        //t.beginRotation(Angle.fromEncoderTicks(5000), 1.0);

    }
}
