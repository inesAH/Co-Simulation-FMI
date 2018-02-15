package siani.javafmi;

import static org.javafmi.framework.FmiContainer.Causality.input;
import static org.javafmi.framework.FmiContainer.Variability.discrete;

import java.math.BigDecimal;

import org.javafmi.framework.FmiSimulation;

public class EcranFMUV2 extends FmiSimulation {
	private static final String ModelName = "EcranFMUV2";
	private static final String PositionVar = "EcranFMUV2.position";

	public double position = 0;
	boolean sens = true;

	@Override
	public Model define() {
		return model(ModelName).canGetAndSetFMUstate(true)
				.add(variable(PositionVar).asReal().causality(input).variability(discrete));

	}

	@Override
	public Status doStep(double stepSize) {
		BigDecimal bd = new BigDecimal(position);
		bd = bd.setScale(2, BigDecimal.ROUND_UP);
		position = bd.doubleValue();
		System.out.println("position du nageur = " + position);

		return Status.OK;

	}

	@Override
	public Status init() {

		logger().info("doing init");
		registerReal(PositionVar, () -> position, value -> position = value);
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
