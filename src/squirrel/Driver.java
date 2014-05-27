package squirrel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import common.Database;
import common.FileSystem;

import squirrel.common.Conf;
import squirrel.common.ConfUtil;
import squirrel.common.Log;
import squirrel.parse.Query;
import squirrel.parse.Record;

/**
 * The Entry Point of system.
 * @author victor
 */
public class Driver {

	public void start() {
		while (true) {
			System.out.print("Enter Query as \"Aspect/Trait\" : ");
			try {
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s = bufferRead.readLine().trim().toLowerCase();
				if (s.startsWith("exit") || s.startsWith("quit")) {
					System.exit(0);
				}
				
				String[] tmp = s.split("/");
				if (tmp.length != 2) {
					Log.warn("Error Input!\n");
					continue;
				} else {
					Query query = new Query(Conf.hotelId, tmp[0].trim(), tmp[1].trim());
					Record record = query.process();
					emitResult(record);
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void emitResult(Record record) throws IOException {
		String out = record.getPrettyText();
		System.out.println(out);
		
		if (Conf.record) {
			String filename = "output/" + record.getAspect() + "_" + record.getTrait() + ".txt";
			File f = FileSystem.createFile(filename);
			FileSystem.writeFile(f, out);
		}
	}

	public static void main(String[] args) {
		/**
		 * TODO:
		 * 1. add time count support
		 * 2. review/sentence mode change in config
		 */
		
		/*
		if (args.length > 0 && args[0].trim().toLowerCase().equals("debug")) {
			Conf.debug = true;
		}*/
		
		try {
			ConfUtil.loadConf();
			Database.OpenConn();
			if (Conf.debug) {
				ConfUtil.printArgs();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Driver driver = new Driver();
		driver.start();
	}
	
}