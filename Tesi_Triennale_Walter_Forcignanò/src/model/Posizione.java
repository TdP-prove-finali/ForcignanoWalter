package model;

import java.util.HashMap;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

public class Posizione {

	private LatLng coordinate;

	private String nomeLuogo;

	private int precisioneCoordinate;
	
	private Map<Posizione, String> manovre;

	public Posizione(LatLng coordinate, String nomeLuogo,int precisioneCoordinate) {
		super();
		this.coordinate = coordinate;
		this.nomeLuogo = nomeLuogo;
		this.precisioneCoordinate=precisioneCoordinate;
		this.manovre= new HashMap<>();
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
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		double latitude =  Math.round( coordinate.getLatitude() * Math.pow( 10, precisioneCoordinate ) )/Math.pow( 10, precisioneCoordinate );
		double longitude = Math.round( coordinate.getLongitude() * Math.pow( 10, precisioneCoordinate ) )/Math.pow( 10,precisioneCoordinate );
		
		return nomeLuogo + " (" + latitude+","+longitude+")";
	}
	
	public String getCoordinateStampate() {
		double latitude =  Math.round( coordinate.getLatitude() * Math.pow( 10, precisioneCoordinate ) )/Math.pow( 10, precisioneCoordinate );
		double longitude = Math.round( coordinate.getLongitude() * Math.pow( 10, precisioneCoordinate ) )/Math.pow( 10, precisioneCoordinate );
		return "(" + latitude+","+longitude+")";
	}

	public void setManovra(Posizione posizione, String manovra) {
		this.manovre.put(posizione, manovra);
		
	}

	public String getManovra(Posizione posizione) {
		
		return this.manovre.get(posizione);
	}

}
