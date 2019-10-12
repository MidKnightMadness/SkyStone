package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public final class AssemblyManager {
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Implementation {
        Class value();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TeleOpImplementation {
        Class value();
    }

    public static <T> T newInstance(Class<T> clazz, HardwareMap hardwareMap, Telemetry telemetry) { return newInstance(clazz, hardwareMap, telemetry, false); }
    @SuppressWarnings("EmptyCatchBlock")
    public static <T> T newInstance(Class<T> clazz, HardwareMap hardwareMap, Telemetry telemetry, boolean teleop) throws Error {
        try {
            Object o;
            if (teleop && clazz.isAnnotationPresent(TeleOpImplementation.class)) {
                o = clazz.getAnnotation(TeleOpImplementation.class).value().newInstance();
            } else if (clazz.isAnnotationPresent(Implementation.class)) {
                o = clazz.getAnnotation(Implementation.class).value().newInstance();
            } else {
                o = clazz.newInstance();
            }

            T t = clazz.cast(o);

            try {
                Field hm = t.getClass().getField("hardwareMap");
                hm.set(t, hardwareMap);
            } catch (Exception e){}

            try {
                Field tl = t.getClass().getField("telemetry");
                tl.set(t, telemetry);
            } catch (Exception e){ }

            try {
                t.getClass().getMethod("init").invoke(t);
            } catch (Exception e) {
                telemetry.addLine("ERROR: UNABLE TO INIT");
                telemetry.addLine(t.getClass().getSimpleName());
                telemetry.addLine(e.getMessage());
            }

            return t;
        } catch (Exception e) {
            telemetry.addLine("ERROR: Unable to find implementation of " + clazz.getSimpleName());
            return null;
        }
    }
}