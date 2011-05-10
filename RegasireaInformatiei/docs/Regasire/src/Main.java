/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adryan
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryParser.ParseException;


import java.util.Scanner;



class Main
{
    static String f;
    protected static String Language = "Romanian";

    public static String cale = "C:/Documents and Settings/ady/Desktop/Regasire/src/search/";
    public static String cale2 = "C:/Documents and Settings/ady/Desktop/Regasire/src/index/";
    public static String find = "C:/Documents and Settings/ady/Desktop/Regasire/src/find/find.txt";



  public static void main(String[] args) throws IOException, ParseException
  {

      Index.indexare(cale);

       // Build an IndexSearcher using the in-memory index
      Searcher searcher = new IndexSearcher(Index.ind);

      // Run some queries
      Scanner sc = new Scanner(System.in);

      System.out.print("\n \n Pentru citire din fisier apasati 1 \n Pentru citire de la tastatura apasati 2 \n \n \n");

      int w;

      w = sc.nextInt();

      if(w == 2)
      {
          System.out.print("Ce vreti sa cautati: ");
          String q = sc.next();
          Search.search(searcher, q, cale);
      }
      
      else if(w == 1)
      {
          StringBuffer fileData = new StringBuffer(1000);
          BufferedReader reader = new BufferedReader(new FileReader(find));
          char[] buf = new char[1024];
          int numRead=0;
          while((numRead=reader.read(buf)) != -1)
          {
              String readData = String.valueOf(buf, 0, numRead);
              fileData.append(readData);
              buf = new char[1024];
          }
          reader.close();
          f = fileData.toString();
          //System.out.println(f);
          Search.search(searcher, f, cale);
      }

      searcher.close();

  }


  
}
