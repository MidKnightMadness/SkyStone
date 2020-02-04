package org.firstinspires.ftc.teamcode.led;

import org.firstinspires.ftc.teamcode.led.old.Log;

public class LEDModes {
    // A simple mode to set the entire section to a single color.
    static class Static extends LEDMode {
        public void update() {
            for (int i = section.getBegin(); i < section.getEnd(); i++) {
                LED.leds[i] = color(0);
            }
        }
    }

    static class Running extends LEDMode {
        private int offset = 0;

        public void update() {
            for (int j = section.getBegin(); j < section.getEnd(); j++) {
                LED.leds[j] = color((j + offset) % colorsLength());
            }
            offset = (offset + 1) % colorsLength();
            Log.out.println("Updated!" + offset);
        }
    }

    static class Bouncing extends LEDMode {
        // Only use on LOWER
        private final int MIN = LED.RIGHT.getBegin();
        private final int MAX = LED.LEFT.getEnd() - 1;
        private final int ACCENT = 0;
        private final int BACKGROUND = 1;

        private int position = MIN;
        private int direction = 1;

        public void update() {
            position = position + direction;
            direction = position > MAX || position < MIN ? -direction : direction;

            for (int i = section.getBegin(); i < section.getEnd(); i++) {
                LED.leds[i] = i == position ? color(ACCENT) : color(BACKGROUND);
            }
        }
    }

    static class Progress extends LEDMode {
        @Override
        public void update() {
            getParam();
        }
    }
}
