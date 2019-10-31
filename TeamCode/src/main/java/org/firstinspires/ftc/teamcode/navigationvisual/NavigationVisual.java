package org.firstinspires.ftc.teamcode.navigationvisual;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class NavigationVisual extends OpMode {
    public static boolean DEBUG = true;
    public static boolean SAVE = false;
    public abstract void init();



    public abstract void stop();

}
