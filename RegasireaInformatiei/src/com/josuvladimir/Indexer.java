package com.josuvladimir;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
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
	

	public IndexWriter mWriter;

	public String[] getStopWords()
	{
	  ArrayList<String> arrayList = new ArrayList<String>();
	  String[] strings = null;
	  try{
			File f = new File(Main.STOP_WORDS_PATH);
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
		mWriter = new IndexWriter(dir,
				new RoAnalyzer(Version.LUCENE_29, getStopWords()),
				true, IndexWriter.MaxFieldLength.UNLIMITED);
	}


	public void close() throws IOException 	{
		mWriter.close();                   
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
		return mWriter.numDocs();
	}

	public static class TextFilesFilter implements FileFilter {
		public boolean accept(File path) {
			return
			path.getName().toLowerCase().endsWith(".txt") || 
			path.getName().toLowerCase().endsWith(".pdf") ||
			path.getName().toLowerCase().endsWith(".doc");
		}
	}

	protected Document getDocument(File f) throws Exception {
		Document doc = new Document();
		doc.add(new Field(Main.CONTENT, Util.getContent(f.getAbsolutePath()), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(Main.TITLE, f.getName(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(Main.FULLPATH, f.getCanonicalPath(),
    		Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(Main.FIRST_LINE, Util.getFirstLine(f.getAbsolutePath()),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(Main.DATE, Util.getData(f.lastModified()),
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		f.lastModified();
		return doc;
	}
	private void indexFile(File f) throws Exception {
		Util.log("Indexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		Analyzer analyzer = new RoAnalyzer(Version.LUCENE_29,getStopWords());
		mWriter.addDocument(doc, analyzer);
	}
}