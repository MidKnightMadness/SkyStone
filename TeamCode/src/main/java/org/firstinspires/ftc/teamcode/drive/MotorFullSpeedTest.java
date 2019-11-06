package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class MotorFullSpeedTest extends OpMode {

    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("Motor1");
        motor2 = hardwareMap.dcMotor.get("Motor2");
        motor3 = hardwareMap.dcMotor.get("Motor3");
        motor4 = hardwareMap.dcMotor.get("Motor4");

    }

    @Override
    public void loop() {
        motor1.setPower(.5);
        motor2.setPower(.5);
        motor3.setPower(-.5);
        motor4.setPower(-.5);

    }
}
