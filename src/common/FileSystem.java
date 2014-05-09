package common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Abstraction of Unix file System, common utils for file access.
 * @author victor
 *
 */
public class FileSystem {
	
	public static void log(String s) {
		System.out.println("[FILE] " + s);
	}
	
	public static File getFile(String filename) {
		File file = new File(filename);
		return file;
	}
	
	/**
	 * create a new File
	 * 
	 * Warning: this method will delete existing file
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createNewFile(String fileName) throws IOException {
		File f = getFile(fileName);
		if (f.exists()) {
			f.delete();
			log(fileName + " deleted");  
		}
		f.createNewFile();
		log(fileName + " created");
		return f;
	}
	
	/**
	 * A simple wrapping method for scanner
	 * read all lines and trimmed
	 * 
	 * @param filename
	 * @throws IOException 
	 */
	public static List<String> readAllLines(String filename) throws IOException {
		log("read: " + filename);
		
		List<String> lst = new ArrayList<String>();
		try (Scanner scanner =  new Scanner(getFile(filename), StandardCharsets.UTF_8.name())){
			while (scanner.hasNextLine()){
				lst.add(scanner.nextLine().trim());
			}
		}
		return lst;
	}

	/**
	 * Read all files contained within a folder, map filename to lines
	 * 
	 * @param foldername
	 * @return
	 * @throws IOException
	 */
	public static List<File> getFilesInFolder(String foldername) throws IOException {
		List<File> res = new ArrayList<File>();
		File[] listOfFiles = getFile(foldername).listFiles();
		res.addAll(Arrays.asList(listOfFiles));
		
		return res;
	}
	
	public static void createFolder(String folderName) {
		File theDir = new File(folderName);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			log("creating directory: " + folderName);
			boolean result = theDir.mkdir();  

			if(result) {    
				log("DIR " + folderName + " created");  
			}
		}
	}

	

	/**
	 * Warning: it will create all folders along the filename path, and delete the original file
	 * 
	 * @param filename
	 * @return
	 */
	public static File createFile(String filename) throws IOException  {
		String[] prefixs = filename.split("/");
		String s = "";
		for (int i=0; i<prefixs.length-1; i++) {
			s += prefixs[i];
			createFolder(s);
			s += "/";
		}
		return createNewFile(filename);
	}
	
	/**
	 * a simple wrapping method for write to file and close it
	 * 
	 * @param aFile
	 * @param content
	 * @throws IOException
	 */
	public static void writeFile(File aFile, String content) throws IOException  {
		String fEncoding = "UTF-8";
		log("Writing to file named " + aFile.getPath() + ". Encoding: " + fEncoding);

		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(aFile), fEncoding));
		try {
			out.write(content);
		}
		finally {
			out.close();
		}
	}

	/** Requires two arguments - the file name, and the encoding to use.  */
	public static void main(String... aArgs) throws IOException {
		//String fileName = aArgs[0];
		//String encoding = aArgs[1];
		//FileSystem test = new FileSystem(fileName, encoding);
		//test.write();
		//test.read();
	}

}