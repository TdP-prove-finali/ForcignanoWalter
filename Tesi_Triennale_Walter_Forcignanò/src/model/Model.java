package model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;

import database.TaxiDAO;



public class Model {

	private TaxiDAO dao;
	private PercorsoIdMap percorsoIdMap;
	private StepIdMap stepIdMap;
	private TaxiIdMap taxiIdMap;
	private Graph<LatLng, DefaultWeightedEdge> grafo;
	private boolean pesoTempo;
	int j = 0;

	public Model() {
		super();
		pesoTempo = true;
		dao = new TaxiDAO();
		percorsoIdMap = new PercorsoIdMap();
		stepIdMap = new StepIdMap();
		taxiIdMap = new TaxiIdMap();

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

		for (Step step : stepIdMap.values()) {
			this.percorsoIdMap.get(step.getPercorso_id()).loadStepInPercorso(step);
		}

	}

	public void creaGrafo() {
		// bisogna definire quali sono i vertici e quali gli archi.
		// un unico grafo verrà generato con le posizioni come vertici.
		// come peso sarà la distanza.

		ArrayList<LatLng> listaPosizioni = new ArrayList<>();

		for (Step s : this.stepIdMap.values()) {
			String[] posizione = s.getStep_location_list().split(",");

			try {
				double latitude = Double.parseDouble(posizione[0]);
				double longitude = Double.parseDouble(posizione[1]);

				LatLng ll = new LatLng(latitude, longitude);
				// System.out.println(ll.toString());
				if (!listaPosizioni.contains(ll)) {
					listaPosizioni.add(ll);
				}
			} catch (NumberFormatException nfe) {
				throw new RuntimeException("Errore nella conversione dei dati.");
			}

		}
		// System.out.println("Numero di posizioni trovate: "+listaPosizioni.size()+"\n
		// posizioni trovate: "+ listaPosizioni.toString());

		grafo = new SimpleDirectedWeightedGraph<LatLng, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		Graphs.addAllVertices(grafo, listaPosizioni);

		System.out.println("Dimensione del grafo: " + this.grafo.vertexSet().size());

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

						// System.out.println(part+" "+arr);
						// qui controllo che ci sia il peso dell'arco
						if (!this.grafo.containsEdge(part, arr)) {
							// se è già presente confronto altrimenti lo inserisco.
							// versione con peso solo la distanza.
							Graphs.addEdge(grafo, part, arr, distanza);
						} else {
							// se il peso trovato è minore di quello presente già nel grafo aggiorno la mia
							// mappa.
							if (grafo.getEdgeWeight(grafo.getEdge(part, arr)) > partenza.getDistance_per_step()) {
								grafo.setEdgeWeight(grafo.getEdge(part, arr), partenza.getDistance_per_step());
							}
						}

					} catch (NumberFormatException nfe) {
						throw new RuntimeException("Errore nella conversione dei dati.");
					}
				}

			}
		}

		System.out.println("dimensione archi nel grafo:" + grafo.edgeSet().size());
//		System.out.println(j);

	}

}
