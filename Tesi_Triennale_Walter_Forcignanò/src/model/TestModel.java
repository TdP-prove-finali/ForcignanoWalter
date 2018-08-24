package model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model model = new Model();

		model.creaVerticiGrafo();
		for (Posizione p : model.getPosizioniMap().values()) {
			//System.out.println(model.getStatiRaggiungibili(p).toString());
			if(!model.getStatiRaggiungibili(p).isEmpty()&& model.getStatiRaggiungibili(p).size()==6)
			System.out.println(model.calcolaPercorsoOttimale(p, model.getStatiRaggiungibili(p).get(5)).toString() + "----" + model.calcolaPeso(model.getPercorsoOttimale()));
			
		}

	}
	

}
