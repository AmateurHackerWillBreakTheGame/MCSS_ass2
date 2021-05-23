package Daisyworld;

public class PatchController extends PatchControlThread {

	private double global_temperature;
	
	protected Patch[][] map;
	
	public PatchController(Patch[][] map) {
		super();
		this.map = map;
		this.global_temperature = calculate_global_temperature();	
	}
	
	public double calculate_global_temperature() {
		double total = 0;
		int i = 0, j = 0;
		
		for (; i < Params.ROWS; i++) {
			for (; j < Params.COLUMNS; j++) {
				total += map[i][j].getTemperature();
			}
		}
		
		return total / (Params.ROWS * Params.COLUMNS);
	}
	
	/*
	 * mimic the go function
	 */
	public void run() {
		int i, j;

		// calculate the new temperature
		for (i = 0; i < Params.ROWS; i++) {
			for (j = 0; j < Params.COLUMNS; j++) {
				 map[i][j].calculateTemperature();
				 
			}
		}
		
		// diffuse by 50% --- to give 50% of the temperature to its 8 neighboring patches.
		for (i = 0; i < Params.ROWS; i++) {
			for (j = 0; j < Params.COLUMNS; j++) {
				 map[i][j].diffuse();
			}
		}
		
		// check daisies survivability
		for (i = 0; i < Params.ROWS; i++) {
			for (j = 0; j < Params.COLUMNS; j++) {
				 if (map[i][j].existDaisy()) {
					 if (map[i][j].getDaisy().checkSurvivability()) {
						 
					 } else {
						 map[i][j].daisyDied();
					 }
					 
				 }
			}
		}
		
		// calculate global temperature
		global_temperature = calculate_global_temperature();
		
		// TODO update display (maybe print?)
	}
}
