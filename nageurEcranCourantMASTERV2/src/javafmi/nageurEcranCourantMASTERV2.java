package javafmi;

import org.javafmi.wrapper.Simulation;

public class nageurEcranCourantMASTERV2 {

	int startTime = 0;
	int stopTime = 32;
	int stepSize = 1;
	double courant = 0.;
	double position = 0.;

	public nageurEcranCourantMASTERV2() {
		Simulation courantSim = new Simulation("FMUs/CourantFMUV2.fmu");
		Simulation nageurSim = new Simulation("FMUs/NageurFMUV2.fmu");
		Simulation ecranSim = new Simulation("FMUs/EcranFMUV2.fmu");

		courantSim.init(startTime, stopTime);
		nageurSim.init(startTime, stopTime);
		ecranSim.init(startTime, stopTime);
		System.out.println("NAGEUR PUIS ECRAN");

		for (int i = startTime; i < stopTime; i++) { // nageur puis ecran
			courantSim.doStep(stepSize);
			courant = courantSim.read("CourantFMUV2.puissance").asDouble();

			nageurSim.write("NageurFMUV2.courant").with(0.5);
			position = nageurSim.read("NageurFMUV2.position").asDouble();

			nageurSim.doStep(stepSize);
			ecranSim.write("EcranFMUV2.position").with(position);

			ecranSim.doStep(stepSize);

		}

		ecranSim.terminate();
		nageurSim.terminate();
		courantSim.terminate();
	}

	public static void main(String[] args) {
		System.out.println("--- Master --- ");
		new nageurEcranCourantMASTERV2();

	}

}
