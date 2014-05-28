package squirrel.cli;

import java.io.File;
import java.io.IOException;

import squirrel.Driver;
import squirrel.common.Conf;
import squirrel.common.HotelMap;
import squirrel.common.Log;
import squirrel.parse.Query;
import squirrel.parse.Record;

import common.Database;
import common.FileSystem;

public class QueryHandler extends DefaultHandler {
	Integer hotelId;
	String aspect;
	String trait;
	Record record;
	
	public QueryHandler(String s) {
		super(s);
		super.checkArgs(2);
		
		String confArg = "";
		for (int i=1; i<args.length; i++) {
			if (args.equals(";") || args.equals(".")) {
				continue;
			}
			confArg += args[i];
		}

		String[] queryArgs = confArg.split("/");
		if (queryArgs.length != 3) {
			Log.cliArgError("Require 3 arguments for Query, seperately by '/' ");
		}
		
		hotelId = HotelMap.getHotelIdByName(queryArgs[0].trim());
		if (hotelId == null) {
			Log.hotelNotExistError("Hotel name does not exist in Database");
		}
		aspect = queryArgs[1].trim();
		trait = queryArgs[2].trim();
	}

	public void handle() {
		Query query = new Query(hotelId, aspect, trait);
		record = query.process(Database.getAllReviewSentences(hotelId));
	}

	public void emitResult() {
		//System.out.println("\n Sample out put! \n\n");
		String out = record.getPrettyText();
		System.out.println(out);

		try {
			if (Conf.record) {
				String filename = "output/" + record.getAspect() + "_" + record.getTrait() + ".txt";
				File f = FileSystem.createFile(filename);
				FileSystem.writeFile(f, out);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Record emitRecord() {
		return record;
	}
	
}