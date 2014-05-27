package squirrel.cli;

import java.io.File;
import java.io.IOException;

import squirrel.common.Conf;
import squirrel.common.Log;
import squirrel.parse.Record;

import common.FileSystem;

public class QueryHandler extends DefaultHandler {
	Integer hotelId = Conf.hotelId;
	String aspect;
	String trait;
	Record record;
	
	public QueryHandler(String s) {
		super(s);
		super.checkArgs(2);

		String[] queryArgs = args[1].split("/");
		if (queryArgs.length != 2) {
			Log.cliArgError();
		}

		//TODO
		//hotelId = 
		aspect = queryArgs[0].trim();
		trait = queryArgs[1].trim();
	}

	public void handle() {
		/*
		Query query = new Query(hotelId, aspect, trait);
		record = query.process(allReviewSents);
		 */
	}

	public void emitResult() {
		System.out.println("\n Sample out put! \n\n");
		/*
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
		}*/
	}
}