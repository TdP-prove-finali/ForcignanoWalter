package database;



public class CostruzioneDB {
	
	public static void main(String[] args) {
		
		
	 CostruzioneTabellaPercorso file= new CostruzioneTabellaPercorso();
	
	 String nomeFile= "src\\it\\polito\\tdp\\Taxi_New_York\\db\\fastest_routes_test.csv";
	
	
	 try {
		file.caricaDati(nomeFile);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
		
		
	

	}

}
