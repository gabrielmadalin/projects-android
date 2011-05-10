package com.josuvladimir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.josuvladimir.util.MyRoAnalyzer;

public class Main {

	private static final String FILE_PATH = "/res/input.txt";

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		File file = new File(FILE_PATH);
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String string;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		process();
	}

	@SuppressWarnings({ "unused", "deprecation" })
	private static void process() {
		try {
			Directory directory = FSDirectory.open(new File(FILE_PATH));
			IndexSearcher searcher = new IndexSearcher(directory);
			QueryParser parser = new QueryParser(Version.LUCENE_29, "contents", new MyRoAnalyzer(Version.LUCENE_29));
			
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
