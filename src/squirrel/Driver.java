package squirrel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import common.Database;

import squirrel.cli.ArgsHandler;
import squirrel.cli.ConfHandler;
import squirrel.cli.DefaultHandler;
import squirrel.cli.ExitHandler;
import squirrel.cli.Handler;
import squirrel.cli.HelpHandler;
import squirrel.cli.QueryHandler;
import squirrel.cli.ResultHandler;
import squirrel.common.Conf;
import squirrel.common.ConfUtil;
import squirrel.err.CliArgNumException;
import squirrel.err.HotelNotExistException;
import squirrel.nlp.Sentence;
import squirrel.parse.Record;

/**
 * The Entry Point of system.
 * @author victor
 */
public class Driver {
	public static final String welcomeString = "  Welcome to Squirrel Review Query System!  ";
	
	//Hack !!!
	//public static List<Sentence> allReviewSents;
	public Record lastRecord;
	
	private void dispatchCommand(String s) {
		try {
			Handler handler;

			if (s.startsWith("exit") || s.startsWith("quit")) {
				handler = new ExitHandler(s);
			} else if (s.startsWith("set")) {
				handler = new ConfHandler(s);
			} else if (s.startsWith("view") || s.startsWith("show")) {
				handler = new ResultHandler(s, lastRecord);
			} else if (s.startsWith("help")) {
				handler = new HelpHandler(s);
			} else if (s.startsWith("conf") || s.startsWith("arg")) {
				handler = new ArgsHandler(s);
			} else if (s.startsWith("find") || s.startsWith("search") || s.startsWith("query")) {
				handler = new QueryHandler(s);
			}
			else {
				handler = new DefaultHandler(s);
			}

			handler.handle();
			handler.emitResult();
			Record newRecord = handler.emitRecord();
			if (newRecord != null) {
				lastRecord = newRecord;
			}
			System.out.println();
		}
		catch (CliArgNumException e) {
			System.out.println(e.getMessage());
			System.out.println();
		}
		catch (HotelNotExistException e) {
			System.out.println(e.getMessage());
			System.out.println();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		//allReviewSents = Database.getAllReviewSentences(Conf.hotelId);
		while (true) {
			try {
				System.out.print("squirrel> ");
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s = bufferRead.readLine().trim().toLowerCase();
				dispatchCommand(s);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void printSharp(int num) {
		for (int i=0; i<num; i++) {
			System.out.print("#");
		}
	}
	
	private static void printString(String s, int padding) {
		printSharp(padding);
		System.out.print(welcomeString);
		printSharp(padding);
		System.out.println();
	}
	
	private static void printString(int length, int padding) {
		printSharp(padding);
		for (int i=0; i<length; i++) {
			System.out.print(" ");
		}
		printSharp(padding);
		System.out.println();
	}
	
	private static void printWelcome() {
		int padding = 3;
		int fontLen = welcomeString.length();
		
		System.out.println();
		printSharp(fontLen+padding*2);
		System.out.println();
		
		printString(welcomeString.length(), padding);
		printString(welcomeString, padding);
		printString(welcomeString.length(), padding);
		
		printSharp(fontLen+padding*2);
		System.out.println();
		System.out.println();
	}
	
	public static void main(String[] args) {
		printWelcome();
		
		try {
			ConfUtil.loadConf();
			Database.OpenConn();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Driver driver = new Driver();
		driver.start();
	}

}