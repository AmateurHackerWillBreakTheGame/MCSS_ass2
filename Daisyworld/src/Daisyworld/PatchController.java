package Daisyworld;

public class PatchController extends PatchControlThread {
	
	private int counter = 0;
	
	private double global_temperature;
	
	protected Patch[][] map;
	
	// contains the value to be updated for each patch
	protected double[][] diffuseMap = new double[Params.ROWS][Params.COLUMNS];
	
	public PatchController(Patch[][] map) {
		super();
		this.map = map;
		this.global_temperature = calculate_global_temperature();
	}
	
	public synchronized double calculate_global_temperature() {
		double total = 0;
		int i = 0, j = 0;
		
		for (; i < Params.ROWS; i++) {
			for (; j < Params.COLUMNS; j++) {
				total += map[i][j].getTemperature();
			}
		}
		
		return total / (Params.ROWS * Params.COLUMNS);
	}
	
	public synchronized void diffuse() {
		int i, j, x ,y;
		
		// update the diffuseMap
		for (i = 0; i < Params.ROWS; i++) {
			for (j = 0; j < Params.COLUMNS; j++) {
				diffuseMap[i][j] = 0;

				for (x = i - 1; x <= i + 1; x++) {
					if (x >= 0 && x < Params.ROWS) {
						for (y = j - 1; y <= j + 1; y++) {
							if (y >= 0 && y < Params.COLUMNS) {
								// exclude the center patch
								if (y != j && x != i) {
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
				map[i][j].setTemperature(diffuseMap[i][j] + map[i][j].getTemperature() / 2);
			}
		}
	}
	
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
	
	private synchronized void growRandomEmptyPatch(Patch[][] map, int i, int j) {
		int x, y;
		
		for (x = i - 1; x <= i + 1; x++) {
			if (x >= 0 && x < Params.ROWS) {
				for (y = j - 1; y <= j + 1; y++) {
					if (y >= 0 && x < Params.ROWS) {
						if (map[x][y].getDaisy() == null) {
							
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

	private synchronized boolean existDaisy() {
		int i, j;

		for (i = 0; i < Params.ROWS; i++) {
			for (j = 0; j < Params.COLUMNS; j++) {
				 if (map[i][j].getDaisy() != null) {
					 return true;
				 }
			}
		}

		return false;
	}
	/*
	 * mimic the go function
	 */
	public synchronized void run() {
		int i, j;
			
		while (existDaisy()) {
			// calculate the new temperature
			for (i = 0; i < Params.ROWS; i++) {
				for (j = 0; j < Params.COLUMNS; j++) {
					 map[i][j].calculateTemperature();
				}
			}
			
			// diffuse by 50% --- to give 50% of the temperature to its 8 neighboring patches.
			diffuse();
			
			// check daisies survivability
			checkSurvivability();
			
			// calculate global temperature
			global_temperature = calculate_global_temperature();
			
			// update display (maybe print?)
			counter++;
			update();
		}
	}

	private void update() {
		int i, j;
		
		System.out.print("\n");
		System.out.println("                    --------------- Simulation round " + counter + " ---------------");
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
