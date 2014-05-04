package ucla.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** 
 * Read and write a file using an explicit encoding.
 * Removing the encoding from this code will simply cause the 
 * system's default encoding to be used instead.  
 */
public class FileUtil {

	private final String fFileName;
	private final String fEncoding;
	//private final String FIXED_TEXT = "But soft! what code in yonder program breaks?";

	/**
	 * a simple wrapping method for scanner
	 * we have trimmed every line
	 * 
	 * @param filename
	 * @throws IOException 
	 */
	public static List<String> readByLines(String filename) throws IOException {
		System.out.println("Read: " + filename);
		List<String> lst = new ArrayList<String>();
		Path path = Paths.get(filename);

		try (Scanner scanner =  new Scanner(path, StandardCharsets.UTF_8.name())){
			while (scanner.hasNextLine()){
				lst.add(scanner.nextLine().trim());
			}
		}

		return lst;
	}

	/**
	 * read all files contained within a folder, map with filename to filecontent
	 * 
	 * @param foldername
	 * @return
	 * @throws IOException
	 */
	public static Map<String, List<String>> readFolder(String foldername) throws IOException {
		Map<String, List<String>> res = new HashMap<String, List<String>>();

		File folder = new File(foldername);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			//System.out.println("File " + listOfFiles[i].getCanonicalPath());
			String filename = listOfFiles[i].getName();
			res.put(filename, readByLines(foldername + "/" + filename));
		}

		return res;
	}
	
	public static void createFolder(String folderName) {
		File theDir = new File(folderName);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + folderName);
			boolean result = theDir.mkdir();  

			if(result) {    
				System.out.println("DIR " + folderName + " created");  
			}
		}
	}

	public static File deleteAndCreateNewFile(String fileName) throws IOException {
		File reviewFile = new File(fileName);
		if (reviewFile.exists()) {
			reviewFile.delete();
		}
		reviewFile.createNewFile();
		return reviewFile;
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



	Writer out;

	public FileUtil(String aFileName, String aEncoding){
		fEncoding = aEncoding;
		fFileName = aFileName;
	}

	public void create() throws IOException {
		File file = new File(fFileName);
		file.createNewFile();
	}

	/**
	 * Use buffered writer to write to file.
	 * Warning: use close() to close the file.
	 * 
	 * @param content
	 * @throws Exception
	 */
	public void write(String content) throws Exception {
		if (out == null) {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fFileName), fEncoding));
		}
		out.write(content);
	}

	public void close() throws Exception {
		out.close();
	}

	/** Read the contents of the given file. */
	void read() throws IOException {
		log("Reading from file.");
		StringBuilder text = new StringBuilder();
		String NL = System.getProperty("line.separator");
		Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
		try {
			while (scanner.hasNextLine()){
				text.append(scanner.nextLine() + NL);
			}
		}
		finally{
			scanner.close();
		}
		log("Text read in: " + text);
	}

	private static void log(String aMessage){
		System.out.println(aMessage);
	}

	/** Requires two arguments - the file name, and the encoding to use.  */
	public static void main(String... aArgs) throws IOException {
		String fileName = aArgs[0];
		String encoding = aArgs[1];
		FileUtil test = new FileUtil(fileName, encoding);
		//test.write();
		test.read();
	}

}