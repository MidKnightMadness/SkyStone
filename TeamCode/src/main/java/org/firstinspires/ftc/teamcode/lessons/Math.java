package org.firstinspires.ftc.teamcode.lessons;

public class Math {

    public static void main(String[] args) {

        /*

        Here in Java mathland, we have 5 different mathematical operators:
        +  addition
        -  subtraction
        *  multiplication
        /  division
        %  modulus

        Modulus is the one you may not have heard of. It gives the remainder after dividing two numbers
        Ex: 7 / 3 is 2 remainder 1.
        Therefore 7 % 3 = 1

        (Its useful for telling if something is even or odd cause x % 2 is always 0 for evens and 1 for odds)
         */

        int apples = 20;
        int oranges = 3;

        int applesAndOranges = apples + oranges;

        System.out.println(" we have our " + apples + " apples and our " + oranges + " oranges.");
        System.out.println("That's like " + applesAndOranges + " fruits!");


        int halfOfTheFruit =  applesAndOranges / 2;

        System.out.println("Oh no! We spilled half of it! We only have " + halfOfTheFruit + " left!");
        System.out.println();
        System.out.println("Now we are at a party for three! How much fruit does everyone get?");

        System.out.println("Let's each take " + (halfOfTheFruit / 3) + " fruits.");
        System.out.println("We can give the leftover " + (halfOfTheFruit % 3) + " fruits to Bill Gates.");

        System.out.println();

        System.out.println("No, no, no! We should split the " + (halfOfTheFruit) + " fruits exactly!");

        //This is called casting. We are turning our int into a double
        double fruits = (double)halfOfTheFruit;

        System.out.println("Fine! Now we each get " + (fruits / 3) + " fruits!");


        System.out.println();


        // Note: integers can not become decimals unless they are told to by casting them. This means that if you divide them, they will truncate:  3 / 4   = 0
        // If one number is a decimal and one is an integer, the integer will be automatically cast to a double, and double arithmetic will happen. 3 / 4.0 = 0.75


        // If you haven't noticed yet, we can add Strings together. This is called concatenating Strings

        String myString = "Hi " + "there!";
        System.out.println(myString);


        System.out.println();

        //     adding this . casts the 4 to a double        Scientific notation
        //                           \/                           \/
        double bunchesOfMath = (1 / 4.) + 2.1 - ((593102109 * 3.123E5) % 23);

        System.out.println("Bunches of Math: " + bunchesOfMath);
        System.out.println("Bunches of Math as an integer: " + (int) bunchesOfMath);

        // Another method instead of string concatenation:
        System.out.printf("Bunches of Math: %f", bunchesOfMath);
        System.out.printf("Bunches of Math as an integer: %d", (int) bunchesOfMath);
    }
}


/*
# Notes:

Refactor
   - right-click on the class in the tree view and use refactor to change the name of the class
   - changes the name of the class, the file, and any references to the class in other files
   OR
   - right-click on a variable to change it and any other references to it

Style Guide
   - Classes - each word starts with a capital letter, ExampleClass
   - properties, variables, and methods - camelCase - each word starts with a capital letter except the first, exampleVariable
   - static properties - all caps with underscores for spaces, EXAMPLE_CONSTANT

 */