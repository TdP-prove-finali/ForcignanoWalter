package model;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Percorso {
	private int percorso_id;
	private String taxi_id;
	private String starting_street;
	private String end_street;
	private double total_distance;
	private double total_travel_time;
	private int number_of_steps;
	
	private List<Step> listaStep;

	public Percorso(int percorso_id, String taxi_id, String starting_street, String end_street, double total_distance,
			double total_travel_time, int number_of_steps) {
		super();
		this.percorso_id = percorso_id;
		this.taxi_id = taxi_id;
		this.starting_street = starting_street;
		this.end_street = end_street;
		this.total_distance = total_distance;
		this.total_travel_time = total_travel_time;
		this.number_of_steps = number_of_steps;
		this.listaStep=new ArrayList<>();
	}

	public int getPercorso_id() {
		return percorso_id;
	}

	public String getTaxi_id() {
		return taxi_id;
	}

	public String getStarting_street() {
		return starting_street;
	}

	public String getEnd_street() {
		return end_street;
	}

	public double getTotal_distance() {
		return total_distance;
	}

	public double getTotal_travel_time() {
		return total_travel_time;
	}

	public int getNumber_of_steps() {
		return number_of_steps;
	}

	public List<Step> getListaStep() {
		return listaStep;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + percorso_id;
		result = prime * result + ((taxi_id == null) ? 0 : taxi_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Percorso other = (Percorso) obj;
		if (percorso_id != other.percorso_id)
			return false;
		if (taxi_id == null) {
			if (other.taxi_id != null)
				return false;
		} else if (!taxi_id.equals(other.taxi_id))
			return false;
		return true;
	}

	public void loadStepInPercorso(Step stepPercorso) {
		
		this.listaStep.add(stepPercorso);
		this.listaStep.sort(new Comparator<Step>() {

			@Override
			public int compare(Step o1, Step o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getStep_id(), o2.getStep_id());
			}
		});
		
	}

	@Override
	public String toString() {
		return "Percorso [percorso_id=" + percorso_id + ", total_distance=" + total_distance + ", total_travel_time="
				+ total_travel_time + "]";
	}
	
	
}
