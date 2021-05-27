package Daisyworld;

public class Params {

	public static final int ROWS = 30;
	public static final int COLUMNS = 30;
	public static final int  MAX_AGE = 25;
	public static final int BLACK_PORTION = 20; // 0% to 100%
	public static final int WHITE_PORTION = 0; // 0% to 100%
	public static final double BLACK_ALBEDO = 0.25; // 0 to 1
	public static final double WHITE_ALBEDO = 0.75; // 0 to 1
	public static final double SURFACE_ALBEDO = 0.4; // 0 to 1
	public static double solar_luminosity = 0.8; // 0 to 3
	public static double global_tempreature = 0.0;
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
