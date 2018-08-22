package model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model model = new Model();

		model.creaGrafo();
		for (Posizione p : model.getPosizioniMap().values()) {
			System.out.println(model.getStatiRaggiungibili(p).toString());
		}

	}

}
