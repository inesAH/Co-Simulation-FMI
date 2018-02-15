package siani.javafmi;

import static org.javafmi.framework.FmiContainer.Causality.output;
import static org.javafmi.framework.FmiContainer.Variability.discrete;

import org.javafmi.framework.FmiSimulation;

public class CourantFMUV2 extends FmiSimulation {
	private static final String ModelName = "CourantFMUV2";
	private static final String Puissance = "CourantFMUV2.puissance";

	public double puissance = 0.0;

	@Override
	public Model define() {
		return model(ModelName).canGetAndSetFMUstate(true)
				.add(variable(Puissance).asReal().causality(output).variability(discrete));

	}

	@Override
	public Status doStep(double temps) {
		return Status.OK;
	}

	@Override
	public Status init() {
		logger().info("doing init");
		registerReal(Puissance, () -> puissance, value -> puissance = value);
		return Status.OK;
	}

	@Override
	public Status reset() {
		return Status.OK;
	}

	@Override
	public Status terminate() {
		return Status.OK;
	}

	public Status getState(String stateId) {
		logger().info("doing get state");
		return super.getState(stateId);
	}

}
