package org.firstinspires.ftc.teamcode.navigation;

import org.firstinspires.ftc.teamcode.common.Position;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.visual.Visual;

public class Navigation {
    public Navigation(Visual visual, Drive drive) {
        Position visualPosition = visual.getPosition();
        Position drivePosition = drive.getPosition();

        if (visualPosition != null) {
            if (Math.abs(visualPosition.getX().toMillimeters() - drivePosition.getX().toMillimeters()) > 4 ||
                    Math.abs(visualPosition.getY().toMillimeters() - drivePosition.getY().toMillimeters()) > 4)
                drive.resetPosition(visualPosition);
        }


    }
}
