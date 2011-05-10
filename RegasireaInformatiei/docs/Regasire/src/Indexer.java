/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adryan
 */
import java.io.FileNotFoundException;
import java.io.StringReader;

import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.apache.lucene.analysis.snowball.SnowballFilter;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import org.apache.lucene.analysis.StopFilter;
import java.io.File;
import org.apache.lucene.store.Directory;



class Index
{
    //public static RAMDirectory idx = new RAMDirectory();

    public static Directory ind;
    private static final String[] STOP_WORDS =
  {
"a",
"abia",
"acea",
"aceasta",
"aceea",
"aceeasi",
"aceia",
"acel",
"acela",
"acelasi",
"acelea",
"acest",
"acesta",
"aceste",
"acestea",
"acestei",
"acestia",
"acestui",
"acolo",
"acum",
"adica",
"ai",
"aia",
"aici",
"aiurea",
"al",
"ala",
"alaturi",
"ale",
"alt",
"alta",
"altceva",
"alte",
"altfel",
"alti",
"altii",
"altul",
"am",
"anume",
"apoi",
"ar",
"are",
"as",
"asa",
"asemenea",
"asta",
"astazi",
"astfel",
"asupra",
"atare",
"ati",
"atit",
"atat",
"atata",
"atatea",
"atatia",
"atita",
"atitea",
"atitia",
"atunci",
"au",
"avea",
"avem",
"avut",
"azi",
"b",
"ba",
"bine",
"c",
"ca",
"cat",
"cam",
"capat",
"care",
"careia",
"carora",
"caruia",
"catre",
"ce",
"cea",
"ceea",
"cei",
"ceilalti",
"cel",
"cele",
"celor",
"ceva",
"chiar",
"ci",
"cind",
"cand",
"cine",
"cineva",
"cit",
"cita",
"cite",
"citeva",
"citi",
"citiva",
"conform",
"cu",
"cui",
"cum",
"cumva",
"d",
"da",
"daca",
"dar",
"dat",
"de",
"deasupra",
"deci",
"decit",
"degraba",
"deja",
"desi",
"despre",
"din",
"dintr",
"dintre",
"doar",
"dupa",
"e",
"ea",
"ei",
"el",
"ele",
"era",
"este",
"eu",
"exact",
"f",
"face",
"fara",
"fata",
"fel",
"fi",
"fie",
"foarte",
"fost",
"g",
"geaba",
"h",
"i",
"ia",
"iar",
"ii",
"il",
"imi",
"in",
"inainte",
"inapoi",
"inca",
"incit",
"insa",
"intr",
"intre",
"isi",
"iti",
"j",
"k",
"l",
"la",
"le",
"li",
"lor",
"lui",
"m",
"ma",
"mai",
"mare",
"mi",
"mod",
"mult",
"multa",
"multe",
"multi",
"n",
"ne",
"ni",
"nici",
"niciodata",
"nimeni",
"nimic",
"niste",
"noi",
"nostri",
"nou",
"noua",
"nu",
"numai",
"o",
"or",
"ori",
"orice",
"oricum",
"p",
"pai",
"parca",
"pe",
"pentru",
"peste",
"pina",
"plus",
"prea",
"prin",
"putini",
"r",
"s",
"sa",
"sai",
"sale",
"sau",
"se",
"si",
"sint",
"sintem",
"sa-ti",
"sa-mi",
"spre",
"sub",
"sus",
"sunt",
"suntem",
"t",
"te",
"ti",
"toata",
"toate",
"tocmai",
"tot",
"toti",
"totul",
"totusi",
"tu",
"tuturor",
"u",
"un",
"una",
"unde",
"unei",
"unele",
"uneori",
"unii",
"unor",
"unui",
"unul",
"ul",
"ului",
"v",
"va",
"voi",
"vom",
"vor",
"vreo",
"vreun",
"x",
"z"
  };
    public static void main(String[] args) throws IOException
    {

    }

  public static void indexare(String cale) throws IOException
  {
      Directory idx = FSDirectory.getDirectory(Main.cale2, false);
      ind = idx;

    try
    {
      IndexWriter writer = new IndexWriter(idx, new StandardAnalyzer(), true);

        File dir = new File(cale);

        String[] children = dir.list();
        if (children == null) 
        {

        }
        else
        {
            for (int i = 0; i < children.length; i++)
            {
                String filename = children[i];
                System.out.print(filename);
                System.out.print(" \n");

                writer.addDocument(createDocument(filename,
                   cont(filename)));
            }
        }

      writer.optimize();
      writer.close();
      
    }
    catch (IOException ioe)
    {
        System.out.print(ioe.toString());
    }
 
  }


  public static String cont(String fil) throws FileNotFoundException
  {

      String t = " ";
      try
      {
          FileInputStream fstream = new FileInputStream( Main.cale + fil);
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          while ((strLine = br.readLine()) != null)
          {
              t = t + strLine + " ";
              System.out.print(strLine);
          }
          System.out.print("\n");
          in.close();
      }
      catch (Exception e)
      {
          System.err.println("Error: " + e.getMessage());
      }
      return t;
  }


  private static Document createDocument(String title, String content)
  {
    Document doc = new Document();

    doc.add(new Field("title", title, Field.Store.YES, Field.Index.NO));

    content = content.replace("\u015f", "s").replace("\u0163", "t").replace("\u0103", "a").replace("\u00e2", "a").replace("\u00ee", "i");
    EnglishStemmer ob = new EnglishStemmer();

    TokenStream result = new StandardTokenizer(new StringReader(content));

    result = new StandardFilter(result);
    result = new LowerCaseFilter(result);
    result = new StopFilter(result, STOP_WORDS);
    result = new SnowballFilter(result,"Romanian");
    //result = new PorterStemFilter(result);

    doc.add(new Field("content", result));

    return doc;
  }

}
