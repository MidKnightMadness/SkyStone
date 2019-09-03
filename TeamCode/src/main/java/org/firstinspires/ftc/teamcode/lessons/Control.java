package org.firstinspires.ftc.teamcode.lessons;



public class Control {

    public static void main(String[] args) {

        boolean imSmart = 1 == 2;

        //if statements are pretty self-explanatory
        //if the statement in the parentheses is true, then the code inside the brackets will run
        if (imSmart) {
            System.out.println("I'm super smart!");
            int a = 1;
        }



        /*
        Here are all the comparison operators:

        >     greater than
        <     less than
        >=    greater than or equal to
        <=    less than or equal to
        ==    equal to

        !

        !=

        */

        int num1 = 4;
        int num2 = 5;

        if(!(num1 < num2)) {
            System.out.println("We got some small numbers!");
        } else if (num1 + num2 <= 50) {
            System.out.println("These are some big numbers!");
        } else {
            System.out.println("How'd you find numbers this big?");
        }

        switch(num1) {
            case 0:
            case 1: System.out.println("IT's one!"); break;
            case 2:
                System.out.println("IT's two!");
                int a = 1;
                break;
            default: break;

        }

        if(num1 == num2) {
            System.out.println("Uh oh, the numbers are the same!!!!!");
        }
    }
}
