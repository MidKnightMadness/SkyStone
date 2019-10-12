/* Created by Gregory Ling on 2019-09-01 */

package org.firstinspires.ftc.teamcode.challenges;

public class Methods {
    public static void main(String[] args) {
        int x =9;
        int y =8;

        threeaverage(1, 2, 3);


        // Initialize two integer variable with two numbers
        int num0 = 0;
        int num1 = 21;

        // Create a method that will take three integer and return the double average of the three


        // Use the average method to average the two variables and one constant value
        double result = doubleAvg(num0, num1, 200);

        System.out.print("IM ALIVE!!");

        // And print out the variables, your value, and the average using printf
        System.out.printf("values: %d, %d, %d, result: %d\n",num0,num1,200, (int) result);

        // Try it again using println
        System.out.println("values: "+num0+", "+num1+", "+200+", result: "+result);
    }

    static double doubleAvg(int num0, int num1, int num2)
    {
        return (num0+num1+num2) /3. *2;
    }
    private static void threeaverage(int num, int num1, int num2){System.out.println((num+num1+num2)/3*2);}

    private static void twoaverage(int num, int num1){System.out.println(num+num1/2);

    }


}
