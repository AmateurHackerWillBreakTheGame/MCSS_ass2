package Daisyworld;
public class DaisySimulation {

	
	public static void main(String[] args) {
		
		Patch daisyWorld = new Patch();
		PatchController pc = new PatchController(daisyWorld);
		
		// start threads
		pc.start();
		
		// check all threads still alive
		while (pc.isAlive()) {
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				PatchControlThread.terminate(e);
			}
		}
		
		// interrupt other threads
		
		System.out.println("Sim terminating");
		System.out.println(PatchControlThread.getTerminateException());
		System.exit(0);
	}

}
