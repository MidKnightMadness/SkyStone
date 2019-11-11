package org.firstinspires.ftc.teamcode.visual;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Position;

public abstract class Visual extends Assembly {

    enum SkystoneSetup {
        Left,
        Center,
        Right,
    }

    public abstract Position getPosition();

    public abstract SkystoneSetup findSkystone();

    public abstract void stop();
}
