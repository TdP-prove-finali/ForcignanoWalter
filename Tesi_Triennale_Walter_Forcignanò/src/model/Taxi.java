package model;

import java.util.ArrayList;
import java.util.List;

public class Taxi {
	private String id;

	private List<List<Posizione>> listaPercorsi;

//	private Graph<Step, DefaultWeightedEdge> grafo;

	public Taxi(String id) {
		super();
		this.id = id;
//		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.listaPercorsi = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public List<List<Posizione>> getListaPercorsi() {
		return listaPercorsi;
	}

//	public Graph<Step, DefaultWeightedEdge> getGrafo() {
//		return grafo;
//	}
//
//	public void loadGrafo(List<Percorso> percorsiTaxi, List<Step> listStep) {
//		Graphs.addAllVertices(grafo, listStep);
//
//		for (Percorso p : percorsiTaxi) {
//
//			for (Step c : p.getListaStep()) {
//				for (Step d : p.getListaStep()) {
//					// come faccio a direzionare l'arco??
//					if (!c.equals(d) && !grafo.containsEdge(c, d) && grafo.containsVertex(c)
//							&& grafo.containsVertex(d)) {
//						grafo.addEdge(c, d);
//					}
//				}
//
//			}
//
//		}
//
//		/*
//		 * Se i vertici del mio grafo sono gli step, cosa rappresentano gli archi? -Il
//		 * fatto che appartengano allo stesso percorso. Quindi due step collegati per il
//		 * semplice fatto di essere all'interno dello stesso percorso fa sì che ci sia
//		 * un arco
//		 */
//
//		
//	}

}
