package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TaxiIdMap {

	private Map<String, Taxi> map = new HashMap();

	public void put(Taxi taxi) {
		this.map.put(taxi.getId(), taxi);
	}

	public Taxi get(Taxi taxi) {
		if (this.map.containsKey(taxi.getId())) {
			return this.map.get(taxi.getId());
		}

		this.map.put(taxi.getId(), taxi);
		return taxi;
	}

	public Taxi get(int taxiID) {
		return this.map.get(taxiID);
	}

	
	public Collection<Taxi> values(){
		return this.map.values();
	}
}
