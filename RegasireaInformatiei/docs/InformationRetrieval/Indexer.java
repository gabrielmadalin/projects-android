package informationRetrieval;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.*;


public class Indexer {
	
  public IndexWriter writer;
  
  public String[] getStopWords()
  {
	  ArrayList<String> a = new ArrayList<String>();
	  String[] s = null;
	  try{
			File f = new File("e:/ir/stopwords.txt");
		    FileInputStream fstream = new FileInputStream(f);
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    while ((strLine = br.readLine()) != null)   
		    {
		    	a.add(strLine);
		    	
		    }
		    in.close();
		    s = new String[a.size()];
		                            
		    for(int i=0;i<a.size();i++)
		    {
		    	s[i] = a.get(i);
		    }
		    }catch (Exception e){
		      System.err.println("Error: " + e.getMessage());
		    }
		   
	  return s;
  }
  public Indexer(String indexDir) throws IOException 
  {
	Directory dir = FSDirectory.open(new File(indexDir));
    writer = new IndexWriter(dir,            //3 se creaza indexwriterul
                 new SnowballAnalyzer(Version.LUCENE_29,"Romanian",getStopWords()),
                 true,                       //3
                 IndexWriter.MaxFieldLength.UNLIMITED); //3
    		
  }

  public void close() throws IOException 
  {
    writer.close();                             //4
  }

  public int index(String dataDir, FileFilter filter) throws Exception
  {
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
    return writer.numDocs();                     //5
  }

  public static class TextFilesFilter implements FileFilter 
  {
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
    Analyzer analyzer = new MyRoAnalyzer(Version.LUCENE_29,getStopWords());
    writer.addDocument(doc, analyzer);                           //10
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
