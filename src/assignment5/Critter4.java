package assignment5;

import assignment5.Critter.CritterShape;

/* CRITTERS GUI <MyClass.java>
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Prithvi Senthilkumar
 * ps33536
 * 17840
 * Hamza Shaikh
 * hms2659
 * 17835
 * Slip days used: <0>
 * Fall 2021
 */
/**
 * Critter4 will always walk to the right if look is clear
 * has a 50% each to reprojduce either to upper left or lower right, higher threshold energy
 * will not fight other Critter4, but will always fight other critters
 * @author Prithvi and Hamza
 *
 */
public class Critter4 extends Critter {

	@Override
	public String toString() {
		return "4";
	}
	@Override
	/**
	 * simulates the time step for the critter, walks/runs/reproduces
	 */
	public void doTimeStep() {
		// TODO Auto-generated method stub
		int decision = getRandomInt(2);
		if (look(1, true) == null) {
			run(1);
		}
		
		if (getEnergy() > 100) {
			Critter4 child = new Critter4();
			if (decision == 0) {
				reproduce(child, 7);
			}
			else {
				reproduce(child, 3);
			}
            
		}

	}

	@Override
	/**
	 * @return true if critter will fight, false if not, tries to run/walk if false
	 */
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		if (oponent.equals(this.toString())) {
			return false;
		}
		return true;
	}
	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return Critter.CritterShape.DIAMOND;
	}
	@Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.PURPLE;
    }
	public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.PINK;
    }

}
