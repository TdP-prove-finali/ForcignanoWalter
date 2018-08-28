package model;

import com.javadocmd.simplelatlng.LatLng;

public class Posizione {

	private LatLng posizione;

	private String nomeLuogo;
	private String manovra;

	public Posizione(LatLng posizione, String nomeLuogo) {
		super();
		this.posizione = posizione;
		this.nomeLuogo = nomeLuogo;
	}

	public LatLng getCoordinate() {
		return posizione;
	}

	public void setCoordinate(LatLng posizione) {
		this.posizione = posizione;
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
		result = prime * result + ((posizione == null) ? 0 : posizione.hashCode());
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
		if (posizione == null) {
			if (other.posizione != null)
				return false;
		} else if (!posizione.equals(other.posizione))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nomeLuogo + " " + posizione.toString();
	}

}
