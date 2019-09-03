/* Created by Gregory Ling on 2019-09-01 */

package org.firstinspires.ftc.teamcode.challenges;

public class Methods {
    public static void main(String[] args) {
        // Initialize two integer variable with two numbers
        int num0 = 0;
        int num1 = 21;

        // Create a method that will take three integer and return the double average of the three


        // Use the average method to average the two variables and one constant value
        double result = doubleAvg(num0, num1, 200);

        // And print out the variables, your value, and the average using printf
        System.out.printf("values: %d, %d, %d, result: %d",num0,num1,200,result);

        // Try it again using println
        System.out.println("values: "+num0+", "+num1+", "+200+", result: "+result);
    }

    static double doubleAvg(int num0, int num1, int num2)
    {
        return (num0+num1+num2) /3. *2;
    }
}
