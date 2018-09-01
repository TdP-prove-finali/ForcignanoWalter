package model;

import com.javadocmd.simplelatlng.LatLng;

public class Posizione {

	private LatLng coordinate;

	private String nomeLuogo;
	private String manovra;

	public Posizione(LatLng coordinate, String nomeLuogo) {
		super();
		this.coordinate = coordinate;
		this.nomeLuogo = nomeLuogo;
	}

	public LatLng getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(LatLng coordinate) {
		this.coordinate = coordinate;
	}

	public String getNomeLuogo() {
		return nomeLuogo;
	}

	public void setNomeLuogo(String nomeLuogo) {
		this.nomeLuogo = nomeLuogo;
	}

	public String getManovra() {
		return manovra;
	}

	public void setManovra(String manovra) {
		this.manovra = manovra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		return result;
	}

	/**
	 * Due posizioni risultano uguali se hanno le stesse coordinate.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Posizione other = (Posizione) obj;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate)) {
//			
//			double latA =coordinate.getLatitude();
//			double longA = coordinate.getLongitude();
//			
//			double latB = other.coordinate.getLatitude();
//			double longB =other.coordinate.getLongitude();
//			
//			double precisione = 0.0002; //corrisponde a circa 22 metri.
//			if((latA+precisione)<latB || (latA-precisione)<latB)
		
			return false;
		}
		return true;
	}

	
	@Override
	public String toString() {
		return nomeLuogo + " " + coordinate.toString();
	}

	
}
