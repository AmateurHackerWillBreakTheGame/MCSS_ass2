package Daisyworld;
import java.lang.Math;

public class Patch {

	private char image = '_';
	
	private Daisy daisy;
	
	private double temperature = Params.global_tempreature;
	
	public Patch() {
		daisy = null;
	}
	
	public Patch(Params.DaisyType daisyType) {
		daisy = (daisyType == Params.DaisyType.BLACK) ? initBlackDaisy() : initWhiteDaisy();
	}

	public synchronized void daisyDied() {
		daisy = null;
	}
	
	public synchronized void calculateTemperature() {
		double absorbed_luminosity = 0;
		double local_heating = 0;
		
		if (daisy != null) {
			absorbed_luminosity = (1 - Params.SURFACE_ALBEDO) * daisy.getAlbedo();
		} else {
			absorbed_luminosity = (1 - Params.SURFACE_ALBEDO) * Params.solar_luminosity;
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
	
	public Daisy initWhiteDaisy() {
		image = 'W';
		return new Daisy(false, Params.DaisyType.BLACK);
	}
	
	public Daisy initBlackDaisy() {
		image = 'B';
		return new Daisy(false, Params.DaisyType.BLACK);
	}
	
	public Daisy growWhiteDaisy() {
		image = 'W';
		return new Daisy(true, Params.DaisyType.BLACK);
	}
	
	public Daisy growBlackDaisy() {
		image = 'B';
		return new Daisy(true, Params.DaisyType.BLACK);
	}
	
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
