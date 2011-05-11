package com.josuvladimir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.josuvladimir.util.Util;

public class Main {

	public static final String INPUT_FILE_PATH		= "D:/Documents/WORK/Projects/RegasireaInformatiei/res/input.txt";
	public static final String INPUT_DOCS_PATH		= "D:/Documents/WORK/Projects/RegasireaInformatiei/res/docs";
	public static final String INDEX_PATH 			= "D:/Documents/WORK/Projects/RegasireaInformatiei/index/";
	public static final String OUTPUT_PATH 			= "D:/Documents/WORK/Projects/RegasireaInformatiei/output/";
	private static File mOutputFile;
	@SuppressWarnings("unused")
	private static FileWriter mWriter;

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
	}

	@SuppressWarnings("unused")
	private static void init() throws IOException {
		File file = new File(INPUT_FILE_PATH);
		FileInputStream inputStream;
		inputStream = new FileInputStream(file);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		mOutputFile = new File(OUTPUT_PATH + "output.txt");
		mWriter = new FileWriter(mOutputFile);
	}

	@SuppressWarnings({ "unused", "deprecation" })
	private static void search() throws IOException {
		Directory directory = FSDirectory.open(new File(INPUT_DOCS_PATH));
		IndexSearcher searcher = new IndexSearcher(directory);
		QueryParser parser = new QueryParser(Version.LUCENE_29, "contents", new RoAnalyzer(Version.LUCENE_29));
	}
}
