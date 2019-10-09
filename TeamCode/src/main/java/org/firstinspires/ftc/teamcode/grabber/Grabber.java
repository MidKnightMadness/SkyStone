package org.firstinspires.ftc.teamcode.grabber;

public abstract class Grabber {
    protected abstract void init();
    protected abstract void grab();
    protected abstract void release();
    protected abstract boolean isFinished();
}
