package org.firstinspires.ftc.teamcode.grabber;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.common.Assembly;


public abstract class Grabber extends Assembly {

    protected abstract void grab();
    protected abstract void release();
    protected abstract boolean isFinished();

}
