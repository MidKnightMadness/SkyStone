package org.firstinspires.ftc.teamcode.visual;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Position;
import java.io.PrintWriter;


public abstract class Visual extends Assembly {

    public enum SkystoneSetup {
        Left,
        Center,
        Right,
    }

    public abstract Position getPosition();

    public abstract SkystoneSetup findSkystone();

    public abstract double getSkystoneOffset();

    public abstract boolean[] isBlack(PrintWriter logging);

    public abstract void stop();
}
