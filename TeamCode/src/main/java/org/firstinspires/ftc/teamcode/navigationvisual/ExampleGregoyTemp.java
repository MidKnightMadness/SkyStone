package org.firstinspires.ftc.teamcode.navigationvisual;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;

public class ExampleGregoyTemp {
    public void init() {
        VuforiaSkyStone vuforia = new VuforiaSkyStone();
        vuforia.initialize();
        vuforia.activate();

        vuforia.trackPose("Red Perimeter 1").x

    }

}
