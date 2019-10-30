package org.firstinspires.ftc.teamcode.foundationMover;

import com.qualcomm.robotcore.hardware.TouchSensor;

public class FoundationButtons {
    private TouchSensor left;
    private TouchSensor right;

    public enum State {
        BOTH(3),
        LEFT(2),
        RIGHT(1),
        NEITHER(0);

        private int value ;

        private State(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

    }

    public FoundationButtons(TouchSensor left, TouchSensor right) {

        this.left = left;
        this.right = right;
        /**If either of the are pressed, which one is pressed?**/

    }

    public State getState() {

        if (left.isPressed() == true && right.isPressed() == true) {
            return State.BOTH;
        }

        else if (right.isPressed() == true) {
            return State.RIGHT;
        }

        else if (left.isPressed() == true) {
            return State.LEFT;
        }

        else{
            return State.NEITHER;
        }
    }
}
