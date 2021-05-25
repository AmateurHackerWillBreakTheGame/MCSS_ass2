package Daisyworld;
import java.lang.Math;

public class Patch {

	public char image = ' ';
	
	private Daisy daisy;
	
	private double temperature = Params.global_tempreature;
	
	public Patch() {
		daisy = null;
	}
	
	public Patch(Params.DaisyType daisyType) {
		if (daisyType == Params.DaisyType.BLACK) {
			initBlackDaisy();
		} else {
			initWhiteDaisy();
		}
	}

	public synchronized void daisyDied() {
		daisy = null;
		image = ' ';
	}
	
	public synchronized void calculateTemperature() {
		double absorbed_luminosity = 0;
		double local_heating = 0;
		
		if (daisy != null) {
			absorbed_luminosity = (1 - daisy.getAlbedo()) * Params.solar_luminosity;
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
	
	public void initWhiteDaisy() {
		image = 'o';
		daisy = new Daisy(false, Params.DaisyType.WHITE);
	}
	
	public void initBlackDaisy() {
		image = '*';
		daisy = new Daisy(false, Params.DaisyType.BLACK);
	}
	
	public void growWhiteDaisy() {
		image = 'o';
		daisy = new Daisy(true, Params.DaisyType.WHITE);
	}
	
	public void growBlackDaisy() {
		image = '*';
		daisy = new Daisy(true, Params.DaisyType.BLACK);
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
