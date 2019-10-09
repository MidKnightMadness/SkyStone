package org.firstinspires.ftc.teamcode.grabber;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

public abstract class Grabber {
    protected abstract void init();
    protected abstract void grab();
    protected abstract void release();
    protected abstract boolean isFinished();
    public HardwareMap hardwareMap;
}
