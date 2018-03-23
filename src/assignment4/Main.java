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
import java.lang.reflect.Method;

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
    		String input = "";
        boolean isQuit = false;
        while(!isQuit){
            System.out.print("critters>");
            input = kb.nextLine();
            List<String> command = parse(input);
            switch(command.get(0)){
                case "quit":  
                		if (command.size() > 1 ) {
                			System.out.println("error processing: " + input);
                			continue;
                		}
                		isQuit = true;
                    break;
                case "show":  
                		if(command.size() > 1) {
                			System.out.println("error processing: " + input);
                			continue;
                		}
                		Critter.displayWorld();
                    break;

                case "step":  
                		int count = 1;
                		if(command.size() > 2) {
                			System.out.println("error processing: " + input);
                			continue;
                		}
                		else if(command.size() == 2){
                			try {
                				count = Integer.parseInt(command.get(1));
            				
                			} catch(Exception e) {
                				System.out.println("error processing: " + input);
                				continue;
                			}
                		} else {
                			count = Integer.parseInt(command.get(1));
                		}
                		
                		for(int i = 0; i < count; i++){
                			Critter.worldTimeStep();
                		}
                		break;

                case "seed":
                		if(command.size() > 2 && command.size() < 2) {
                			System.out.println("error processing: " + input);
                			continue;
                		}
                		try {
                			Critter.setSeed(Integer.parseInt(command.get(1)));
                		} catch(Exception e){
                			System.out.println("error processing: " + input);
                			continue;
                		}
                		break;
                case "make":
                		count = 1;
                		if( (command.size() <= 1) || (command.size() > 3)) {
                			System.out.println("error processing: " + input);
                			continue;
                		}
                		else if(command.size() == 3) {
                			try {
                				count = Integer.parseInt(command.get(2));
            				
                			} catch(Exception e) {
                				System.out.println("error processing: " + input);
                				continue;
                			}
                		}
                		try {
                			for(int i=0; i<count; i++) {
                				Critter.makeCritter(command.get(1));
                			}
                		} catch(InvalidCritterException e){
                			System.out.println("error processing: " + e);
                			continue;
                		}
                		break;
                case "stats":
                    if(command.size() < 2){
                        break;
                    }
                    List<Critter> instances = Critter.getInstances(command.get(1));
                    try {
                        Class<?> c = Class.forName(myPackage + "." + command.get(1));
                        java.lang.reflect.Method runStats = c.getMethod("runStats", List.class);
    						runStats.invoke(c, instances);
                    }
                    catch (Exception e) {
                        throw new InvalidCritterException(command.get(1));
                
                    }
                    break;

                default:  break;
            }
        }
        
        /* Write your code above */
        System.out.flush();

    }
    /**
     * Parses the input into a list for processing
     * @param input String input from console
     * @return list of words in input
     */
    static List<String> parse(String input){
        List<String> output = new ArrayList<>();
        output = Arrays.asList(input.split(" "));
        return output;
    }
}
