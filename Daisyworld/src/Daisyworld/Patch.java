package Daisyworld;
import java.lang.Math;

public class Patch {

	private Daisy daisy;
	
	private double temperature = Params.global_tempreature;
	
	public Patch() {
		daisy = null;
	}
	
	public Patch(Params.DaisyType daisyType) {
		daisy = (daisyType == Params.DaisyType.BLACK) ? growBlackDaisy() : growWhiteDaisy();
	}
	
	public synchronized void update() {}

	public void daisyDied() {
		daisy = null;
	}

	public synchronized void diffuse() {
		// TODO send 50% of its temperature to its 8 neighbours
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
	
	public double getTemperature() {
		return temperature;
	}
	
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public Daisy growWhiteDaisy() {
		return new Daisy(true, Params.DaisyType.BLACK);
	}
	
	public Daisy growBlackDaisy() {
		return new Daisy(true, Params.DaisyType.BLACK);
	}
	
	public boolean existDaisy() {
		return true;
	}
	
	public Daisy getDaisy() {
		return daisy;
	}
	
	public String toString() {
		return "0";
	}

}
