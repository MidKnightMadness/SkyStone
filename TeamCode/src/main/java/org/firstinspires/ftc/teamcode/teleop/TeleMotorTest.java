package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TeleMotorTest extends TeleMotor {

    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("fl");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2 = hardwareMap.dcMotor.get("bl");
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3 = hardwareMap.dcMotor.get("br");
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4 = hardwareMap.dcMotor.get("fr");
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {

    }
}
