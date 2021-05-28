package Daisyworld;
import Daisyworld.Params.ScenarioType;

/**
 * PatchController class control the up
 */
public class PatchController extends PatchControlThread {
	
	private int ticks = 0;
	
	private double global_temperature;
	
	protected Patch[][] map;
	
	private ScenarioType scenario = Params.scenario;
	
	private double solar_luminosity;
	
	// contains the value to be updated for each patch
	protected double[][] diffuseMap = new double[Params.ROWS][Params.COLUMNS];
	
	/* generate a controller that mimic the map behaviours */
	public PatchController(Patch[][] map) {
		super();
		this.map = map;
		this.global_temperature = calculate_global_temperature();
		
		if (scenario == Params.ScenarioType.high_solar_luminosity) {
			solar_luminosity = 1.4;
		} else if (scenario == Params.ScenarioType.low_solar_luminosity) {
			solar_luminosity = 0.6;
		} else if (scenario == Params.ScenarioType.maintain_current_luminosity) {
			solar_luminosity = Params.solar_luminosity;
		} else if (scenario == Params.ScenarioType.mid_solar_luminosity) {
			solar_luminosity = 1.0;
		} else {
			solar_luminosity = 0.8;
		}
	}
	
	/**
	 * calculate the global temperature of the map
	 * @return
	 */
	public synchronized double calculate_global_temperature() {
		double total = 0;
		int i, j;
		
		for (i=0 ; i < Params.ROWS; i++) {
			for (j=0 ; j < Params.COLUMNS; j++) {
				total += map[i][j].getTemperature();
			}
		}
		return total / (Params.ROWS * Params.COLUMNS);
	}
	
	/**
	 * perform temperature diffusion 
	 */
	public synchronized void diffuse() {
		int i, j, x ,y;

		
		// initialize diffusemap
		for (i=0; i<Params.ROWS; i++) {
			for (j=0; j<Params.COLUMNS; j++) {
				diffuseMap[i][j] = 0;
			}
		}
		
		// update the diffuseMap
		for (i = 0; i < Params.ROWS; i++) {
			for (j = 0; j < Params.COLUMNS; j++) {
				
				// assign half of the temperature evenly to neighbours
				for (x = i - 1; x <= i + 1; x++) {
					if (x >= 0 && x < Params.ROWS) {
						for (y = j - 1; y <= j + 1; y++) {
							if (y >= 0 && y < Params.COLUMNS) {
								// exclude the center patch
								if (y != j || x != i) {
									diffuseMap[x][y] += map[i][j].getTemperature() / 16;
								}
							}
						}
					}
				}
			}
		}
		
		// update the temperature to map
		for (i = 0; i < Params.ROWS; i++) {
			for (j = 0; j < Params.COLUMNS; j++) {

				int neighbours = 0;
				
				// calculate neighbours number
				for (x = i - 1; x <= i + 1; x++) {
					if (x >= 0 && x < Params.ROWS) {
						for (y = j - 1; y <= j + 1; y++) {
							if (y >= 0 && y < Params.COLUMNS) {
								// exclude the center patch
								if (y != j || x != i) {
									neighbours += 1;
								}
							}
						}
					}
				}
				
				map[i][j].setTemperature(diffuseMap[i][j] + map[i][j].getTemperature() / 2 + map[i][j].getTemperature() * (8-neighbours) / 16);
			}
		}
	}
	
	/**
	 * checkSurvivability check the state of daisies on each patch and 
	 * make relavent update if necessary
	 */
	public synchronized void checkSurvivability() {
		int i, j;
		
		for (i = 0; i < Params.ROWS; i++) {
			for (j = 0; j < Params.COLUMNS; j++) {
				 if (map[i][j].existDaisy()) {
					 int flag = map[i][j].getDaisy().checkSurvivability(map[i][j].getTemperature());
					 // generate new seed
					 if (flag == 2) {
						 growRandomEmptyPatch(map, i, j);
					 } else if (flag == 0) {
						 map[i][j].daisyDied();
					 }
				 }
			}
		}
	}
	
	/**
	 * sprout daisies on an empty neighbor patch randomly 
	 * if a patch provides seeds
	 * @param map 
	 * @param i the row of the patch that provides seed
	 * @param j the column of the patch that provides seed
	 */
	private synchronized void growRandomEmptyPatch(Patch[][] map, int i, int j) {
		int x, y;

		for (x = i - 1; x <= i + 1; x++) {
			if (x >= 0 && x < Params.ROWS) {
				for (y = j - 1; y <= j + 1; y++) {
					if (y >= 0 && y < Params.COLUMNS) {
						if (map[x][y].getDaisy() == null && (x != i || y != j)) {
							if (map[i][j].getDaisy().getDaisyType() == Params.DaisyType.BLACK) {
								map[x][y].growBlackDaisy();
							} else {
								map[x][y].growWhiteDaisy();
							}
						}
					}
				}
			}
		}
	}

	private synchronized boolean existBothDaisy() {
		int i, j;
		boolean white = false;
		boolean black = false;
		
		for (i = 0; i < Params.ROWS; i++) {
			for (j = 0; j < Params.COLUMNS; j++) {
				 if (map[i][j].getDaisy() != null) {
					 if (map[i][j].getDaisy().getDaisyType() == Params.DaisyType.WHITE) {
						 white = true;
					 } else {
						 black = true;
					 }
				 }
			}
		}

		return white && black;
	}
	/*
	 * mimic the go function, with a limitation of ticks performed
	 */
	public synchronized void run() {
		int i, j;
		int n = 0;
			
		while (existBothDaisy() && n < Params.MAX_TICK) {
			n += 1;
			
			// calculate the new temperature
			for (i = 0; i < Params.ROWS; i++) {
				for (j = 0; j < Params.COLUMNS; j++) {
					 map[i][j].calculateTemperature(solar_luminosity);
				}
			}
			
			// diffuse by 50% --- to give 50% of the temperature to its 8 neighboring patches.
			diffuse();
			
			// check daisies survivability
			checkSurvivability();
			
			// calculate global temperature
			global_temperature = calculate_global_temperature();
			
			// update display (maybe print?)
			ticks++;
			update();
			
			// change solar_luminosity
			if (scenario == Params.ScenarioType.high_solar_luminosity) {
				solar_luminosity = 1.4;
			} else if (scenario == Params.ScenarioType.low_solar_luminosity) {
				solar_luminosity = 0.6;
			} else if (scenario == Params.ScenarioType.ramp_up_ramp_down) {
				if (ticks > 200 && ticks <= 400) {
					solar_luminosity += 0.005;
				} else if (ticks > 600 && ticks <= 850) {
					solar_luminosity -= 0.0025;
				}
			} else if (scenario == Params.ScenarioType.mid_solar_luminosity) {
				solar_luminosity = 1.0;
			}
		}
	}

	private void update() {
		int i, j;
		System.out.print("\n");
		System.out.println("                    --------------- Simulation round " + ticks + " ---------------");
		System.out.println("current global temperature: " + global_temperature);
		
		for (i = 0; i < Params.ROWS; i++) {
			if (i < 10) {
				System.out.print("0");
			}
			System.out.print(i + ": ");
			for (j = 0; j < Params.COLUMNS; j++) {
				System.out.print(map[i][j].toString());
			}
			System.out.print("\n");
		}
	}
}
