package Daisyworld;

public class Params {

	public static final int ROWS = 30;
	public static final int COLUMNS = 30;
	public static final int  MAX_AGE = 25;
	public static final int BLACK_PORTION = 10;
	public static final int WHITE_PORTION = 10;
	public static final double BLACK_ALBEDO = 0.25;
	public static final double WHITE_ALBEDO = 0.75;
	public static final double SURFACE_ALBEDO = 0.4;
	public static double solar_luminosity = 1.0;
	public static double global_tempreature = 0.0;

	/* DaisyType represent the type of daisy, which contains black and white */
	enum DaisyType {
		WHITE,
		BLACK;
	}
	
}
