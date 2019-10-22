package org.firstinspires.ftc.teamcode.common;

import android.preference.PreferenceManager;
import android.util.Log;


import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

/**
 * Config: Configuration file to hold Motor Names.
 *
 * Create a new static final class for every assembly.
 *
 * Created by Gregory on 9/10/18.
 */

public final class Config {
    public static final class Drive {     // Drive Assembly Configuration
        public static final String FRONT_LEFT = "fl";   // Hub 2: 0
        public static final String FRONT_RIGHT = "fr"; // Hub 2: 1
        public static final String BACK_RIGHT = "br";   // Hub 2: 2
        public static final String BACK_LEFT = "bl";     // Hub 2: 3
        // "imu"   Hub 2: I2C Bus 0: 0
    }

    public static final class Measurements {    // WE HAVE TO MEASURE THESE
        public static final double ROBOT_DEGREES_PER_TICK = 0.060857d;
        public static final double ENCODER_TICKS_PER_INCH = 5000d/68d;
        public static final int ENCODER_TICKS_PER_SHAFT_DEGREE = 1100;
    }


}
