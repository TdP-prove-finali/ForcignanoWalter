package model;

public class Arco {
	private Step partenza;
	private Step arrivo;
	private double distanza;
	private double tempoPercorrenza;

	public Arco(Step partenza, Step arrivo, double distanza,double tempoPercorrenza) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
		this.distanza = distanza;
		this.tempoPercorrenza=tempoPercorrenza;
	}

	public Step getPartenza() {
		return partenza;
	}

	public void setPartenza(Step partenza) {
		this.partenza = partenza;
	}

	public Step getArrivo() {
		return arrivo;
	}

	public void setArrivo(Step arrivo) {
		this.arrivo = arrivo;
	}

	public double getDistanza() {
		return distanza;
	}

	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}

	public double getTempoPercorrenza() {
		return tempoPercorrenza;
	}

	public void setTempoPercorrenza(double tempoPercorrenza) {
		this.tempoPercorrenza = tempoPercorrenza;
	}

	@Override
	public String toString() {
		return "\n partenza=" + partenza + ", arrivo=" + arrivo + ", distanza=" + distanza + ", tempoPercorrenza="
				+ tempoPercorrenza +"\n";
	}

	
	
}
