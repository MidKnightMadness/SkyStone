package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.util.RobotLog;

public class SingletonFinalize {
    private static SingletonFinalize instance = new SingletonFinalize();
    public static SingletonFinalize getInstance() {
        return instance;
    }

    public SingletonFinalize() {
        RobotLog.a("I'm ALIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIVE!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        RobotLog.a("I'm DEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD!");
    }
}
