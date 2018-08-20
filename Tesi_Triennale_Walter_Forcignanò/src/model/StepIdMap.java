package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StepIdMap {
	//la posizione è una variabile di tipo string che nella tabella è salvata come steplocationList

	private Map<String, Step> map = new HashMap<String, Step>();

	public void put(Step Step) {
		this.map.put(Step.getStep_location_list(), Step);
	}

	public Step get(Step step) {
		if (this.map.containsKey(step.getStep_location_list())) {
			return this.map.get(step.getStep_location_list());
		}

		this.map.put(step.getStep_location_list(), step);
		return step;
	}

	public Step get(String posizione) {
		return this.map.get(posizione);
	}

	public Collection <Step> values() {
		// TODO Auto-generated method stub
		return this.map.values();
	}

	
}
