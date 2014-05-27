package squirrel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import squirrel.cli.CliArgNumException;
import squirrel.cli.ConfHandler;
import squirrel.cli.DefaultHandler;
import squirrel.cli.ExitHandler;
import squirrel.cli.Handler;
import squirrel.cli.HelpHandler;
import squirrel.cli.QueryHandler;
import squirrel.cli.ResultHandler;

/**
 * The Entry Point of system.
 * @author victor
 */
public class Driver {
	public static final String welcomeString = "  Welcome to Squirrel Review Query System!  ";

	private void dispatchCommand(String s) {
		try {
			Handler handler;

			if (s.startsWith("exit") || s.startsWith("quit")) {
				handler = new ExitHandler(s);
			} else if (s.startsWith("set")) {
				handler = new ConfHandler(s);
			} else if (s.startsWith("view")) {
				handler = new ResultHandler(s);
			} else if (s.startsWith("help")) {
				handler = new HelpHandler(s);
			} else if (s.startsWith("s") || s.startsWith("q") || s.startsWith("find")) {
				handler = new QueryHandler(s);
			}
			else {
				handler = new DefaultHandler(s);
			}

			handler.handle();
			handler.emitResult();
			System.out.println();
		}
		catch (CliArgNumException e) {
			System.out.println("Argument Number or Format mismatch!\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		//List<Sentence> allReviewSents = Database.getAllReviewSentences(Conf.hotelId);
		while (true) {
			try {
				System.out.print("> ");
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
		/**
		 * TODO: review/sentence mode change in config
		 */
		printWelcome();
		
		/*
		if (args.length > 0 && args[0].trim().toLowerCase().equals("debug")) {
			Conf.debug = true;
		}*/

		/*
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
		}*/

		Driver driver = new Driver();
		driver.start();
	}

}