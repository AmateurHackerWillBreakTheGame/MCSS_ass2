package Daisyworld;

/**
 * Params contains all the parameters involved in the system
 * To adjust the parameters, simply change the value in a provided range 
 */
public class Params {

	/* row of a map */
	public static final int ROWS = 30;

	/* column of a map */
	public static final int COLUMNS = 30;

	/* preset maximum age of daisies */
	public static final int  MAX_AGE = 25;

	/* initial percentage of black daisies on the map */
	public static final int BLACK_PORTION = 20; // 0% to 50%

	/* initial percentage of white daisies on the map */
	public static final int WHITE_PORTION = 0; // 0% to 50%

	/* initial albedo of black daisies */
	public static final double BLACK_ALBEDO = 0.54; // 0 to 1

	/* initial albedo of white daisies */
	public static final double WHITE_ALBEDO = 0.584; // 0 to 1

	/* initial albedo of empty patch */
	public static final double SURFACE_ALBEDO = 0.4; // 0 to 1
	
	/* age reduction value of white daisy */
	public static final int WHITE_AGE_REDUCE = 10;

	/* age reduction value of black daisy */
	public static final int BLACK_AGE_REDUCE = 5;

	/* enable age reduction extension */
	public static final boolean Extension = true;

	/* maximum of tick that the model will run for */
	public static final int MAX_TICK = 10000;
	
	/* solar luminosity */
	public static double solar_luminosity = 1.001; // 0 to 3
	
	/* global temperature */
	public static double global_tempreature = 0.0;
	
	/* currentt scenario type */
	public static ScenarioType scenario = ScenarioType.maintain_current_luminosity;
	
	/* ScenarioType represent the type of scenario */
	enum ScenarioType {
		ramp_up_ramp_down,
		low_solar_luminosity,
		high_solar_luminosity,
		mid_solar_luminosity,
		maintain_current_luminosity;
	}
	
	
	/* DaisyType represent the type of daisy, which contains black and white */
	enum DaisyType {
		WHITE,
		BLACK;
	}
	
}
