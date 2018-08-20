package model;

public class Step {

	private String taxi_id;
	private int percorso_id;   //da decidere se risalire al percorso tramite id oppure tramite oggetto;
	private int step_id;
	private String street_for_each_step;
	private double travel_time_per_step;
	private double distance_per_step;
	private String step_maneuvers;
	private String step_direction;
	private String step_location_list;
	
	public Step(String taxi_id, int percorso_id, int step_id, String street_for_each_step, double travel_time_per_step,
			double distance_per_step, String step_maneuvers, String step_direction, String step_location_list) {
		super();
		this.taxi_id = taxi_id;
		this.percorso_id = percorso_id;
		this.step_id = step_id;
		this.street_for_each_step = street_for_each_step;
		this.travel_time_per_step = travel_time_per_step;
		this.distance_per_step = distance_per_step;
		this.step_maneuvers = step_maneuvers;
		this.step_direction = step_direction;
		this.step_location_list = step_location_list;
	}

	public String getTaxi_id() {
		return taxi_id;
	}

	public int getPercorso_id() {
		return percorso_id;
	}

	public int getStep_id() {
		return step_id;
	}

	public String getStreet_for_each_step() {
		return street_for_each_step;
	}

	public double getTravel_time_per_step() {
		return travel_time_per_step;
	}

	public double getDistance_per_step() {
		return distance_per_step;
	}

	public String getStep_maneuvers() {
		return step_maneuvers;
	}

	public String getStep_direction() {
		return step_direction;
	}

	public String getStep_location_list() {
		return step_location_list;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distance_per_step);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((step_location_list == null) ? 0 : step_location_list.hashCode());
		temp = Double.doubleToLongBits(travel_time_per_step);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Step other = (Step) obj;
		if (Double.doubleToLongBits(distance_per_step) != Double.doubleToLongBits(other.distance_per_step))
			return false;
		if (step_location_list == null) {
			if (other.step_location_list != null)
				return false;
		} else if (!step_location_list.equals(other.step_location_list))
			return false;
		if (Double.doubleToLongBits(travel_time_per_step) != Double.doubleToLongBits(other.travel_time_per_step))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Step [percorso_id=" + percorso_id + ", step_id=" + step_id + ", travel_time_per_step="
				+ travel_time_per_step + ", distance_per_step=" + distance_per_step + ", step_location_list="
				+ step_location_list + "]";
	}


	

	
	
	
	
	
}
