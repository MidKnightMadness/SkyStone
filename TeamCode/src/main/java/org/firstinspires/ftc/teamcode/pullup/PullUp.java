package org.firstinspires.ftc.teamcode.pullup;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.AssemblyManager;

@AssemblyManager.Implementation(AngularPullUp.class)
public abstract class PullUp extends OpMode {
    public static boolean DEMO = false;
    public abstract void open() throws InterruptedException;
    public abstract void close() throws InterruptedException;
    public abstract void drop() throws InterruptedException;
    public abstract void reset();
    public abstract void reachCrater();
}
