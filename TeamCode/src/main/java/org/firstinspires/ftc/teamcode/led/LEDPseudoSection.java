package org.firstinspires.ftc.teamcode.led;

public class LEDPseudoSection {
    private LEDSection[] sections;
    public LEDPseudoSection(LEDSection...sections) {
        this.sections = sections;
    }
    public void set(LED.Modes mode, LEDColor...colors) {
        set(colors);
        set(mode);
    }
    public void set(LED.Modes mode) {
        for (LEDSection section : sections) {
            section.set(mode);
        }
    }
    public void set(LEDColor...colors) {
        for (LEDSection section : sections) {
            section.set(colors);
        }
    }
}