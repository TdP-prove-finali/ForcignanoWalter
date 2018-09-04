package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Percorso;
import model.PercorsoIdMap;
import model.Step;
import model.StepIdMap;
import model.Taxi;
import model.TaxiIdMap;

public class TaxiDAO {
	/**
	 * Il metodo fornito carica tutti i taxi presenti nel data-set all'interno della
	 * TaxiIdMap passata come parametro.
	 * 
	 * @param taxiIdMap
	 * @return
	 */
	public List<Taxi> loadTaxi(TaxiIdMap taxiIdMap) {

		List<Taxi> taxiList = new ArrayList<>();

		String sql = "select distinct taxi_id from percorso as p";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				taxiList.add(taxiIdMap.get(new Taxi(rs.getString("taxi_id"))));

				// System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"),
				// rs.getString("StateNme"));
			}

			conn.close();
			return taxiList;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

	}

	/**
	 * Carica tutti i percorsi all'interno della percorsoIdMap passata come parametro.
	 * 
	 * @param percorsoIdMap
	 */
	public List<Percorso> loadAllPercorsi(PercorsoIdMap percorsoIdMap) {

		List<Percorso> percorsi = new ArrayList<>();

		String sql = "select distinct percorso_id,taxi_id,starting_street,end_street,total_distance,total_travel_time,number_of_steps from percorso as p order by percorso_id";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				percorsi.add(percorsoIdMap.get(new Percorso(rs.getInt("percorso_id"), rs.getString("taxi_id"),
						rs.getString("starting_street"), rs.getString("end_street"), rs.getDouble("total_distance"),
						rs.getDouble("total_travel_time"), rs.getInt("number_of_steps"))));

			}

			conn.close();
			return percorsi;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

	}

	/**
	 * Metodo che carica tutti gli step all'interno della stepIdMap. Il numero di
	 * step caricati è pari a numeroStep.
	 * 
	 * @param stepIdMap
	 * @param numeroStep
	 * @return
	 */
	public List<Step> loadAllStep(StepIdMap stepIdMap, int numeroStep) {

		List<Step> steps = new ArrayList<>();

		String sql = "select step_id,taxi_id,percorso_id,street_for_each_step,travel_time_per_step,distance_per_step,step_maneuvers,step_direction,step_location_list "
				+ "from step order by percorso_id limit ?";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, numeroStep);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				steps.add(
						stepIdMap.get(new Step(rs.getString("taxi_id"), rs.getInt("percorso_id"), rs.getInt("step_id"),
								rs.getString("street_for_each_step"), rs.getDouble("travel_time_per_step"),
								rs.getDouble("distance_per_step"), rs.getString("step_maneuvers"),
								rs.getString("step_direction"), rs.getString("step_location_list"))));
			}

			conn.close();
			return steps;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

	}

	/**
	 * Metodo che ha lo scopo di inserire all'interno del data-set "taxi_routes",
	 * all'interno della tabella "step" tutte le informazioni relative allo step in
	 * questione.
	 * 
	 * @param taxi_id
	 * @param percorso_id
	 * @param len
	 * @param street_for_each_step
	 * @param distance_per_step
	 * @param travel_time_per_step
	 * @param step_maneuvers
	 * @param step_direction
	 * @param step_location_list
	 * @return
	 */

	public boolean inserisciStepInSistema(String taxi_id, int percorso_id, int len, List<String> street_for_each_step,
			List<String> distance_per_step, List<String> travel_time_per_step, List<String> step_maneuvers,
			List<String> step_direction, List<String> step_location_list) {

		String sql = "INSERT IGNORE INTO `taxi_routes`.`step` (`taxi_id`,`percorso_id`,`step_id`,`street_for_each_step` ,`travel_time_per_step` ,`distance_per_step` , `step_maneuvers`,`step_direction`,`step_location_list`) VALUES(?,?,?,?,?,?,?,?,?)";
		boolean returnValue = true;

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			for (int i = 0; i < len; i++) {
				st.setString(1, taxi_id);

				st.setInt(2, percorso_id);
				st.setInt(3, i);

				st.setString(4, street_for_each_step.get(i));
				st.setString(5, distance_per_step.get(i));
				st.setString(6, travel_time_per_step.get(i));
				st.setString(7, step_maneuvers.get(i));
				st.setString(8, step_direction.get(i));
				st.setString(9, step_location_list.get(i));

				int res = st.executeUpdate();

				if (res == 0)
					returnValue = false;
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}

		return returnValue;
	}

	/**
	 * Metodo che ha lo scopo di inserire all'interno del data-set "taxi_routes",
	 * all'interno della tabella "percorso" tutte le informazioni relative al
	 * particolare percorso.
	 * 
	 * @param percorso_id
	 * @param taxi_id
	 * @param starting_street
	 * @param end_street
	 * @param totale_distance
	 * @param total_travel_time
	 * @param number_of_steps
	 * @return
	 */
	public boolean inserisciPercorsoInDB(int percorso_id, String taxi_id, String starting_street, String end_street,
			double totale_distance, double total_travel_time, int number_of_steps) {
		String sql = "INSERT IGNORE INTO `taxi_routes`.`percorso` (`percorso_id`,`taxi_id`,`starting_street`,`end_street` ,`total_distance` , `total_travel_time`,`number_of_steps` ) VALUES(?,?,?,?,?,?,?)";
		boolean returnValue = false;

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, percorso_id);

			st.setString(2, taxi_id);
			st.setString(3, starting_street);
			st.setString(4, end_street);

			st.setDouble(5, totale_distance);
			st.setDouble(6, total_travel_time);

			st.setInt(7, number_of_steps);

			int res = st.executeUpdate();

			if (res == 1) // perchè restituisce false?? non capisco.
				returnValue = true;

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}

		return returnValue;
	}

}
