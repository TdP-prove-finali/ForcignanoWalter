package model;

import com.javadocmd.simplelatlng.LatLng;

public class Posizione {

	private LatLng posizione;

	private String nomeLuogo;

	public Posizione(LatLng posizione, String nomeLuogo) {
		super();
		this.posizione = posizione;
		this.nomeLuogo = nomeLuogo;
	}

	public LatLng getPosizione() {
		return posizione;
	}

	public void setPosizione(LatLng posizione) {
		this.posizione = posizione;
	}

	public String getNomeLuogo() {
		return nomeLuogo;
	}

	public void setNomeLuogo(String nomeLuogo) {
		this.nomeLuogo = nomeLuogo;
	}

	@Override
	public String toString() {
		return nomeLuogo+" "+posizione.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeLuogo == null) ? 0 : nomeLuogo.hashCode());
		result = prime * result + ((posizione == null) ? 0 : posizione.hashCode());
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
		Posizione other = (Posizione) obj;
		if (nomeLuogo == null) {
			if (other.nomeLuogo != null)
				return false;
		} else if (!nomeLuogo.equals(other.nomeLuogo))
			return false;
		if (posizione == null) {
			if (other.posizione != null)
				return false;
		} else if (!posizione.equals(other.posizione))
			return false;
		return true;
	}
	
	

}
