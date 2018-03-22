package assignment4;

import java.util.List;
/**
 * Side Side critter is only capable of moving in two directions-- left or right.
 * @author Vaidehi
 *
 */
public class SideSideCritter extends Critter.TestCritter{
	
	private int direction;

	@Override
	public void doTimeStep() {
		// Move
		walk(direction);		

		// Reproduce
		if (getEnergy() > 150) {
			SideSideCritter sideCrit = new SideSideCritter();
			reproduce(sideCrit, Critter.getRandomInt(8));
		}	

		// Turn -- bishop style movement (diagonals only)
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
		// TODO Auto-generated method stub
		return true;
	}

	public String toString() { 
		return "-";
	}
	
	public SideSideCritter() {
		direction = Critter.getRandomInt(8);
	}
	
}

