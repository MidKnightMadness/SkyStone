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

    public Position add(Distance x, Distance y, Angle theta) {
        this.x.add(x);
        this.y.add(y);
        this.theta.add(theta);
        return this;
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

    public void setTheta(Angle theta) {
        this.theta = theta;
    }
    
    @Override
    public String toString() {
        return "x: "+ x.toInches() + ", y: " + y.toInches() + ", theta: " + theta.toDegrees();
    }

}


