package com.josuvladimir;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.josuvladimir.util.Util;

@SuppressWarnings("deprecation")
public class Main {

	public static final String ROOT_PATH 			= System.getProperty("user.dir");
	public static final String INPUT_FILE_PATH		= ROOT_PATH + "/res/input.txt";
	public static final String INPUT_DOCS_PATH		= ROOT_PATH + "/res/docs";
	public static final String INDEX_PATH 			= ROOT_PATH + "/index/";
	public static final String OUTPUT_PATH 			= ROOT_PATH + "/output/";
	public static final String STOP_WORDS_PATH 		= ROOT_PATH + "/res/stop_words.txt";
	private static String mSearchString;
//	private static File mOutputFile;
//	private static FileWriter mWriter;

	public static void main(String[] args) {
		try {
			init();
			long time = System.currentTimeMillis();
			index();
			Util.log("Index in " + String.valueOf(System.currentTimeMillis() - time) + " milis");
			search();
		} catch (IOException e) {
			Util.log("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void index() throws Exception {
		Indexer indexer = new Indexer(INDEX_PATH);
		indexer.index(INPUT_DOCS_PATH, null);
		IndexReader reader = indexer.mWriter.getReader();
		indexer.close();
		mSearchString = "eu";
	}

	private static void init() throws IOException {
//		File file = new File(INPUT_FILE_PATH);
//		FileInputStream inputStream;
//		inputStream = new FileInputStream(file);
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//		mOutputFile = new File(OUTPUT_PATH + "output.txt");
//		mWriter = new FileWriter(mOutputFile);
	}

	private static void search() throws IOException, ParseException {
		Directory directory = FSDirectory.open(new File(INDEX_PATH));
		IndexSearcher searcher = new IndexSearcher(directory);
		QueryParser parser = new QueryParser(Version.LUCENE_29, "contents", new RoAnalyzer(Version.LUCENE_29));
		Query query = parser.parse(mSearchString);
		Hits hits = searcher.search(query);
		if (hits.length() > 0) {
			for (int i = 0; i < hits.length(); i++) {
				Document document = hits.doc(i);
				Util.log(document.get("title"));
			}
		}
	}
}
