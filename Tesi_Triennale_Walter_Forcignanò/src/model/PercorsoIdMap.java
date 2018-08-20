package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PercorsoIdMap {

	private Map<Integer, Percorso> map = new HashMap<Integer, Percorso>();

	public void put(Percorso percorso) {
		this.map.put(percorso.getPercorso_id(), percorso);
	}

	public Percorso get(Percorso percorso) {
		if (this.map.containsKey(percorso.getPercorso_id())) {
			return this.map.get(percorso.getPercorso_id());
		}

		this.map.put(percorso.getPercorso_id(), percorso);
		return percorso;
	}

	public Percorso get(int percorsoID) {
		return this.map.get(percorsoID);
	}

	public Collection<Percorso> values() {
		// TODO Auto-generated method stub
		return map.values();
	}

	
	
}
