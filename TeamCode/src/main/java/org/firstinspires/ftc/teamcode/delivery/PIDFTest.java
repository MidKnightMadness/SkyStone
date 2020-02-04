package org.firstinspires.ftc.teamcode.delivery;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.config.HardwareConfig;

@Disabled
@TeleOp
public class PIDFTest extends OpMode {
    private DcMotorEx vertical;
    private DcMotorEx horizontal;

    @Override
    public void init() {
        vertical = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.ELEVATOR);
        horizontal = (DcMotorEx) hardwareMap.dcMotor.get(HardwareConfig.HORIZONTAL);

        telemetry.addData("vertical encoder", vertical.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
        telemetry.addData("vertical position", vertical.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
        telemetry.addData("horizontal encoder", vertical.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
        telemetry.addData("horizontal position", vertical.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
    }

    @Override
    public void loop() {

    }
}
