package model;

public class PosizionePiuPeso {
	private Posizione posizione;
	private double peso;
	public PosizionePiuPeso(Posizione posizione, double peso) {
		super();
		this.posizione = posizione;
		this.peso = peso;
	}
	public Posizione getPosizione() {
		return posizione;
	}
	public double getPeso() {
		return peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		PosizionePiuPeso other = (PosizionePiuPeso) obj;
		if (posizione == null) {
			if (other.posizione != null)
				return false;
		} else if (!posizione.equals(other.posizione))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return posizione + ", peso=" + peso ;
	}
	
	
	
	

}
