package assignment4;
/*
 * CRITTERS Critter4.java
 * EE422C Project 4 submission by
 * Vaidehi Duraphe
 * vd5374
 * Anika Agarwal
 * aa59662
 * Slip days used: <0>
 * Spring 18
 */
import java.util.List;
/**
 * RunCrit is only capable of running (in any direction)
 * @author Vaidehi and anika
 *
 */
public class Critter4 extends Critter.TestCritter{
	
	private int direction;

	@Override
	public void doTimeStep() {
		run(direction);		
		if (getEnergy() > 150) {
			Critter4 RunCrit = new Critter4();
			reproduce(RunCrit, Critter.getRandomInt(8));
		}	
			int new_dir = Critter.getRandomInt(2);
			switch(new_dir) {
				case 0: 
					direction = 1; 
				case 1: 
					direction = 2;
				case 2: 
					direction = 3; 
				case 3: 
					direction = 4;
				case 4: 
					direction = 5; 
				case 5: 
					direction = 6;
				case 6: 
					direction = 7; 
				case 7: 
					direction = 8;
			}
	}

	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		return true;
	}

	public String toString() { 
		return "$";
	}
	
	public Critter4() {
		direction = Critter.getRandomInt(8);
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.SQUARE;
	}
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.DARKSLATEBLUE; 
	}
	
}

