package model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model model = new Model();

		System.out.println(model.getStepIdMap().values());
		System.out.println(model.getPercorsoIdMap().get(0).getListaStep().toString());
		model.creaGrafo();
System.out.println(model.getGrafo().edgeSet().toString());
for (Posizione p : model.getPosizioniMap().values()) {
			if ((!model.getStatiRaggiungibili(p).isEmpty())) {
				System.out.println(model.getStatiRaggiungibili(p).toString());
//				if (model.getStatiRaggiungibili(p).size() == 6) {
//					System.out
//							.println(model.calcolaPercorsoOttimale(p, model.getStatiRaggiungibili(p).get(5)).toString()
//									+ "----" + model.calcolaPeso(model.getPercorsoOttimale()));
//				}
			}
		}

	}

}
