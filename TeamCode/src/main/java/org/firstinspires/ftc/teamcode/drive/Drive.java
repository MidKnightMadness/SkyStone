package org.firstinspires.ftc.teamcode.drive;

import org.firstinspires.ftc.teamcode.common.Angle;
import org.firstinspires.ftc.teamcode.common.Assembly;
import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.navigation.Navigation;

public abstract class Drive extends Assembly {
    public abstract void setTarget(Position target, Navigation navigation);

    public abstract Position getPosition();

    public abstract void resetPosition(Position realPosition);

    public abstract void setDirection(Angle theta, double speed, double rotation);

    public abstract boolean isComplete();

    public abstract void resetHeading();

    public abstract void setThreshold(double tThreshold, double rThreshold);
}
