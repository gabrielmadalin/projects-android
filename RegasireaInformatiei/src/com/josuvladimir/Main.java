package com.josuvladimir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.josuvladimir.util.Util;

public class Main {

	public static final String INPUT_FILE_PATH = "D:/Documents/WORK/Projects/RegasireaInformatiei/res/input.txt";
	public static final String OUTPUT_FILE_PATH = "D:/Documents/WORK/Projects/RegasireaInformatiei/res/output.txt";
	private static File mOutputFile;

	public static void main(String[] args) {
		try {
			init();
			process();
		} catch (IOException e) {
			Util.log("Error:" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void init() throws IOException {
		File file = new File(INPUT_FILE_PATH);
		FileInputStream inputStream;
		Util.log("test");
		inputStream = new FileInputStream(file);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		mOutputFile = new File(OUTPUT_FILE_PATH);
		new FileWriter(mOutputFile);
	}

	@SuppressWarnings({ "unused", "deprecation" })
	private static void process() throws IOException {
		Directory directory = FSDirectory.open(new File(INPUT_FILE_PATH));
		IndexSearcher searcher = new IndexSearcher(directory);
		QueryParser parser = new QueryParser(Version.LUCENE_29, "contents", new RoAnalyzer(Version.LUCENE_29));
	}
}
