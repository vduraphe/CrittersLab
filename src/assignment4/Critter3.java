package assignment4;
/*
 * /* CRITTERS Critter3.java
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
 * creates crit that moves up and down only
 * @author Vaidehi and Anika
 *
 */
public class Critter3 extends Critter.TestCritter{

	private int direction;

	@Override
	public void doTimeStep() {
		walk(direction);
		if (getEnergy() > 150) {
			Critter3 upCrit = new Critter3();
			reproduce(upCrit, Critter.getRandomInt(8));
		}

		//only move up down
		int new_dir = Critter.getRandomInt(2);
		switch(new_dir) {
		case 0: 
			direction = 2; 
		case 1: 
			direction = 6;
		}
	}

	@Override
	public boolean fight(String oponent) {
		return true;
	}

	public String toString() { 
		return "3";
	}

	public Critter3() {
		direction = Critter.getRandomInt(8);
	}

}