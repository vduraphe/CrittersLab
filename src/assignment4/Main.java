package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.*;
import java.lang.Integer;
import java.lang.reflect.InvocationTargetException;

import static java.lang.Float.NaN;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     * @throws InvalidCritterException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     */
    public static void main(String[] args) throws InvalidCritterException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        boolean isQuit = false;
        while(!isQuit){
            System.out.print("critters>");
            List<String> command = parse(kb);

            switch(command.get(0)){
                case "quit":  isQuit = true;
                    break;

                case "show":  Critter.displayWorld(); // todo Make sure this is called on right instance
                    break;

                case "step":  int count = 1;
                    if(command.size() > 1){ // prevent null pointer if 1 arg
                        if(Integer.parseInt(command.get(1)) != NaN){ // check if 2nd arg is a #
                            count = Integer.parseInt(command.get(1));
                        }
                    }

                    for(int i = 0; i < count; i++){
                        Critter.worldTimeStep();
                    }
                    break;

                case "seed":
                    if(command.size() < 2){ // Check for args
                        break;
                    }
                    Critter.setSeed(Integer.parseInt(command.get(1)));
                    break;

                case "make":
                    if(command.size() < 2){ // Check for args
                        break;
                    }
                    count = 1; // Count = 3rd arg, default 1
                    if(command.size() > 2){ // prevent null pointer if 1 arg
                        if(Integer.parseInt(command.get(2)) != NaN){ // check if 2nd arg is a #
                            count = Integer.parseInt(command.get(2));
                        }
                    }

                    for(int i = 0; i < count; i++){
                        Critter.makeCritter(command.get(1));
                    }
                    break;

                case "stats":
                    if(command.size() < 2){ // Check for args
                        break;
                    }
                    List<Critter> instances = Critter.getInstances(command.get(1));
                    // run stats for that class - static method
                    try {
                        Class c = Class.forName("assignment4."+ command.get(1));
                        Critter myNewCritter = (Critter) c.newInstance();
                        myNewCritter.runStats(instances);
                    }
                    catch (Exception e) {
                        throw new InvalidCritterException(command.get(1));
                    }
                    break;

                default:  break;
            }
            System.out.println();
        }
        
        /* Write your code above */
        System.out.flush();

    }
    static List<String> parse(Scanner kb){
        List<String> output = new ArrayList<>();
        String command = kb.nextLine();
        output = Arrays.asList(command.split(" "));
        return output;
    }
}
