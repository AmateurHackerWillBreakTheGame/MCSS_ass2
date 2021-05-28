package Daisyworld;
import java.util.Random;
import Daisyworld.Params;

/**
 * Daisy is a class that contains all the relevant information of daisies 
 * grow on a patch 
 */
public class Daisy {
	/* maximum value of age for a daisy to live to */
	private int maxAge = Params.MAX_AGE;

	/* the current age of a daisy */
	private int age;

	/* the albedo of the daisy */
	private double albedo;

	/* the type of daisy: black or white */
	private Params.DaisyType daisyType;

	/**
	 * The constructor of Diays class, 
	 * determine whether it is a randomly generated daisy, or a newborn daisy.
	 * @param sprout whether it is a newborn or a randomly generated daisy.
	 * @param daisyType the type of the daisy
	 */
	public Daisy(boolean sprout, Params.DaisyType daisyType) {
		
		// newborn then set age to 0, otherwise generate it randomly
		if (sprout) {
			setAge(0);
		} else {
			Random r = new Random();
			setAge(r.nextInt(Params.MAX_AGE));
		}
		
		setDaisyType(daisyType);
		
		if (daisyType == Params.DaisyType.WHITE) {
			setAlbedo(Params.WHITE_ALBEDO);
		} else {
			setAlbedo(Params.BLACK_ALBEDO);
		}
		
		
		// set max age if extensions applied
		if (Params.Extension) {
			Random r = new Random();
			if (daisyType == Params.DaisyType.WHITE) {
				maxAge = Params.MAX_AGE - r.nextInt(Params.WHITE_AGE_REDUCE);
			} else {
				maxAge = Params.MAX_AGE - r.nextInt(Params.BLACK_AGE_REDUCE);	
			}
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

		// return a flag indicating:
		// 2: if it is able to grow new daisy in its neighbour areas
		// 0: the daisy dies
		// 1: the daisy grows up
		if (getAge() < maxAge) {
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
	public Params.DaisyType getDaisyType() {
		return daisyType;
	}

	public void setDaisyType(Params.DaisyType daisyType) {
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
