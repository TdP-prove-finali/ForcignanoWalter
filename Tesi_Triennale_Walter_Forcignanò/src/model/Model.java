package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.javadocmd.simplelatlng.LatLng;

import database.TaxiDAO;

public class Model {

	private TaxiDAO dao;
	private PercorsoIdMap percorsoIdMap;
	private StepIdMap stepIdMap;
	private TaxiIdMap taxiIdMap;
	private Graph<Posizione, DefaultWeightedEdge> grafo;
	private boolean pesoTempo;
	private Map<LatLng, Posizione> posizioniMap;
	private List<PosizionePiuPeso> percorsoOttimale;
	

	public List<PosizionePiuPeso> getPercorsoOttimale() {
		return percorsoOttimale;
	}

	public boolean isPesoTempo() {
		return pesoTempo;
	}

	public Collection<Posizione> getPositionsList() {
		List<Posizione> listaPos = new ArrayList<>(posizioniMap.values());
		Collections.sort(listaPos, new Comparator<Posizione>() {

			@Override
			public int compare(Posizione o1, Posizione o2) {
				// TODO Auto-generated method stub
				if (o1.getNomeLuogo().compareTo(o2.getNomeLuogo()) == 0) {
					Double a = o1.getPosizione().getLatitude();
					Double b = o2.getPosizione().getLatitude();
					if (a.compareTo(b) != 0) {
						return a.compareTo(b);
					} else {
						Double c = o1.getPosizione().getLongitude();
						Double d = o2.getPosizione().getLongitude();
						return c.compareTo(d);
					}

				} else
					return o1.getNomeLuogo().compareTo(o2.getNomeLuogo());
			}
		});
		return listaPos;
	}

	public Map<LatLng, Posizione> getPosizioniMap() {
		return posizioniMap;
	}

	public StepIdMap getStepIdMap() {
		return stepIdMap;
	}
	
	
	public PercorsoIdMap getPercorsoIdMap() {
		return percorsoIdMap;
	}

	public Graph<Posizione, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void changeWeight() {
		if(this.pesoTempo) {
			pesoTempo=false;
		}else{
			pesoTempo=true;
		}
		
	}

	public Model() {
		super();
		pesoTempo = false;
		dao = new TaxiDAO();
		percorsoIdMap = new PercorsoIdMap();
		stepIdMap = new StepIdMap();
		taxiIdMap = new TaxiIdMap();
		percorsoOttimale = new ArrayList<>();

		int taxiDimension = dao.loadTaxi(taxiIdMap).size();
		int percorsiDimension = dao.loadAllPercorsi(percorsoIdMap).size();
		int stepsDimension = dao.loadAllStep(stepIdMap).size();

		System.out.println(taxiDimension);
		System.out.println(percorsiDimension);
		System.out.println(stepsDimension);
		System.out.println("dimensione stepidmap:  " + stepIdMap.values().size());

		System.out.println(taxiIdMap.values().size());
		System.out.println(percorsoIdMap.values().size());
		System.out.println(stepIdMap.values().size());

		// qui carico singolarmente step per step all'interno del percorso.
		// per ottimizzare potrei aggiungere tutti gli step in una sola volta.

		for (Step step : stepIdMap.values()) {
			this.percorsoIdMap.get(step.getPercorso_id()).loadStepInPercorso(step);
		}

	}

	public void creaGrafo() {
		// bisogna definire quali sono i vertici e quali gli archi.
		// un unico grafo verr� generato con le posizioni come vertici.
		// come peso sar� la distanza.

		posizioniMap = new HashMap<>();

		for (Step s : this.stepIdMap.values()) {
			String[] posizione = s.getStep_location_list().split(",");

			try {
				double latitude = Double.parseDouble(posizione[0]);
				double longitude = Double.parseDouble(posizione[1]);

				LatLng ll = new LatLng(latitude, longitude);
				String nomeLuogo = s.getStreet_for_each_step();
				String manovra = s.getStep_direction();
				

				// System.out.println(ll.toString());
				if (!posizioniMap.containsKey(ll)) {
					posizioniMap.put(ll, new Posizione(ll, nomeLuogo, manovra));
				}

			} catch (NumberFormatException nfe) {
				throw new RuntimeException("Errore nella conversione dei dati.");
			}

		}
		// System.out.println("Numero di posizioni trovate: "+listaPosizioni.size()+"\n
		// posizioni trovate: "+ listaPosizioni.toString());

		grafo = new SimpleDirectedWeightedGraph<Posizione, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		Graphs.addAllVertices(grafo, posizioniMap.values());

		System.out.println("Dimensione del grafo: " + this.grafo.vertexSet().size());

		//da qui inizio ad aggingere gli archi.
		for (Percorso p : this.percorsoIdMap.values()) {

			if (!p.getListaStep().isEmpty()) {

				List<Step> steps = p.getListaStep();

				for (int i = 0; i < steps.size() - 1; i++) {

					Step partenza = stepIdMap.get(steps.get(i));
					Step arrivo = stepIdMap.get(steps.get(i + 1));

					Double distanza = partenza.getDistance_per_step();
					Double tempo = partenza.getTravel_time_per_step();

					String[] posizionePartenza = partenza.getStep_location_list().split(",");
					String[] posizioneArrivo = arrivo.getStep_location_list().split(",");
					
					

					try {
						double latitudePartenza = Double.parseDouble(posizionePartenza[0]);
						double longitudePartenza = Double.parseDouble(posizionePartenza[1]);

						double latitudeArrivo = Double.parseDouble(posizioneArrivo[0]);
						double longitudeArrivo = Double.parseDouble(posizioneArrivo[1]);

						LatLng part = new LatLng(latitudePartenza, longitudePartenza);
						LatLng arr = new LatLng(latitudeArrivo, longitudeArrivo);

						// new Posizione(part, partenza.getStreet_for_each_step());
						// Posizione arrivoPosizione = new Posizione(arr,
						// arrivo.getStreet_for_each_step());

						// System.out.println(part+" "+arr);
						// qui controllo che ci sia l'arco

						if (this.posizioniMap.containsKey(part) && this.posizioniMap.containsKey(arr)) {

							Posizione partenzaPosizione = this.posizioniMap.get(part);
							Posizione arrivoPosizione = this.posizioniMap.get(arr);

							if (!this.grafo.containsEdge(partenzaPosizione, arrivoPosizione)) {
								// se � gi� presente confronto altrimenti lo inserisco.
								// versione con peso solo la distanza.
								if (!partenzaPosizione.equals(arrivoPosizione)) {
									if (pesoTempo) {
//										if (tempo != 0) {
											Graphs.addEdge(grafo,
													this.posizioniMap.get(partenzaPosizione.getPosizione()),
													this.posizioniMap.get(arrivoPosizione.getPosizione()), tempo);

//										}
									} else {
//										if (distanza != 0) {
											Graphs.addEdge(grafo,
													this.posizioniMap.get(partenzaPosizione.getPosizione()),
													this.posizioniMap.get(arrivoPosizione.getPosizione()), distanza);
//										}
									}
								}

							} else {
								// se il peso trovato � minore di quello presente gi� nel grafo aggiorno la mia
								// mappa.
								if (grafo.getEdgeWeight(grafo.getEdge(partenzaPosizione, arrivoPosizione)) > partenza
										.getDistance_per_step() && partenza.getDistance_per_step() != 0) {
									grafo.setEdgeWeight(grafo.getEdge(partenzaPosizione, arrivoPosizione),
											partenza.getDistance_per_step());
								}
							}
						} else {
							System.out.println("Posizione non presente sulla mappa.");
							// non dovrebbe mai finire qua, perch� tutte le posizione sono caricate
							// all'avvio.
						}

					} catch (NumberFormatException nfe) {
						throw new RuntimeException("Errore nella conversione dei dati.");
					}
				}

			}
		}

		System.out.println("dimensione archi nel grafo:" + grafo.edgeSet().size());
		// System.out.println("archi:");
		// for (DefaultWeightedEdge d : grafo.edgeSet()) {
		// System.out.println(d.toString() + "----- " + grafo.getEdgeWeight(d));
		//
		// }

	}

	public List<PosizionePiuPeso> calcolaPercorsoOttimale(Posizione partenza, Posizione destinazione) {
		List<PosizionePiuPeso> parziale = new ArrayList<>();
		percorsoOttimale= new ArrayList<>();
		/*
		 * Il parziale � una lista di posizioni. La soluzione Ottimale la trovo solo
		 * dopo aver cercato in tutto il grafo. Come una soluzione viene salvata per
		 * pensare che sia ottimale? semplicemente essendo il metodo recursive un void
		 * vaglio tutte le possibilit� fino in fondo e salvo una possibile soluzione
		 * ottimale se arrivo a destinazione. ad ogni passo prendo un nodo vicino e
		 * proseguo in profondit�. se arrivo a destinazione calcolo la somma dei pesi e
		 * vedo se � migliore di quella ottimale. Se non trovo nulla vuol dire che dal
		 * nodo di partenza non � possibile raggiungere il nodo di destinazione.
		 */

		parziale.add(new PosizionePiuPeso(posizioniMap.get(partenza.getPosizione()), 0));
		recursive(parziale, posizioniMap.get(destinazione.getPosizione()));

		return percorsoOttimale;
	}

	private void recursive(List<PosizionePiuPeso> parziale, Posizione destinazione) {

		// se � ottima salvo
		if (parziale.get(parziale.size() - 1).getPosizione().equals(destinazione)) {
			// sono arrivato a destinazione
			// allora calcolo il valore della soluzione
			if (calcolaPeso(parziale) < calcolaPeso(this.percorsoOttimale) || this.percorsoOttimale.isEmpty()) {
				percorsoOttimale = new ArrayList<>(parziale);
			}

		}

		PosizionePiuPeso corrente = parziale.get(parziale.size() - 1);
		// List<Posizione> successori=Graphs.successorListOf(grafo, corrente);

		Set<DefaultWeightedEdge> successori = grafo.outgoingEdgesOf(corrente.getPosizione());

		for (DefaultWeightedEdge arco : successori) {
			// creo un nuovo oggetto che oltre alla posizione ha il peso dell'arco.
			// in questo modo risulta facile calcolare il valore del peso totale di un
			// percorso.
			Posizione successore = Graphs.getOppositeVertex(grafo, arco, corrente.getPosizione());
			double peso = grafo.getEdgeWeight(arco);
			PosizionePiuPeso successivo = new PosizionePiuPeso(successore, peso);

			if (!parziale.contains(successivo)) {
				parziale.add(successivo);
				recursive(parziale, destinazione);
				parziale.remove(parziale.size() - 1);
			}
		}
	}

	public double calcolaPeso(List<PosizionePiuPeso> parziale) {
		double pesoTotale = 0;
		for (int i = 0; i < parziale.size(); i++) {
			pesoTotale += parziale.get(i).getPeso();
		}
		return pesoTotale;
	}

	public List<Posizione> getStatiRaggiungibili(Posizione partenza) {

		BreadthFirstIterator<Posizione, DefaultWeightedEdge> bfv = new BreadthFirstIterator<Posizione, DefaultWeightedEdge>(
				grafo, partenza);

		List<Posizione> raggiungibili = new ArrayList<Posizione>();
		bfv.next(); // non voglio salvare il primo elemento
		while (bfv.hasNext()) {
			raggiungibili.add(bfv.next());
		}
		return raggiungibili;
	}

	

}
