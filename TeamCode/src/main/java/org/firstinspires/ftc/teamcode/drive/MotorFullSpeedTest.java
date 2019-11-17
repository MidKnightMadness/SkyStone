package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.config.HardwareConfig;


@TeleOp
public class MotorFullSpeedTest extends OpMode {

    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BL);
        motor2 = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_BR);
        motor3 = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FL);
        motor4 = hardwareMap.dcMotor.get(HardwareConfig.MECANUM_FR);

        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        motor1.setPower(gamepad1.left_stick_x);
        motor2.setPower(gamepad1.left_stick_x);
        motor3.setPower(-gamepad1.left_stick_x);
        motor4.setPower(-gamepad1.left_stick_x);

    }
}
