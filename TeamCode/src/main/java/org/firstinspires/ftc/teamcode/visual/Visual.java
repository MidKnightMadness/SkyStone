package org.firstinspires.ftc.teamcode.visual;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Position;

public abstract class Visual extends Assembly {

    public abstract VectorF GetPosition();


    public abstract Position findSkystone();
}
