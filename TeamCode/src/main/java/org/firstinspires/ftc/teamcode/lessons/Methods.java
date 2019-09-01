package org.firstinspires.ftc.teamcode.lessons;

public class Methods {

    public static void main(String[] args) {
        myMethod();
        favoriteNumber(17);
        int lowNumber = 2;
        int highNumber = makeNumberHigh(lowNumber);
        System.out.print("I started with " + lowNumber + " and now I have " + highNumber + "!");
    }

    private static void myMethod() {
        System.out.println("I can print from a method!");
    }

    private static void favoriteNumber(int num) {
        System.out.println("My favorite number is: " + num);
    }

    private static int makeNumberHigh(int num) {
        return num * 78;
    }
}