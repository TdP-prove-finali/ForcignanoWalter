package database;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;

public class CostruzioneTabellaStep {

	public static void main(String[] args) {
		
		//creazione tabella step.
		
		TaxiDAO dao = new TaxiDAO();

	//	String csvFile = "rsc/test.csv";
		String csvFile ="src\\it\\polito\\tdp\\Taxi_New_York\\db\\fastest_routes_test.csv";

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;

			int percorso_id = 0;
			String taxi_id = "";

			List<String> street_for_each_step = new ArrayList<>();
			List<String> distance_per_step = new ArrayList<>();
			List<String> travel_time_per_step = new ArrayList<>();
			List<String> step_maneuvers = new ArrayList<>();
			List<String> step_direction = new ArrayList<>();
			List<String> step_location_list = new ArrayList<>();

			while ((line = reader.readNext()) != null) {
				int len = -1;
				int campo = -1;

				for (String field : line) {

					campo++;
					if (!field.isEmpty()) {
						if (!field.contains("|")) {
							if (campo == 0) {
								taxi_id = field;
							//	System.out.println(taxi_id);
							}
						//	System.out.println(field);
							//eventualmente per caricare i dati del percorso posso prima raccoglierli tutti fino al campo=5 e poi inviare i dati.
						} else {
							List<String> splits = new ArrayList<String>(Arrays.asList(field.split("\\|")));
							if (len != -1 && len != splits.size()) {
								System.err.println("Errore. Dati non consistenti!!");
								return;
							}
							if (len == -1) {
								// aggiornamento della lunghezza.
								len = splits.size();
							//	System.out.format("-- ARRAYS len %d ---\n", len);
							}
							if (splits.size() > 0 && splits.get(0).contains(",")) {
							if(campo==11) {
								List<List<String>> coordinates = new ArrayList<>();
								for (String split : splits) {
									coordinates.add(Arrays.asList(split.split(",")));	
								}
								// qui ho l'array di coordinate
								step_location_list = splits;
								/*	//stampa equivalente ad aggiornamento tabella
								for (int i = 0; i < len; i++) {
									System.out.println("Taxi_id: " + taxi_id + " percorso_id: " + percorso_id
											+ " street_for_each_step: " + street_for_each_step.get(i)
											+ " distance_per_step: " + distance_per_step.get(i)
											+ " travel_time_per_step :" + travel_time_per_step.get(i)
											+ " step_maneuvers:" + step_maneuvers.get(i) + " step_direction : "
											+ step_direction.get(i) + " step_location_list:"
											+ step_location_list.get(i));

								}
								*/
								//dopo che ho tutte le liste aggiorno
								
								dao.inserisciStepInSistema(taxi_id, percorso_id, len, street_for_each_step,
										distance_per_step, travel_time_per_step, step_maneuvers,
										step_direction, step_location_list);
							}
								
								
							//	System.out.println(coordinates);
							} else {
								// negli splits ci sono tutte i miei dati in formato di lista.
								// come faccio ad inserire questi dati all'interno del mio DB??
								// qui inserisco gli step.
								int step_id = 0;

								if (campo >= 6) {

									if (campo == 6) {
										street_for_each_step = splits;
									}
									if (campo == 7) {
										distance_per_step = splits;

									}
									if (campo == 8) {
										travel_time_per_step = splits;

									}
									if (campo == 9) {
										step_maneuvers = splits;

									}
									if (campo == 10) {
										step_direction = splits;
									}

								}

							}
							//System.out.println(splits);
						}
					}

				}
				percorso_id++;
			}
			System.out.println(" ");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
