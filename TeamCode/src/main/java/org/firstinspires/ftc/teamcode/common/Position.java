package org.firstinspires.ftc.teamcode.common;

public class Position {
    private Distance x;
    private Distance y;
    private Angle theta;
    public Position(Distance x, Distance y, Angle theta){
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public Distance getX() {
        return x;
    }

    public Distance getY() {
        return y;
    }

    public Angle getTheta() {
        return theta;
    }

}


