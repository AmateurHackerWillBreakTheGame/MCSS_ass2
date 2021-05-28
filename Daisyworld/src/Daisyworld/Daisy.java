package Daisyworld;
import java.util.Random;
import Daisyworld.Params.DaisyType;

/**
 * Daisy is a class that contains all the relevant information of daisies 
 * grow on a patch 
 */
public class Daisy {
	private int age;
	private double albedo;
	private DaisyType daisyType;

	public Daisy(boolean sprout, DaisyType daisyType) {
		
		// newborn then set age to 0, otherwise generate it randomly
		if (sprout) {
			setAge(0);
		} else {
			Random r = new Random();
			setAge(r.nextInt(Params.MAX_AGE));
		}
		
		setDaisyType(daisyType);
		
		if (daisyType == DaisyType.WHITE) {
			setAlbedo(Params.WHITE_ALBEDO);
		} else {
			setAlbedo(Params.BLACK_ALBEDO);
		}
	}

	/**
	 * grow method adds 1 to the current age while the age is not
	 * exceed the maximum age
	 */
	public void grow() {
		assert(age < Params.MAX_AGE);
		setAge(getAge() + 1);
	}
	
	/**
	 * check the survivability of the daisy.
	 * @param temperature the temperature of the patch
	 * @return 0 if the daisies are died, 
	 *         1 if the daisies are still alive and not providing seeds
	 * 		   2 if the daisies providing seeds
	 */
	public int checkSurvivability(double temperature) {
		double seedThreshold = 0;
		Random r = new Random();
		int flag = 1;
		
		grow();
		if (getAge() < Params.MAX_AGE) {
			seedThreshold =(0.1457 * temperature) - (0.0032 * (temperature * temperature)) - 0.6443;
			
			if (r.nextDouble() < seedThreshold) {
				flag = 2;
			}
		} else {
			// die
			flag = 0;
		}
		
		return flag;
	}
	
	public DaisyType getDaisyType() {
		return daisyType;
	}

	public void setDaisyType(DaisyType daisyType) {
		this.daisyType = daisyType;
	}

	public double getAlbedo() {
		return albedo;
	}

	public void setAlbedo(double albedo) {
		this.albedo = albedo;
	}

	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
}
