package model;

public class newRow {

	private String posizionePiuPeso;
	private Double peso;
	private String manovra;
	private String coordinate;

	public newRow(String posizionePiuPeso, Double peso, String manovra, String coordinate) {
		super();
		this.posizionePiuPeso = posizionePiuPeso;
		this.peso = peso;
		this.manovra = manovra;
		this.coordinate = coordinate;
	}

	public String getPosizionePiuPeso() {
		return posizionePiuPeso;
	}

	public Double getPeso() {
		return peso;
	}

	public String getManovra() {
		return manovra;
	}

	public String getCoordinate() {
		return coordinate;
	}
	
	

}
