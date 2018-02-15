package siani.javafmi;

import static org.javafmi.framework.FmiContainer.Causality.input;
import static org.javafmi.framework.FmiContainer.Causality.output;
import static org.javafmi.framework.FmiContainer.Variability.discrete;

import org.javafmi.framework.FmiSimulation;

public class NageurFMUV2 extends FmiSimulation {
	private static final String ModelName = "NageurFMUV2";
	private static final String PositionVar = "NageurFMUV2.position";
	private static final String CourantVar = "NageurFMUV2.courant";

	public double courant = 0;
	public double position = 0;
	boolean sens = true;

	@Override
	public Model define() {
		return model(ModelName).canGetAndSetFMUstate(true)
				.add(variable(PositionVar).asReal().causality(output).variability(discrete))
				.add(variable(CourantVar).asReal().causality(input).variability(discrete));

	}

	@Override
	public Status doStep(double stepSize) {

		for (int i = 1; i <= stepSize; i++) {
			if (position == 10) {
				sens = false;
			}
			if (position == 0) {
				sens = true;
			}
			if ((position < 10) && (sens)) {
				position = position + 1 + courant;
				if (position > 10) {
					double dist = position - 10;
					double tempsG = dist * stepSize / (1 + courant);
					double distP = (tempsG * (1 - courant)) / stepSize;
					position = 10 - distP;
					sens = false;
					break;

				}
			}
			if ((position <= 10) && (!sens)) {
				position = position - 1 + courant;
				if (position < 0) {
					double dist = 0 - position;
					double tempsG = dist * stepSize / (1 - courant);
					double distP = (tempsG * (1 + courant)) / stepSize;
					position = 0 + distP;
					sens = true;
					break;

				}

			}

		}
		return Status.OK;

	}

	@Override
	public Status init() {

		logger().info("doing init");
		registerReal(PositionVar, () -> position, value -> position = value);
		registerReal(CourantVar, () -> courant, value -> courant = value);
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
