package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Assembly {
    public abstract void init();
    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public void update(){};

    public static void initialize(Telemetry telemetry, HardwareMap hardwareMap, Assembly... assemblies) {
        for (Assembly assembly : assemblies) {
            assembly.telemetry = telemetry;
            assembly.hardwareMap = hardwareMap;
            assembly.init();
        }
    }
}
