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
		
		// newborn then set age to age_max, otherwise generate it randomly
		if (sprout) {
			setAge(Params.MAX_AGE);
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
		this.age ++;
	}
	
	/*
	 * check the survivability of the daisy.
	 */
	public boolean checkSurvivability() {
		
		setAge(age + 1);
		if (getAge() < Params.MAX_AGE) {
			// TODO	
		} else {
			// die
			return false;
		}
		
		return true;
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
