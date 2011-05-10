/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adryan
 */
import java.io.IOException;

import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.ParseException;


import org.apache.lucene.analysis.snowball.SnowballAnalyzer;



class Search
{

     public static void main(String[] args) throws IOException
  {

  }
  /**
   * Searches for the given string in the "content" field
   */
  public static void search(Searcher searcher, String queryString, String cale)
      throws ParseException, IOException
  {

    // Build a Query object
    QueryParser parser = new QueryParser("content", new SnowballAnalyzer(Main.Language));
    String c = queryString;

    //QueryParser parser = new QueryParser("content", new StandardAnalyzer());
    queryString = queryString.replace("\u015f", "s").replace("\u0163", "t").replace("\u0103", "a").replace("\u00e2", "a").replace("\u00ee", "i");

    Query query = parser.parse(queryString);
    // Search for the query
    Hits hits = searcher.search(query);

    // Examine the Hits object to see if there were any matches
    int hitCount = hits.length();
    if (hitCount == 0)
    {
      System.out.println("Nu s-a gasit nici un rezultat pentru: \"" + c + "\"");
    }
    else
    {
      System.out.println("Rezultate pentru \"" + c
          + "\" au fost gasite in:");

      // Iterate over the Documents in the Hits object
      for (int i = 0; i < hitCount; i++)
      {
        Document doc = hits.doc(i);

        // Print the value that we stored in the "title" field. Note
        // that this Field was not indexed, but (unlike the
        // "contents" field) was stored verbatim and can be
        // retrieved.
        System.out.println(" " + (i + 1) + ". " + doc.get("title"));
      }
    }
    System.out.println();
  }
}
