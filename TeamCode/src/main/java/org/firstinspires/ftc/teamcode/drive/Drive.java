package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.common.AssemblyManager.Implementation;
import org.firstinspires.ftc.teamcode.common.Distance;
import org.firstinspires.ftc.teamcode.common.Angle;



@Implementation(MecanumDrive.class)

public abstract class Drive extends OpMode {


    public abstract void setRunToPosition();

    public abstract void beginTranslation(Distance distance, double speed);

    public abstract void beginTranslationSide(Distance distance, int direction, double speed);

    public abstract void beginRotation(Angle angle, int direction, double speed);

    public abstract boolean isBusy();
}