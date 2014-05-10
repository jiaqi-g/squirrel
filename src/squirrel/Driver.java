package squirrel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import squirrel.common.DumpTextUtil;
import squirrel.common.ReviewUtil;
import squirrel.common.SynonymsUtil;
import squirrel.parse.Query;
import squirrel.parse.Record;

/**
 * The Driver of the system.
 * 
 * @author victor
 *
 */
public class Driver {
	
	public Driver() {
		try {
			Class.forName(ReviewUtil.class.getCanonicalName());
			Class.forName(SynonymsUtil.class.getCanonicalName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
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
					Query query = new Query(tmp[0].trim(), tmp[1].trim());
					//System.out.println("Results: ");
					List<Record> res = query.process();
					for (int i = 0; i < res.size(); i++) {
						Record record = res.get(i);
						System.out.println("Query" + (i+1) + ": " + record.getAspect() + "/" + record.getTrait());
						System.out.println(record.getPrettyText());
					}
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
		//DumpTextUtil.dumpReviewsToFile("reviews/first_1000_text.txt");
		//DumpTextUtil.dumpReviewsToFolder("reviews");
	}
}