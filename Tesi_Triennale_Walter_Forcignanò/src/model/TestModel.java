package model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model model = new Model();
		

		//caricare solo 7 step in modo da vedere che le funzioni riportano risultati corretti, confrontandoli con le tabelle.
		for(Step s : model.getStepIdMap().values()) {
			System.out.println(s.toString());
		}
		System.out.println("\npercorso \n" + model.getPercorsoIdMap().get(0).getListaStep().toString());
		
		model.creaGrafo();
		
		
		System.out.println(model.getGrafo().edgeSet().toString());
		
		for (Posizione p : model.getPosizioniMap().values()) {
			if ((!model.getStatiRaggiungibili(p).isEmpty())) {
				System.out.println("\n Gli stati raggiungibili da "+p.toString()+" sono \n"+model.getStatiRaggiungibili(p).toString()+"\n");
				
				 System.out
				 .println("Percorso Ottimale da "+ p.toString() + " a "+model.getStatiRaggiungibili(p).get(model.getStatiRaggiungibili(p).size()-1).toString()+" è \n"+model.calcolaPercorsoOttimale(p,
				 model.getStatiRaggiungibili(p).get( model.getStatiRaggiungibili(p).size()-1)).toString()
				 + "----" + model.calcolaPeso(model.getPercorsoOttimale()));
				 
			}
		}

	}

}
