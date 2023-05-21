package ppgit.application;

import hla.rti1516e.exceptions.RTIinternalError;
import ppgit.hlamodule.HlaInterfaceImpl;
import ppgit.simulation.Simulator;

public class Main {

	public static void main(String[] args) throws RTIinternalError {
		Simulator simulator = new Simulator("FEDERADO-0");
		
		HlaInterfaceImpl hlaInterface = new HlaInterfaceImpl();
		
		hlaInterface.run(simulator.getName());

	}

}
