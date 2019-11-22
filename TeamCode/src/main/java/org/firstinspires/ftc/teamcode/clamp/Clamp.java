package org.firstinspires.ftc.teamcode.clamp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.AssemblyManager;


@AssemblyManager.Implementation(LeadClamp.class)
public abstract class Clamp extends OpMode {

    public abstract void closeToHalf();
    public abstract void closeToClosed();
    public abstract void openToHalf();
    public abstract void openToFull();

    public abstract boolean isBusy();

}
