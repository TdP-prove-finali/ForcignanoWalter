package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.javadocmd.simplelatlng.LatLng;

import database.TaxiDAO;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;

public class Model {

	private TaxiDAO dao;
	private PercorsoIdMap percorsoIdMap;
	private StepIdMap stepIdMap;
	// private TaxiIdMap taxiIdMap;
	private Graph<Posizione, DefaultWeightedEdge> grafo;
	private boolean pesoTempo;
	private Map<LatLng, Posizione> posizioniMap;
	private List<PosizionePiuPeso> percorsoOttimale;

	private StringProperty numVertici;
	private StringProperty numArchi;

	private int numeroStep;
	private final int precisioneCoordinate;

	public Model() {
		super();
		pesoTempo = false;
		dao = new TaxiDAO();
		percorsoIdMap = new PercorsoIdMap();
		stepIdMap = new StepIdMap();

		// taxiIdMap = new TaxiIdMap();
		percorsoOttimale = new ArrayList<>();
		numeroStep = 200000;
		precisioneCoordinate=4;
		numVertici = new ReadOnlyStringWrapper();
		numArchi = new ReadOnlyStringWrapper();
		// int taxiDimension = dao.loadTaxi(taxiIdMap).size();
		int percorsiDimension = dao.loadAllPercorsi(percorsoIdMap).size();
		// il caricamento degli step in memoria dipende
		int stepsDimension = dao.loadAllStep(stepIdMap, numeroStep).size();

		// System.out.println(taxiDimension);
		System.out.println(percorsiDimension);
		System.out.println(stepsDimension);
		System.out.println("dimensione stepidmap:  " + stepIdMap.values().size());

		// System.out.println(taxiIdMap.values().size());
		// System.out.println(percorsoIdMap.values().size());
		// System.out.println(stepIdMap.values().size());

		// qui carico singolarmente step per step all'interno del percorso.
		// per ottimizzare potrei aggiungere tutti gli step in una sola volta.

		for (Step step : stepIdMap.values()) {
			this.percorsoIdMap.get(step.getPercorso_id()).loadStepInPercorso(step);
		}

	}
	/**
	 * Il metodo serve ad arrotondare un determinato numero ad una certa cifra dopo la virgola.
	 * @param numero
	 * @param nCifreDecimali
	 * @return
	 */
	public double arrotonda( double numero, int nCifreDecimali ){
	    return Math.round( numero * Math.pow( 10, nCifreDecimali ) )/Math.pow( 10, nCifreDecimali );
	}

	/**
	 * Metodo che ha lo scopo di creare il grafo, caricando tutti i vertici dati da
	 * oggetti Posizione e gli archi che avranno un peso in secondi o km a seconda
	 * del valore della variabile booleana pesoTempo. Se pesoTempo==true allora s,
	 * viceversa km.
	 */
	public void creaGrafo() {
		// bisogna definire quali sono i vertici e quali gli archi.
		// un unico grafo verrà generato con le posizioni come vertici.
		// come peso sarà la distanza.

		posizioniMap = new HashMap<>();
		grafo = new SimpleDirectedWeightedGraph<Posizione, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		for (Step s : this.stepIdMap.values()) {

			// stepIdMap.remove(s);

			String[] posizione = s.getStep_location_list().split(",");

			try {
				double latitude = arrotonda(Double.parseDouble(posizione[0]),precisioneCoordinate);
				double longitude = arrotonda(Double.parseDouble(posizione[1]),precisioneCoordinate);

				LatLng ll = new LatLng(latitude, longitude);
				String nomeLuogo = s.getStreet_for_each_step();
				// String manovra = s.getStep_direction();

				// System.out.println(ll.toString());
				if (!posizioniMap.containsKey(ll)) {
					posizioniMap.put(ll, new Posizione(ll, nomeLuogo,precisioneCoordinate));
				}

			} catch (NumberFormatException nfe) {
				throw new RuntimeException("Errore nella conversione dei dati.");
			}

		}
		// System.out.println("Numero di posizioni trovate: "+listaPosizioni.size()+"\n
		// posizioni trovate: "+ listaPosizioni.toString());

		Graphs.addAllVertices(grafo, posizioniMap.values());

		numVertici.setValue("" + grafo.vertexSet().size());

		System.out.println("Dimensione vertici del grafo: " + this.grafo.vertexSet().size());
		System.out.println("Dimensione stepId del grafo: " + stepIdMap.values().size());

		// da qui inizio ad aggingere gli archi.
		for (Percorso p : this.percorsoIdMap.values()) {

			if (!p.getListaStep().isEmpty()) {

				List<Step> steps = p.getListaStep();

				for (int i = 0; i < steps.size() - 1; i++) {

					Step partenza = stepIdMap.get(steps.get(i));
					Step arrivo = stepIdMap.get(steps.get(i + 1));
					String manovra = partenza.getStep_direction();

					Double distanza = partenza.getDistance_per_step();
					Double tempo = partenza.getTravel_time_per_step();

					String[] coordinatePartenza = partenza.getStep_location_list().split(",");
					String[] coordinateArrivo = arrivo.getStep_location_list().split(",");

					try {
						double latitudePartenza = arrotonda(Double.parseDouble(coordinatePartenza[0]),precisioneCoordinate);
						double longitudePartenza = arrotonda(Double.parseDouble(coordinatePartenza[1]),precisioneCoordinate);

						double latitudeArrivo = arrotonda(Double.parseDouble(coordinateArrivo[0]),precisioneCoordinate);
						double longitudeArrivo = arrotonda(Double.parseDouble(coordinateArrivo[1]),precisioneCoordinate);

						LatLng part = new LatLng(latitudePartenza, longitudePartenza);
						LatLng arr = new LatLng(latitudeArrivo, longitudeArrivo);

						// qui controllo che ci sia l'arco

						if (this.posizioniMap.containsKey(part) && this.posizioniMap.containsKey(arr)) {

							Posizione partenzaPosizione = this.posizioniMap.get(part);

							Posizione arrivoPosizione = this.posizioniMap.get(arr);

							if (!this.grafo.containsEdge(partenzaPosizione, arrivoPosizione)) {
								// se è già presente confronto altrimenti lo inserisco.
								// versione con peso solo la distanza.
								if (!partenzaPosizione.equals(arrivoPosizione)) {
									if (pesoTempo) {

										Graphs.addEdge(grafo, partenzaPosizione,
												arrivoPosizione, tempo);

										partenzaPosizione.setManovra(
												arrivoPosizione, manovra);

									} else {

										Graphs.addEdge(grafo, partenzaPosizione,
												arrivoPosizione, distanza);

										partenzaPosizione.setManovra(
												arrivoPosizione, manovra);
									}
								}

							} else {
								// se il peso trovato è minore di quello presente già nel grafo aggiorno il
								// grafo
								// System.out.println(this.grafo.getEdge(partenzaPosizione,
								// arrivoPosizione).toString());
								// System.out.println(this.grafo.getEdgeWeight(this.grafo.getEdge(partenzaPosizione,
								// arrivoPosizione)));
								if (!pesoTempo) {
									if (grafo.getEdgeWeight(
											grafo.getEdge(partenzaPosizione, arrivoPosizione)) > distanza) {

										// System.out.println(partenza.toString());
										// System.out.println(arrivo.toString());
										// System.out.println("Peso arco aggiornato " +partenzaPosizione.toString()+"
										// "+arrivoPosizione.toString()+"da
										// "+grafo.getEdgeWeight(grafo.getEdge(partenzaPosizione, arrivoPosizione))+" a
										// "+distanza);
										//
										partenzaPosizione.setManovra(
												arrivoPosizione, manovra);

										grafo.setEdgeWeight(grafo.getEdge(partenzaPosizione, arrivoPosizione),
												distanza);
									}
								} else {
									if (grafo
											.getEdgeWeight(grafo.getEdge(partenzaPosizione, arrivoPosizione)) > tempo) {
										grafo.setEdgeWeight(grafo.getEdge(partenzaPosizione, arrivoPosizione), tempo);

										partenzaPosizione.setManovra(
												arrivoPosizione, manovra);

									}

								}
							}
						} else {
							System.out.println("Posizione non presente sulla mappa.");
							// non dovrebbe mai finire qua, perchè tutte le posizione sono caricate
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
		numArchi.setValue("" + grafo.edgeSet().size());

	}

	/**
	 * Metodo che ha lo scopo di calcolare il percorso ottimale tra due posizioni,
	 * in particolare partenza e destinazione, andando a richiamare una funzione
	 * ricorsiva.
	 * 
	 * @param partenza
	 * @param destinazione
	 * @return
	 */
	public List<PosizionePiuPeso> calcolaPercorsoOttimale(Posizione partenza, Posizione destinazione) {
		/*
		 * Il parziale è una lista di posizioni. La soluzione Ottimale la trovo solo
		 * dopo aver cercato in tutto il grafo. Come una soluzione viene salvata per
		 * pensare che sia ottimale? semplicemente essendo il metodo recursive un void
		 * vaglio tutte le possibilità fino in fondo e salvo una possibile soluzione
		 * ottimale se arrivo a destinazione. ad ogni passo prendo un nodo vicino e
		 * proseguo in profondità. se arrivo a destinazione calcolo la somma dei pesi e
		 * vedo se è migliore di quella ottimale. Se non trovo nulla vuol dire che dal
		 * nodo di partenza non è possibile raggiungere il nodo di destinazione.
		 */

		DijkstraShortestPath<Posizione, DefaultWeightedEdge> dsp = new DijkstraShortestPath<Posizione, DefaultWeightedEdge>(
				grafo);

		GraphPath<Posizione, DefaultWeightedEdge> camminoOttimo = dsp.getPath(partenza, destinazione);

		if (camminoOttimo != null) {

			List<Posizione> posizioni = camminoOttimo.getVertexList();

			List<PosizionePiuPeso> list = new ArrayList<>();

			list.add(new PosizionePiuPeso(camminoOttimo.getStartVertex(), 0));

			int i;

			for (i = 1; i < posizioni.size(); i++) {

				list.add(new PosizionePiuPeso(posizioni.get(i),
						grafo.getEdgeWeight(camminoOttimo.getEdgeList().get(i - 1))));

			}

			percorsoOttimale = new ArrayList<>(list);

		} else {
			percorsoOttimale = new ArrayList<>();
		}
		return percorsoOttimale;

	}

	/**
	 * Metodo che dato un percorso parziale ne calcola il peso totale.
	 * 
	 * @param parziale
	 * @return
	 */
	public double calcolaPeso(List<PosizionePiuPeso> parziale) {
		double pesoTotale = 0;
		for (int i = 0; i < parziale.size(); i++) {
			pesoTotale += parziale.get(i).getPeso();
		}
		return pesoTotale;
	}

	/**
	 * Metodo che data una posizione di partenza restituisce tutte le possibili
	 * destinazioni.
	 * 
	 * @param partenza
	 * @return
	 */
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

	/**
	 * Metodo che restituisce una lista ordinata di posizioni. L'ordine è
	 * alfanumerico e in base a coordinate crescenti.
	 * 
	 * @return
	 */
	public Collection<Posizione> getPositionsList() {

		List<Posizione> listaPos = new ArrayList<>(posizioniMap.values());

		Collections.sort(listaPos, new Comparator<Posizione>() {

			@Override
			public int compare(Posizione o1, Posizione o2) {
				// TODO Auto-generated method stub
				if (o1.getNomeLuogo().compareTo(o2.getNomeLuogo()) == 0) {
					Double a = o1.getCoordinate().getLatitude();
					Double b = o2.getCoordinate().getLatitude();
					if (a.compareTo(b) != 0) {
						return a.compareTo(b);
					} else {
						Double c = o1.getCoordinate().getLongitude();
						Double d = o2.getCoordinate().getLongitude();
						return c.compareTo(d);
					}

				} else
					return o1.getNomeLuogo().compareTo(o2.getNomeLuogo());
			}
		});
		return listaPos;
	}

	public List<PosizionePiuPeso> getPercorsoOttimale() {
		return percorsoOttimale;
	}

	public TaxiDAO getDao() {
		return dao;
	}

	public boolean isPesoTempo() {
		return pesoTempo;
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

	public ReadOnlyStringProperty getNumVertici() {
		return numVertici;
	}

	public ReadOnlyStringProperty getNumArchi() {
		return numArchi;
	}

	public void changeWeight() {
		if (this.pesoTempo) {
			pesoTempo = false;
		} else {
			pesoTempo = true;
		}

	}

	public int getNumeroStep() {
		return numeroStep;
	}

	public void setNumeroStep(int numeroStep) {
		this.numeroStep = numeroStep;
	}

	public void caricaStep(int numeroStep) {

		this.numeroStep = numeroStep;
		stepIdMap = new StepIdMap();
		percorsoIdMap = new PercorsoIdMap();

		dao.loadAllPercorsi(percorsoIdMap);
		dao.loadAllStep(stepIdMap, this.numeroStep);

		for (Step step : stepIdMap.values()) {
			this.percorsoIdMap.get(step.getPercorso_id()).loadStepInPercorso(step);
		}

		System.out.println("dimensione stepidmap:  " + stepIdMap.values().size());

	}

}
