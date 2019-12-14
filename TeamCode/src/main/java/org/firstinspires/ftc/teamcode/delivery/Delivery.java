package org.firstinspires.ftc.teamcode.delivery;

import org.firstinspires.ftc.teamcode.common.Assembly;

public abstract class Delivery extends Assembly {
    public abstract void init();
    public abstract void setHeight(double blocks);
    public abstract void setDepth(double inches);
    public abstract boolean isComplete();
}
