package org.firstinspires.ftc.teamcode.led;

public class LEDState {
    public static void update(boolean slow, boolean grabbed, double time) {
        LED.ALL.set(
                grabbed ? LED.Colors.PINK : LED.Colors.BLUE,
                time > 110 ? LED.Colors.RED :
                        time > 90 ? LED.Colors.ORANGE :
                                time > 80 ? LED.Colors.YELLOW : LED.Colors.GREEN
        );
        if(LED.LEFT.getModeEnum() == LED.Modes.STATIC && slow)
            LED.ALL.set(LED.Modes.FLASHING);
        else if(LED.LEFT.getModeEnum() == LED.Modes.FLASHING && !slow)
            LED.ALL.set(LED.Modes.STATIC);
    }
}
