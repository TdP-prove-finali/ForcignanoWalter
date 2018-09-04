package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CostruzioneTabellaPercorso {

	private TaxiDAO dao;

	public CostruzioneTabellaPercorso() {
		dao = new TaxiDAO();
	}

	public static void main(String[] args) {

		CostruzioneTabellaPercorso file = new CostruzioneTabellaPercorso();

		String nomeFile = "src\\it\\polito\\tdp\\Taxi_New_York\\db\\fastest_routes_test.csv";

		try {
			file.caricaDati(nomeFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void caricaDati(String nomeFile) throws Exception {

		// LETTURA FILE PER RIGA.
		// Creazione della tabella percorso.

		try {
			FileReader r = new FileReader(nomeFile);

			BufferedReader br = new BufferedReader(r); // legge per riga

			String s;
			int percorso_id = 0;
			int contatoreRiga = 0;

			while ((s = br.readLine()) != null) {
				contatoreRiga++;
				System.out.println(contatoreRiga);
				String array[] = s.split(","); // array che avra il numero di colonne della mia tabella

				if (contatoreRiga != 1) {

					try {
						// informazioni del percorso
						String id = array[0]; // id del taxi

						String starting_street = array[1];
						String end_street = array[2];

						double total_distance = Double.parseDouble(array[3]);

						double total_travel_time = Double.parseDouble(array[4]);

						int number_of_steps = Integer.parseInt(array[5]);

						if (dao.inserisciPercorsoInDB(percorso_id, id, starting_street, end_street, total_distance,
								total_travel_time, number_of_steps)) {
							// System.out.println("Percorso aggiunto"); } else {
							System.out.println("problema nel salvataggio percorso");
						}

						// aggiornamento paramtri identificativi
						percorso_id++;

					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						System.out.println(
								"NumberFormatException per la conversione di totale_distance o total_travel_time");
					}

				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Eccezione di I/O generica");

		}
	}

}