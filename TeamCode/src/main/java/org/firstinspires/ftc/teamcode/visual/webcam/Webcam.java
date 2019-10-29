package org.firstinspires.ftc.teamcode.visual.webcam;

import org.firstinspires.ftc.teamcode.common.Assembly;

public abstract class Webcam extends Assembly {
    public enum SkystonePosition {
        LEFT,
        CENTER,
        RIGHT
    }


    // return the requested horizontal power
    public abstract double getHorizontalPowerToSkyStone();
    public abstract double getHorizontalPowerToNormalStone();


    // Necessary? I'm not sure because we could just use getHorizontalPowerToSkyStone()...
    public abstract SkystonePosition getPositionOfSkyStone();
}
