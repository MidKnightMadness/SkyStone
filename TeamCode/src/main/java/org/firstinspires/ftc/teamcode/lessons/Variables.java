package org.firstinspires.ftc.teamcode.lessons;


public class Variables {

    public static void main(String[] args) {

        //This is a comment! It gets ignored in the code!


        /*

        This is a fancy comment!

         */





        /*
        Variables!!!!!!!
        All variable declarations take on this form:

        Type myName;

        First comes the type of the variable you want to create, then whatever name you want to pick for your variable




        If you want to assign a value to your variable, you do it with this form:

        myName = myValue;




        Or, if you're feeling super cool, you can create and assign your variable in one line:

        type myName = myValue;


         */






        // Lets take a look at the 4 common variable types in Java: int, double, String, and Boolean

//////////int - any whole number integer, positive or negative
        int someInteger;
        someInteger = 3;
        System.out.println(someInteger);

        int myInteger2 = -1004;
        System.out.println(myInteger2);


        System.out.println();

//////////double - any number, could be a decimal, or whole number, positive or negative
        double someDouble;
        someDouble = 5;
        System.out.println(someDouble);

        double myDouble2 = 24.389;
        System.out.println(myDouble2);


        System.out.println();

//////////String - any grouping of characters on the keyboard. It must be placed within "double quotes"
        String someString;
        someString = "Hi there!!";
        System.out.println(someString);

        String myString2 = "Look at me count: 1, 2, 3!!!";
        System.out.println(myString2);


        System.out.println();


//////////boolean - this can only be one of two options: true or false
        boolean someBoolean;
        someBoolean = true;
        System.out.println(someBoolean);

        boolean myBoolean2 = false;
        System.out.println(myBoolean2);





        System.out.println();





//////////We can change any variable after it has been assigned
        System.out.println("someDouble = " + someDouble);
        someDouble = 23;
        System.out.println("After reassigning it, someDouble = " + someDouble);

        System.out.println();

        System.out.println("someString = " + someString);
        someString = (myBoolean2 ? "Bye": "Hi") + " there!";
        System.out.println("After reassigning it, someString = " + someString);




        System.out.println();
        System.out.println();






        //Lets print it all!
        System.out.println("Although it may be " + someBoolean + " that Java is cool, this robotics team is " + someInteger + " times better! " + someString);
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