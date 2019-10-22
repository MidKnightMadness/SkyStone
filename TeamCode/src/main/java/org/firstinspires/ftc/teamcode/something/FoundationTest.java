package org.firstinspires.ftc.teamcode.something;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous

public class FoundationTest extends LinearOpMode {
    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;
    public double POWER_CONSTANT = 1;
    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3 = hardwareMap.dcMotor.get("motor3");
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4 = hardwareMap.dcMotor.get("motor4");
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        //robot moves about 10 inches in 1 second/1000 milliseconds forward and backward
        //robot moves about 15 inches in 1 second/1000 milliseconds left and right
        motor1.setPower(-POWER_CONSTANT);
        motor2.setPower(POWER_CONSTANT);
        motor3.setPower(-POWER_CONSTANT);
        motor4.setPower(POWER_CONSTANT);
        sleep(2000);
        //forward
      //  motor1.setPower(POWER_CONSTANT);
      //  motor2.setPower(POWER_CONSTANT);
      //  motor3.setPower(-POWER_CONSTANT);
      //  motor4.setPower(-POWER_CONSTANT);
      //  sleep(0);
        //left
      //  motor1.setPower(POWER_CONSTANT);
      //  motor2.setPower(-POWER_CONSTANT);
      //  motor3.setPower(POWER_CONSTANT);
      //  motor4.setPower(-POWER_CONSTANT);
      //  sleep(0);
        //backwards
      //  motor1.setPower(POWER_CONSTANT);
      //  motor2.setPower(POWER_CONSTANT);
      //  motor3.setPower(-POWER_CONSTANT);
      //  motor4.setPower(-POWER_CONSTANT);
      //  sleep(0);
        //left
      //  motor1.setPower(-POWER_CONSTANT);
      //  motor2.setPower(-POWER_CONSTANT);
      //  motor3.setPower(POWER_CONSTANT);
      //  motor4.setPower(POWER_CONSTANT);
        //right
    }
}
