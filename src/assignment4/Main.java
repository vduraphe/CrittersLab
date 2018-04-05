package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Vaidehi Duraphe
 * vd5374
 * Anika Agarwal
 * aa59662
 * Slip days used: <0>
 * Spring 18
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.*;
import java.lang.Integer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import static java.lang.Float.NaN;



/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	
    //static GridPane gp = Critter.displayWorld();
	Stage mainStage;
	private static String myPackage;
	 static {
	        myPackage = Critter.class.getPackage().toString().split(" ")[1];
	    }

    public static void main(String[] args) {
    		launch(args);
    }
    
    /**
     * Parses the input into a list for processing
     * @param input String input from console
     * @return list of words in input
     */

	@Override
	public void start(Stage args) throws Exception {
		try {
			mainStage = args;
			GridPane gp = new GridPane();
			Scene scene = new Scene(gp, 600,600);
			mainStage.setScene(scene);
			mainStage.setTitle("Critters");
			mainStage.show();
			gp.setGridLinesVisible(true);
			Critter.displayWorld(gp);
			Stage stage = new Stage();
	        stage.setTitle("Controller");
	        stage.show();
	        
	        Stage stage2 = new Stage();
	        FlowPane consolePane = new FlowPane();
	        Scene consoleScene = new Scene(consolePane, 500, 200);
	        stage2.setScene(consoleScene);
	        stage2.show();

	        GridPane control = new GridPane();
	        Scene functionScene = new Scene(control, 250, 250);
	        stage.setScene(functionScene);
	        control.setHgap(10);
	        control.setVgap(10);
	        control.setPadding(new Insets(10, 10, 10, 10));
	        
	        //create buttons
	        Button showButton = new Button("Show");
	        Button makeButton = new Button("Make");
	        Button stepButton = new Button("Step");
	        Button statsButton = new Button("Stats");
	        Button seedButton = new Button("Seed");
	        Button quitButton = new Button("Quit");
	       
	        //add buttons
	        control.add(showButton, 0, 0);
			control.add(makeButton, 0, 1);
	        control.add(stepButton,0, 2);
	        control.add(statsButton,0, 3);
	        control.add(seedButton,0, 4);
	        control.add(quitButton,0, 5);
	        
	        TextField makeTextField = new TextField();
	        HBox makeBoxClass = new HBox(makeTextField);
	        makeBoxClass.setMaxWidth(100);
	        TextField makeNumTextField = new TextField();
	        HBox makeBoxNum = new HBox(makeNumTextField);
	        makeBoxNum.setMaxWidth(40);
	        TextField stepsNumTextField = new TextField();
	        HBox stepsBox = new HBox(stepsNumTextField);
	        stepsBox.setMaxWidth(40);
	        TextField statsTextField = new TextField();
	        HBox statsClassBox = new HBox(statsTextField);
	        statsClassBox.setMaxWidth(100);
	        TextField seedNumTextField = new TextField();
	        HBox seedBox = new HBox(seedNumTextField);
	        seedBox.setMaxWidth(40);
	        
	        control.add(makeBoxClass,1, 1);
	        control.add(makeBoxNum,2, 1);
	        control.add(stepsBox,1, 2);
	        control.add(statsClassBox,1, 3);
	        control.add(seedBox,1, 4);

	        Label lbl = new Label();
	        lbl.setText("Console Text");
	        consolePane.getChildren().add(lbl);
	        
	        //button actions
	        
	        showButton.setOnAction(e-> {
	            Critter.displayWorld(gp);
	        });
	        
	        makeButton.setOnAction(e-> {
	        		try {
	        			System.out.println(makeNumTextField.getText());
	        			for(int i=0; i < Integer.parseInt(makeNumTextField.getText()); i++)
	        			Critter.makeCritter(makeTextField.getText());
	        		} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InvalidCritterException f){
	        			System.out.println("error processing: " + f);
	        		}
	        		Critter.displayWorld(gp);

	        });
	        
	        stepButton.setOnAction(e-> {
	        		int count = 0;
	        		try {
	        			System.out.println(stepsNumTextField.getText());
	        			count = Integer.parseInt(stepsNumTextField.getText());
	        			for (int i = 0; i < count; i++) {
	        				Critter.worldTimeStep();
	        			}
	        		} catch (IllegalArgumentException f){
	        			System.out.println("error processing: " + f);
	        		}
	        		Critter.displayWorld(gp);
	        });
	        
	        statsButton.setOnAction(e-> {
	        		try {
	        			List<Critter> instances = Critter.getInstances(statsTextField.getText());
	        			Class<?> c = Class.forName(myPackage + "." + statsTextField.getText());
                        java.lang.reflect.Method runStats = c.getMethod("runStats", List.class);
    					String result = (String) runStats.invoke(c, instances);
    					lbl.setText(result);
	        		} catch (IllegalArgumentException | InvalidCritterException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException | SecurityException f){
	        			System.out.println("error processing: " + f);
	        		}
	        });
	        
	        seedButton.setOnAction(e-> {
	        		try {
	        			Critter.setSeed(Integer.parseInt(seedNumTextField.getText()));
	        		} catch(Exception f){
	        			System.out.println("error processing: " + f);
	        		}
	        });
	        
	        quitButton.setOnAction(e-> {
	        	
	        });
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
			

		
	
       

	}
}
