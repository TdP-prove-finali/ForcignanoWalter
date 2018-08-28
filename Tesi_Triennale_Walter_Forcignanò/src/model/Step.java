package model;

public class Step {

	private String taxi_id;
	private int percorso_id; // da decidere se risalire al percorso tramite id oppure tramite oggetto;
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
		result = prime * result + percorso_id;
		result = prime * result + ((step_direction == null) ? 0 : step_direction.hashCode());
		result = prime * result + step_id;
		result = prime * result + ((step_location_list == null) ? 0 : step_location_list.hashCode());
		result = prime * result + ((step_maneuvers == null) ? 0 : step_maneuvers.hashCode());
		result = prime * result + ((street_for_each_step == null) ? 0 : street_for_each_step.hashCode());
		result = prime * result + ((taxi_id == null) ? 0 : taxi_id.hashCode());
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
		if (percorso_id != other.percorso_id)
			return false;
		if (step_direction == null) {
			if (other.step_direction != null)
				return false;
		} else if (!step_direction.equals(other.step_direction))
			return false;
		if (step_id != other.step_id)
			return false;
		if (step_location_list == null) {
			if (other.step_location_list != null)
				return false;
		} else if (!step_location_list.equals(other.step_location_list))
			return false;
		if (step_maneuvers == null) {
			if (other.step_maneuvers != null)
				return false;
		} else if (!step_maneuvers.equals(other.step_maneuvers))
			return false;
		if (street_for_each_step == null) {
			if (other.street_for_each_step != null)
				return false;
		} else if (!street_for_each_step.equals(other.street_for_each_step))
			return false;
		if (taxi_id == null) {
			if (other.taxi_id != null)
				return false;
		} else if (!taxi_id.equals(other.taxi_id))
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
