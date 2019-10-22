package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ScissorTest extends TeleMotor {

    DcMotor motor1;

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {

    }
}
