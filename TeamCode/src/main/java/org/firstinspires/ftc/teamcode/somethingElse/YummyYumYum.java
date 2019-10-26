package org.firstinspires.ftc.teamcode.somethingElse;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class YummyYumYum extends LinearOpMode {
    private DcMotor motor1;
    private DcMotor motor2;

    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.dcMotor.get("Tasty Tasty Ice Cream");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2 = hardwareMap.dcMotor.get("Tasty Tasty Candy");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        telemetry.addLine("ICE CREAM READY TO EAT");
//        telemetry.update();
//        waitForStart();
//        motor.setPower(1);
//        telemetry.addLine("ICE CREAM IS NOW BEING EATEN");
//        telemetry.update();
//        sleep(5000);
//        telemetry.addLine("ICE CREAM IS NOW BEING EATEN SLOWER");
//        telemetry.update();
//        motor.setPower(0.5);
//        sleep(5000);
        telemetry.addLine("Both motors are positive");
        telemetry.update();
        waitForStart();
        //Motor1
        motor1.setPower(-1);
        motor2.setPower(1);
        sleep(2000);

    }

}

