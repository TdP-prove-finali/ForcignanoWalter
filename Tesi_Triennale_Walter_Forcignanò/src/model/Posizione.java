package model;

public class Posizione {
	
	private int latitudine;
	private int longitudine;
	
	private String nomeLuogo;

	public Posizione(int latitudine, int longitudine, String nomeLuogo) {
		super();
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.nomeLuogo = nomeLuogo;
	}

	public int getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(int latitudine) {
		this.latitudine = latitudine;
	}

	public int getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(int longitudine) {
		this.longitudine = longitudine;
	}

	public String getNomeLuogo() {
		return nomeLuogo;
	}

	public void setNomeLuogo(String nomeLuogo) {
		this.nomeLuogo = nomeLuogo;
	}

	@Override
	public String toString() {
		return "Posizione [latitudine=" + latitudine + ", longitudine=" + longitudine + ", nomeLuogo=" + nomeLuogo
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + latitudine;
		result = prime * result + longitudine;
		result = prime * result + ((nomeLuogo == null) ? 0 : nomeLuogo.hashCode());
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
		if (latitudine != other.latitudine)
			return false;
		if (longitudine != other.longitudine)
			return false;
		if (nomeLuogo == null) {
			if (other.nomeLuogo != null)
				return false;
		} else if (!nomeLuogo.equals(other.nomeLuogo))
			return false;
		return true;
	}
	
	
	

}
