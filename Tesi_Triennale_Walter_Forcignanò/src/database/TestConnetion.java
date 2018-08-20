package database;

import java.sql.Connection;

public class TestConnetion {

	public static void main(String[] args) {

		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Test PASSED");

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}
	}

}
