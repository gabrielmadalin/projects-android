package com.josuvladimir;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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


@SuppressWarnings("deprecation")
public class Main {

	public static final String ROOT_PATH 			= System.getProperty("user.dir");
	public static final String INPUT_FILE_PATH		= ROOT_PATH + "/res/input.txt";
	public static final String INPUT_DOCS_PATH		= ROOT_PATH + "/res/docs";
	public static final String INDEX_PATH 			= ROOT_PATH + "/index/";
	public static final String OUTPUT_PATH 			= ROOT_PATH + "/output/";
	public static final String STOP_WORDS_PATH 		= ROOT_PATH + "/res/stop_words.txt";
	public static final String CONTENT 				= "contents";
	public static final String FILENAME 			= "filename";
	public static final String FULLPATH 			= "fullpath";
	public static final String FIRST_LINE 			= "first_line";
	public static final String TITLE 				= "title";
	public static final String DATE 				= "date";
    public static String Language = "Romanian";
	private static String mSearchString;
	private static Scanner mScanner;

	public static void main(String[] args) {
		try {
			init();
			long time = System.currentTimeMillis();
			index();
			Util.log("Index in " + String.valueOf(System.currentTimeMillis() - time) + " milis");
			while (getSearchString().length() > 0) {
				search();
			}
			mScanner.close();
			Util.log("Exit");
		} catch (IOException e) {
			Util.log("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void init() {
		mScanner = new Scanner(System.in);
	}

	@SuppressWarnings("unused")
	private static void index() throws Exception {
		Indexer indexer = new Indexer(INDEX_PATH);
		indexer.index(INPUT_DOCS_PATH, null);
		IndexReader reader = indexer.mWriter.getReader();
		indexer.close();
	}

	private static String getSearchString() throws IOException {
		Util.log("Search for: ");
		if (mScanner.hasNextLine()) {
			mSearchString = mScanner.nextLine();
		}
		return mSearchString;
	}

	private static void search() throws IOException, ParseException {
		Directory directory = FSDirectory.open(new File(INDEX_PATH));
		IndexSearcher searcher = new IndexSearcher(directory);
		QueryParser parser = new QueryParser(Version.LUCENE_29, CONTENT, new RoAnalyzer(Version.LUCENE_29));
		Query query = parser.parse(mSearchString);
		Hits hits = searcher.search(query);
		Util.log("Results: " + hits.length());
		if (hits.length() > 0) {
			for (int i = 0; i < hits.length(); i++) {
				Document document = hits.doc(i);
				Util.log("Found in: " + document.get(FULLPATH) + "\nDate: " + document.get(DATE)  + "\nScore: " + hits.score(i) + "\n " + document.get(FIRST_LINE));
			}
		}
	}
}
