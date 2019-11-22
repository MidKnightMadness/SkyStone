package org.firstinspires.ftc.teamcode.drive.old;

import com.qualcomm.hardware.motors.RevRobotics20HdHexMotor;
import com.qualcomm.hardware.motors.RevRobotics40HdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
//@TeleOp
public class TestEncoder extends OpMode {
    private DcMotor motor;
    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("testmotor");
        motor.resetDeviceConfigurationForOpMode();
        motor.setMotorType(MotorConfigurationType.getMotorType(RevRobotics40HdHexMotor.class));
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        telemetry.addData("encoder", motor.getCurrentPosition());
    }
}
