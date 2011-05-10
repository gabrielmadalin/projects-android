package informationRetrieval;

import java.io.*;
import java.util.*;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.vectorhighlight.BaseFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.FastVectorHighlighter;
import org.apache.lucene.search.vectorhighlight.FragListBuilder;
import org.apache.lucene.search.vectorhighlight.FragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.ScoreOrderFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.SimpleFragListBuilder;


public class Main
{ 
	public static String indexDir; //1 directorul in care se creeaza indexul
	public static String dataDir;  //2 directorul in care se afla fisierele .txt
	public static String queryString;
	public static void init()
	{
		try{
			File f = new File("e:/ir/input.txt");
		    FileInputStream fstream = new FileInputStream(f);
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    if ((strLine = br.readLine()) != null)   
		    {
		      indexDir = strLine;
		      if ((strLine = br.readLine()) != null)
		      {
		    	  dataDir = strLine;
		    	  if ((strLine = br.readLine()) != null)
			      {
			    	  queryString = strLine;
			      }
			      else
			      {
			    	  try{
			    		    File r = new File("e:/ir/result.txt");
			    		    FileWriter fwriter = new FileWriter(r);
			    		    BufferedWriter result = new BufferedWriter(fwriter);
			    		    result.write("Intrebarea nu exista");
			    		    result.close();
			    		    }catch (Exception e){
			    		      System.err.println("Error: " + e.getMessage());
			    		    }
			      }
		      }
		      else
		      {
		    	  try{
		    		    File r = new File("e:/ir/result.txt");
		    		    FileWriter fwriter = new FileWriter(r);
		    		    BufferedWriter result = new BufferedWriter(fwriter);
		    		    result.write("Calea catre directorul care contine documentele nu exista");
		    		    result.close();
		    		    }catch (Exception e){
		    		      System.err.println("Error: " + e.getMessage());
		    		    }
		      }
		    }
		    else
		    {
		    	try
		    	{
		    	File r = new File("e:/ir/result.txt");
    		    FileWriter fwriter = new FileWriter(r);
    		    BufferedWriter result = new BufferedWriter(fwriter);
    		    result.write("Calea catre directorul care va contine indexerul nu exista");
    		    result.close();
    		    }catch (Exception e){
    		      System.err.println("Error: " + e.getMessage());
    		    }
		    }
		    in.close();
		    }catch (Exception e){
		      System.err.println("Error: " + e.getMessage());
		    }
	}
	static FastVectorHighlighter getHighlighter() {
	    FragListBuilder fragListBuilder = new SimpleFragListBuilder();  
	    FragmentsBuilder fragmentBuilder =                              
	      new ScoreOrderFragmentsBuilder(                                
	        BaseFragmentsBuilder.COLORED_PRE_TAGS,                        
	        BaseFragmentsBuilder.COLORED_POST_TAGS);                      
	    return new FastVectorHighlighter(true, true,                     
	        fragListBuilder, fragmentBuilder);                            
	  }

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception 
	{
		init();
		
//		long start = System.currentTimeMillis();
//   
//		Indexer indexer = new Indexer(indexDir); // se instantiaza clasa Indexer pt directorul indexDir
//		
//		int numIndexed = 0; // retine numarul fisierelor indexate
//		
//		try {
//			numIndexed = indexer.index(dataDir, new TextFilesFilter());
//		} 
//		
//		finally {
//			indexer.close();
//		}
//		
//		long end = System.currentTimeMillis();
//
//		System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");
	
		//tika
		//indexer
		long start = new Date().getTime();
	    TikaIndexer indexer = new TikaIndexer(indexDir);
	    int numIndexed = indexer.index(dataDir, null);
	    IndexReader i = indexer.writer.getReader();
	    indexer.close();
	    long end = new Date().getTime();

	    System.out.println("Indexing " + numIndexed + " files took "
	      + (end - start) + " milliseconds");
	    
		//searcher
	    
	    Directory directory = FSDirectory.open(new File(indexDir));
	    IndexSearcher searcher = new IndexSearcher(directory); 
	    QueryParser queryparser = new QueryParser(Version.LUCENE_29,"contents",new MyRoAnalyzer(Version.LUCENE_29,indexer.getStopWords()));
	    Query query = queryparser.parse(queryString);
	    SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class=\"highlight\">",
	    	"</span>");
	    TopDocs docs = searcher.search(query, 10);
	    
	    FileWriter writer = new FileWriter(new File("e:/ir/output.html"));
	    writer.write("<html>");
	    writer.write("<style>\n" +
	    ".highlight {\n" +
	    " background: yellow;\n" +
	    "}\n" +
	    "</style>");
	    writer.write("<body>");
	    for(ScoreDoc doc : docs.scoreDocs) 
	    {
	    	Document d = i.document(doc.doc);
	    	String text = d.get("contents");
	    	String filepath = d.get("filename");
	    	TokenStream tokens = new MyRoAnalyzer(Version.LUCENE_29,indexer.getStopWords()).tokenStream("contents", new StringReader(text));
	    	QueryScorer scorer = new QueryScorer(query, "contents");
	    	Highlighter highlighter = new Highlighter(formatter,scorer);
	    	highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
	    	String result = highlighter.getBestFragments(tokens, text, 3, "...");
	    	writer.write("<b>Documentul: ");
	    	
	    	writer.write(Integer.toString(doc.doc) + ";     ");
	    	writer.write("Calea: " + filepath + ";     ");
	    	writer.write("Scorul: " + Float.toString(doc.score));
	    	writer.write("</br>");
	    	
	    	writer.write("</b>");
	    	writer.write(result);
	    	writer.write("</br>--------------------------------------------------------------------------------------------------");
	    	writer.write("</br>");
	    	
	    }
	   
	    writer.write("</body></html>");
	    writer.close();
	    
	    searcher.close();
	}
}
