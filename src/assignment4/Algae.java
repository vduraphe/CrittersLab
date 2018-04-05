package assignment4;

/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;
import assignment4.Critter.CritterShape;

public class Algae extends TestCritter {

	public String toString() { return "@"; }
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
	
	public CritterShape viewShape() { 
		return CritterShape.STAR; 
	}
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.GREEN; 
	}
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.DARKGREEN; 
	}
	
}
