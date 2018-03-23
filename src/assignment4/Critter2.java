package assignment4;
/*
 * /* CRITTERS Critter2.java
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
 * Side Side critter is only capable of moving in two directions-- left or right.
 * @author Vaidehi and Anika 
 *
 */
public class Critter2 extends Critter.TestCritter{
	
	private int direction;

	@Override
	public void doTimeStep() {
		walk(direction);		
		if (getEnergy() > 150) {
			Critter2 sideCrit = new Critter2();
			reproduce(sideCrit, Critter.getRandomInt(8));
		}	

		// side side movement
			int new_dir = Critter.getRandomInt(2);
			switch(new_dir) {
				case 0: 
					direction = 4; 
				case 1: 
					direction = 8;
			}
	}

	@Override
	public boolean fight(String oponent) {
		return true;
	}

	public String toString() { 
		return "2";
	}
	
	public Critter2() {
		direction = Critter.getRandomInt(8);
	}
	
}

