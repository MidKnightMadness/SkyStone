package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.AssemblyManager;
import org.firstinspires.ftc.teamcode.common.AssemblyManager.Implementation;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Angle;



@AssemblyManager.Implementation(ScissorLift.class)

public abstract class Lift extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
    public abstract void raiseLift(int enc);
    public abstract void lowerLift(int enc);

    public abstract boolean isBusy();



}