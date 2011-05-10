package com.josuvladimir;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class Indexer {
	
	private static final String STOP_WORDS_PATH = "D:/Documents/WORK/Projects/RegasireaInformatiei/res/stop_words.txt";

	public IndexWriter mWriter;

	public String[] getStopWords()
	{
	  ArrayList<String> arrayList = new ArrayList<String>();
	  String[] strings = null;
	  try{
			File f = new File(STOP_WORDS_PATH);
		    FileInputStream fstream = new FileInputStream(f);
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    while ((strLine = br.readLine()) != null)   
		    {
		    	arrayList.add(strLine);
		    	
		    }
		    in.close();
		    strings = new String[arrayList.size()];
		    for(int i=0;i<arrayList.size();i++)
		    {
		    	strings[i] = arrayList.get(i);
		    }
		    
	  }catch (Exception e){
		  System.err.println("Error: " + e.getMessage());
	  }
	  return strings;

	}
  
	public Indexer(String indexDir) throws IOException {
		Directory dir = FSDirectory.open(new File(indexDir));
		mWriter = new IndexWriter(dir,            //3 se creaza indexwriterul
				
			  //  new SnowballAnalyzer(Version.LUCENE_29,"Romanian",getStopWords()),
			  new RoAnalyzer(Version.LUCENE_29, getStopWords()),
			  true,                       //3
			  IndexWriter.MaxFieldLength.UNLIMITED); //3

	}


	public void close() throws IOException 
	{
		mWriter.close();                             //4
	}


	public int index(String dataDir, FileFilter filter) throws Exception {
		File[] files = new File(dataDir).listFiles();
		for (File f: files) {
			if (!f.isDirectory() &&
					!f.isHidden() &&
					f.exists() &&
					f.canRead() &&
					(filter == null || filter.accept(f))) {
				indexFile(f);
			}
		}
		return mWriter.numDocs();                     //5
	}
	public static class TextFilesFilter implements FileFilter {
		public boolean accept(File path) {
			return path.getName().toLowerCase()        //6
             .endsWith(".txt");                  //6
		}
	}
	protected Document getDocument(File f) throws Exception {
		Document doc = new Document();
		doc.add(new Field("contents", new FileReader(f)));
		doc.add(new Field("filename", f.getName(),              //8
				Field.Store.YES, Field.Index.NOT_ANALYZED));//8
		doc.add(new Field("fullpath", f.getCanonicalPath(),     //9
    		Field.Store.YES, Field.Index.NOT_ANALYZED));//9
		return doc;
	}
	private void indexFile(File f) throws Exception {
		System.out.println("Indexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		Analyzer analyzer = new RoAnalyzer(Version.LUCENE_29,getStopWords());
		mWriter.addDocument(doc, analyzer);                           //10
	}
}

/*
#1 Create index in this directory
#2 Index *.txt files from this directory
#3 Create Lucene IndexWriter
#4 Close IndexWriter
#5 Return number of documents indexed
#6 Index .txt files only, using FileFilter
#7 Index file content
#8 Index file name
#9 Index file full path
#10 Add document to Lucene index
*/
