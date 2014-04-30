package ucla.tripadvisor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneUtil {

	public static final String indexPath = "lucene_index";
	public static final String docsPath = "docs";
	IndexReader reader;

	public IndexReader getIndexReader() throws Exception {
		if (reader == null) {
			reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		}
		return reader;
	}

	public int findNumOfDocumentContains(String term) throws Exception {
		Term t  = new Term("content", term);
		return getIndexReader().docFreq(t);
	}

	private static void indexDocs(IndexWriter writer, File file) throws Exception {
		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				// an IO error could occur
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException fnfe) {
					// at least on windows, some temporary files raise this exception with an "access denied" message
					// checking if the file can be read doesn't help
					return;
				}

				try {
					Document doc = new Document();

					Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
					doc.add(pathField);
					doc.add(new LongField("modified", file.lastModified(), Field.Store.NO));
					doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))));

					if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
						System.out.println("adding " + file);
						writer.addDocument(doc);
					} else {
						System.out.println("updating " + file);
						writer.updateDocument(new Term("path", file.getPath()), doc);
					}
				} finally {
					fis.close();
				}
			}
		}
	}

	private static void buildIndex() throws Exception {

		Directory dir = FSDirectory.open(new File(indexPath));
		final File docDir = new File(docsPath);

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);

		boolean create = true;

		if (create) {
			iwc.setOpenMode(OpenMode.CREATE);
		} else {
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}

		IndexWriter writer = new IndexWriter(dir, iwc);
		indexDocs(writer, docDir);

		writer.close();
	}

	public static void main(String[] args) throws Exception {
		buildIndex();
	}
}
