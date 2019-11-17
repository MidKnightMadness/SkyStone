package org.firstinspires.ftc.teamcode.drive;

import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Position;

public abstract class Drive extends Assembly {
    public abstract void moveTo(Position target);

    public abstract Position getPosition();

    public abstract void resetPosition(Position realPosition);
}
