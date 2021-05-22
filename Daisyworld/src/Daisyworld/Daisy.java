package Daisyworld;
import java.util.Random;

/**
 * DaisyType represent the type of daisy, which contains black and white
 */
enum DaisyType {
	WHITE,
	BLACK;
}

/**
 * Daisy is a class that contains all the relevant information of daisies 
 * grow on a patch 
 */
public class Daisy {
	static final int MAX_AGE = 25;
	private int age;
	public final double albedo; 
	public final DaisyType daisyType;

	public Daisy(double albedo, DaisyType daisyType) {
		Random r = new Random();
		this.age = r.nextInt(MAX_AGE);
		this.albedo = albedo;
		this.daisyType = daisyType;
	}

	/**
	 * get the current age of the daisy
	 * @return
	 */
	public int getAge() {
		return age;
	}

	/**
	 * grow method adds 1 to the current age while the age is not
	 * exceed the maximum age
	 */
	public void grow() {
		assert(age < MAX_AGE);
		this.age ++;
	}
	



}
