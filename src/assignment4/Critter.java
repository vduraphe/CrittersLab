package assignment4;
/*
 * CRITTERS Critter2.java
 * EE422C Project 4 submission by
 * Vaidehi Duraphe
 * vd5374
 * Anika Agarwal
 * aa59662
 * Slip days used: <0>
 * Spring 18
 */


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.shape.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */

/**
 * Creates a critter, creates methods for it to move/reproduce/fight and handles all of its interactions
 * @author Vaidehi
 *
 */
public abstract class Critter {
	
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.CADETBLUE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 

	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static Critter[][] grid = new Critter[Params.world_width][Params.world_height];

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	protected final String look(int direction, boolean steps) {
		int num = 1;
		int x = x_coord;
		int y = y_coord;

		if(steps){
			num = 2;
		}

		switch(++direction){
			case 1:
				x += num;
				break;
			case 2:
				x += num;
				y -= num;
				break;
			case 3:
				y -= num;
				break;
			case 4:
				x -= num;
				y -= num;
				break;
			case 5:
				x -= num;
				break;
			case 6:
				x -= num;
				y += num;
				break;
			case 7:
				y += num;
				break;
			case 8:
				x += num;
				y += num;
				break;
			default:
				break;
		}

		if(x > Params.world_width - 1) {
			x = (x % (Params.world_width - 1)) - 1;
		}
		if(y > Params.world_height - 1) {
			y = (y_coord % (Params.world_height - 1)) - 1;
		}
		if(x < 0) {
			x += Params.world_width;
		}
		if(y < 0) {
			y += Params.world_height;
		}

		energy -= Params.look_energy_cost;

		return checkLook(x, y);
	}

	// helper bc we lazy f u
	private static String checkLook(int temp_x, int temp_y){
		for (Critter c : population) {
			if((c.x_coord == temp_x) && (c.y_coord == temp_y) ){
				return c.toString();
			}
		}
		return null;
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private static int size = 500/Params.world_width;

	/**
	 * Moves 1 tile
	 * @param direction : direction crit moves in
	 */
	protected final void walk(int direction) {
		move(direction,1);
		energy -= Params.walk_energy_cost;
	}
	/**
	 * Moves 2 tiles in a direction, direction given by parameter
	 * @param direction the direction crit moves in
	 */
	protected final void run(int direction) {
		move(direction,2);
		energy -= Params.run_energy_cost;
	}
	/**
	 * moves the critter num of spots based on param tiles in a direction based on param dir
	 * @param dir direction crit moves in
	 * @param tiles how many spaces it moves
	 */
	protected final void move(int dir, int tiles) {
		int width = Params.world_width;
		int height = Params.world_height;
		switch(dir) {
		case 0: 
			grid[x_coord][y_coord] = null;
			x_coord = (x_coord+tiles) % width;
			grid[x_coord][y_coord] = this;
			break;
		case 1:
			grid[x_coord][y_coord] = null;
			x_coord = (x_coord+tiles) % width;
			y_coord = (x_coord+tiles) % height;
			grid[x_coord][y_coord] = this;
			break;
		case 2:
			grid[x_coord][y_coord] = null;
			y_coord = (y_coord+tiles) % height;
			grid[x_coord][y_coord] = this;
			break;
		case 3:
			grid[x_coord][y_coord] = null;
			x_coord = (x_coord-tiles + width) % width;
			y_coord = (y_coord+tiles) % height;
			grid[x_coord][y_coord] = this;
			break;
		case 4:
			grid[x_coord][y_coord] = null;
			x_coord = (x_coord-tiles + width) % width;
			grid[x_coord][y_coord] = this;
			break;
		case 5:
			grid[x_coord][y_coord] = null;
			x_coord = (x_coord-tiles + width) % width;
			y_coord = (y_coord-tiles + height) % height;
			grid[x_coord][y_coord] = this;
			break;
		case 6:
			grid[x_coord][y_coord] = null;
			y_coord = (y_coord-tiles + height) % height;
			grid[x_coord][y_coord] = this;
			break;
		case 7:
			grid[x_coord][y_coord] = null;
			x_coord = (x_coord+tiles) % width;
			y_coord = (y_coord-tiles + height) % height;
			grid[x_coord][y_coord] = this;
			break;
		default:
			break;
		}
	}
	/**
	 * subtracts rest energy from critter
	 */
	protected final void rest() {
		energy -= Params.rest_energy_cost;
	}
	/**
	 * Creates a new critter if original critter energy is greater than reproduction energy needed. adds to babies list.
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy >= Params.min_reproduce_energy) {
			energy = (int) Math.ceil(0.5 * energy);
			offspring.energy = (int) Math.floor(0.5 * energy);
			offspring.x_coord = x_coord;
			offspring.y_coord = y_coord;
			offspring.energy += 2;
			offspring.walk(direction);
			babies.add(offspring);
			grid[offspring.x_coord][offspring.y_coord] = offspring;
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			Class c = Class.forName(myPackage + "." + critter_class_name);
			Critter myNewCritter = (Critter) c.newInstance();
			myNewCritter.energy = Params.start_energy;
			myNewCritter.x_coord = getRandomInt(Params.world_width);
			myNewCritter.y_coord = getRandomInt(Params.world_height);
			population.add(myNewCritter);
            grid[myNewCritter.x_coord][myNewCritter.y_coord] = myNewCritter;


		}
		catch (Exception e) {
			throw new InvalidCritterException(critter_class_name);
		}
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class critType = Class.forName(myPackage + "." + critter_class_name);
			for(int i = 0; i < population.size(); i++) {
				Critter c = population.get(i);
				if (critType.isInstance(c)) {
					result.add(c);
				}
			}
		} catch (Exception e) {
			throw new InvalidCritterException(critter_class_name);
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static String runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		String output = "";
		for (String s : critter_count.keySet()) {
			output += prefix + s + ":" + critter_count.get(s);
			prefix = ", ";
		}
		return output;
		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
	}

	/**
	 * each critter does its time step, dead crits eliminated, algae refreshed
	 */
	public static void worldTimeStep() {
		for (Critter c : population) {
			//do time step for every crit in population
			c.doTimeStep();
		}
		for (Critter crit1 : population) {
			for (Critter crit2 : population) {
				if (!(crit1.equals(crit2)) && (crit1.x_coord == crit2.x_coord) && (crit1.y_coord == crit2.y_coord)) {                      
					// handle encounter if crits in same spot and not the same crit
					handleEncounter(crit1, crit2);
				} 
			}
		}
		//avoid concurrent modification by using iterator
		Iterator<Critter> critIt = population.iterator();
		while (critIt.hasNext()) {
			Critter check = critIt.next();
			check.energy -= Params.rest_energy_cost;
			//if dead
			if (check.energy <= 0) {
				critIt.remove();
				grid[check.x_coord][check.y_coord] = null;
			}
		}
		for (int i = 0; i < Params.refresh_algae_count; i++) {
			Algae babyAlg = new Algae();
			babyAlg.setEnergy(Params.start_energy);
			babyAlg.setX_coord(getRandomInt(Params.world_width));
			babyAlg.setY_coord(getRandomInt(Params.world_height));
			population.add(babyAlg);
			grid[babyAlg.getX_coord()][babyAlg.getY_coord()] = babyAlg;
		}
		
	}
	/**
	 * prints out the world, including borders
	 */
	public static void displayWorld(GridPane gridPane) {
		gridPane.getChildren().clear();
		for (int r = 0; r < Params.world_width; r++) {
            for (int c = 0; c < Params.world_height; c++) {
                Shape s = new Rectangle(size, size);
                s.setFill(null);
                s.setStroke(Color.BLACK);
                gridPane.add(s, c, r);
            }
		}
		for (int r = 0; r < Params.world_width; r++) {
            for (int c = 0; c < Params.world_height; c++) {
                if (grid[r][c] != null) { 
                    Critter crit = grid[r][c];
                    Shape s = crit.getIcon();
                    gridPane.add(s, r, c); 
                }
            }
		}

	}

	/**
	 * Checks if critter1 and critter 2 are willing to fight. Then uses their dice rolls to engage them in a fight if necessary.
	 * Changed critters' energies based on results of fight.
	 * @param crit1 Critter object 1 
	 * @param crit2 Critter object 2
	 */
	private static void handleEncounter(Critter crit1, Critter crit2) {
		int crit1Roll = 0;
		int crit2Roll = 0;
		boolean crit1Fight = crit1.fight(crit2.toString());
		boolean crit2Fight = crit2.fight(crit1.toString());
		if ((crit1.x_coord == crit2.x_coord) && (crit1.y_coord == crit2.y_coord) && (crit1.energy != 0) && (crit2.energy!= 0)) {
			if (crit1Fight == true) {
				if (crit1.energy > 0) {
					crit1Roll = Critter.getRandomInt(crit1.energy);
				}
			} 
			if (crit2Fight == true) {
				if (crit2.energy > 0) {
					crit2Roll = Critter.getRandomInt(crit2.energy);
				}
			}
			if (crit1Roll < crit2Roll) {
				//crit2 wins
				crit2.energy += crit1.energy/2;
				crit1.energy = 0;
			} else if (crit2Roll < crit1Roll) {
				//crit1 wins
				crit1.energy += crit2.energy/2;
				crit2.energy = 0;

			} else {	
				//tie
				crit1.energy += crit2.energy/2;
				crit2.energy = 0;
			}
		}
	}
	private Shape getIcon() {
        Shape s = null;
        Color outline = viewOutlineColor();
        Color fill = viewFillColor();
        CritterShape critShape = viewShape();
        Polygon polygon;

        switch(critShape) {
        		case CIRCLE: s = new Circle(size/2);
        			s.setFill(fill);
        			break;
            case SQUARE: s = new Rectangle(size, size);
                s.setFill(fill);
                break;
            case STAR: polygon = new Polygon();
                polygon.getPoints().addAll(new Double[]{
                        (double) (size - 1) / 2, 2.5, 
                        (double) (size - 1) / 3, (double) (size - 1) / 3, 0.0, (double)  (size - 1) / 3,
                        (double) (size - 1) / 3, (double) (size - 1) / 1.5,
                        (double) (size - 1) / 6, (double) size - 1,
                        (double) (size - 1) / 2, (double) (size - 1) / 1.5,
                        (double) (size - 1) / 1.1667, (double) size - 1,
                        (double) (size - 1) / 1.5, (double) (size - 1) / 1.5,
                        (double) size - 3.5, (double) (size - 1) / 3, 
                        (double) (size - 1) / 1.5, (double) (size - 1) / 3});
                s = polygon;
                s.setFill(fill);
                break;
            case DIAMOND: polygon = new Polygon();
                polygon.getPoints().addAll(new Double[]{
                        (double) (size - 1) / 2, 1.0, 
                        0.0, (double) (size - 1) / 2,
                        (double) (size - 1) / 2, (double) size - 1,
                        (double) size - 2, (double) (size - 1) / 2}); 
                s = polygon;
                s.setFill(fill);
                break;
            case TRIANGLE: polygon = new Polygon();
                polygon.getPoints().addAll(new Double[]{
                        (double) (size/2 - 1), 0.0, 
                        0.0, (double) size - 2, 
                        (double) size - 2, (double) size - 2});
                s = polygon;
                s.setFill(fill);
                break;
        }
        // set the outline
        s.setStroke(outline);
        return s;
    }

	
}
