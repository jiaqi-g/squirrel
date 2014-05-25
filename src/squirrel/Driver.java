package squirrel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import squirrel.parse.Query;
import squirrel.parse.Record;

/**
 * The Entry Point of system.
 * @author victor
 */
public class Driver {
	
	public static final Integer hotelId = 93396;
	
	public void start() {
		while (true) {
			System.out.print("Enter Query as \"Aspect/Trait\" : ");
			try {
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s = bufferRead.readLine();
				String[] tmp = s.split("/");
				if (tmp.length != 2) {
					System.out.print("Error Input! \n");
					continue;
				} else {
					Query query = new Query(hotelId, tmp[0].trim(), tmp[1].trim());
					//System.out.println("Results: ");
					Record record = query.process();
					System.out.println(record.getPrettyText());
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Driver driver = new Driver();
		driver.start();
	}
}