package org.firstinspires.ftc.teamcode.led;

/**** Abstract LEDMode for controlling the leds ****/
public abstract class LEDMode {
    protected LEDSection section;
    void setSection(LEDSection section) {
        this.section = section;
    }

    protected LEDColor color(int i) { // prevent null unset colors
        return section.colors != null && i < section.colors.length && section.colors[i] != null ? section.colors[i] : LED.Colors.OFF;
    }

    protected int colorsLength() {
        return section.colors.length;
    }

    protected int getParam() {
        return section.param;
    }

    public abstract void update();
}
