package Daisyworld;
import java.lang.Math;

/**
 * Patch is a class that contains all useful for a patch in the map 
 * including the daisies grow on the patch and the temperature 
 */
public class Patch {

	/* the image of the patch displayed in the map */
	public char image = ' ';
	
	/* the daisy on the patch */
	private Daisy daisy;
	
	/* the temperature of the patch */
	private double temperature = Params.global_tempreature;
	
	/**
	 * when there is no input parameter, the patch created contains no daisies
	 */
	public Patch() {
		daisy = null;
	}
	
	/**
	 * initiate the daisy based on the type on the new patch created
	 * @param daisyType the type of daisy on the patch
	 */
	public Patch(Params.DaisyType daisyType) {
		if (daisyType == Params.DaisyType.BLACK) {
			initBlackDaisy();
		} else {
			initWhiteDaisy();
		}
	}

	/**
	 * update the relavent variables when the daisies on this patch are died
	 */
	public synchronized void daisyDied() {
		daisy = null;
		image = ' ';
	}
	
	/**
	 * update the local temperature based on solar luminosity and albedo rate
	 * @param solar_luminosity the luminosity of the sun
	 */
	public synchronized void calculateTemperature(double solar_luminosity) {
		double absorbed_luminosity = 0;
		double local_heating = 0;
		
		if (daisy != null) {
			absorbed_luminosity = (1 - daisy.getAlbedo()) * solar_luminosity;
		} else {
			absorbed_luminosity = (1 - Params.SURFACE_ALBEDO) * solar_luminosity;
		}
		
		if (absorbed_luminosity > 0) {
			local_heating = 72 * Math.log(absorbed_luminosity) + 80;
		} else {
			local_heating = 80;
		}
		
		temperature = (temperature + local_heating) / 2;
	}
	
	public synchronized double getTemperature() {
		return temperature;
	}
	
	public synchronized void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * initalise white daisies when patch is created 
	 */
	public void initWhiteDaisy() {
		image = 'o';
		daisy = new Daisy(false, Params.DaisyType.WHITE);
	}
	
	 /**
	 * initalise black daisies when patch is created 
	 */
	public void initBlackDaisy() {
		image = '*';
		daisy = new Daisy(false, Params.DaisyType.BLACK);
	}
	
	/**
	 * sprout new white daisies on this patch
	 */
	public void growWhiteDaisy() {
		image = 'o';
		daisy = new Daisy(true, Params.DaisyType.WHITE);
	}
	
	/**
	 * sprout new black daisies on this patch
	 */
	public void growBlackDaisy() {
		image = '*';
		daisy = new Daisy(true, Params.DaisyType.BLACK);
	}
	
	/**
	 * check if there are daisies on the patch
	 * @return true if there exists daisies
	 */
	public synchronized boolean existDaisy() {
		if (this.getDaisy() != null) {
			return true;
		}
		
		return false;
	}
	
	public Daisy getDaisy() {
		return daisy;
	}
	
	public String toString() {
		return "[" + image + "]";
	}

}
