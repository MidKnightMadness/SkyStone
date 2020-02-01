package org.firstinspires.ftc.teamcode.delivery;

import org.firstinspires.ftc.teamcode.common.Assembly;

public abstract class Delivery extends Assembly {
    public abstract void init();
    public abstract void setHeight(double blocks);
    public abstract void setDepth(double inches);
    public abstract void setDepthRaw(int encoderTicks);
    public abstract void holdDepth();
    public abstract boolean isComplete();
    public abstract void holdHeight();
    public abstract void setDepthPower(double speed);
    public abstract void setHeightPower(double speed);
    public abstract void setOverride(boolean override);


}
