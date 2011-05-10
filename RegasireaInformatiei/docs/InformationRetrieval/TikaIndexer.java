package informationRetrieval;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;

public class TikaIndexer extends Indexer {

  private boolean DEBUG = false;                     //1

  static Set<String> textualMetadataFields           //2
        = new HashSet<String>();                     //2
  static {                                           //2
    textualMetadataFields.add(Metadata.TITLE);       //2
    textualMetadataFields.add(Metadata.AUTHOR);      //2
    textualMetadataFields.add(Metadata.COMMENTS);    //2
    textualMetadataFields.add(Metadata.KEYWORDS);    //2
    textualMetadataFields.add(Metadata.DESCRIPTION); //2
    textualMetadataFields.add(Metadata.SUBJECT);     //2
  }

  public TikaIndexer(String indexDir) throws IOException {
    super(indexDir);
  }

  protected Document getDocument(File f) throws Exception {

    Metadata metadata = new Metadata();
    metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());   // 4

    // If you know content type (eg because this document
    // was loaded from an HTTP server), then you should also
    // set Metadata.CONTENT_TYPE

    // If you know content encoding (eg because this
    // document was loaded from an HTTP server), then you
    // should also set Metadata.CONTENT_ENCODING

    InputStream is = new FileInputStream(f);      // 5
    Parser parser = new AutoDetectParser();       // 6
    ContentHandler handler = new BodyContentHandler(); // 7
    ParseContext context = new ParseContext();   // 8
    context.set(Parser.class, parser);           // 8

    try {
      parser.parse(is, handler, metadata,      // 9
                   new ParseContext());        // 9
    } finally {
      is.close();
    }

    Document doc = new Document();

    doc.add(new Field("contents", handler.toString(),           // 10
                      Field.Store.YES, Field.Index.ANALYZED));   // 10

    if (DEBUG) {
      System.out.println("  all text: " + handler.toString());
    }
    
    for(String name : metadata.names()) {         //11
      String value = metadata.get(name);

      if (textualMetadataFields.contains(name)) {
        doc.add(new Field("contents", value,      //12
                          Field.Store.NO, Field.Index.ANALYZED));
      }

      doc.add(new Field(name, value, Field.Store.YES, Field.Index.NO)); //13

      if (DEBUG) {
        System.out.println("  " + name + ": " + value);
      }
    }

    if (DEBUG) {
      System.out.println();
    }

    doc.add(new Field("filename", f.getCanonicalPath(),     //14
             Field.Store.YES, Field.Index.NOT_ANALYZED));

    return doc;
  }
}

/*
  #1 Change to true to see all text
  #2 Which metadata fields are textual
  #3 List all mime types handled by Tika
  #4 Create Metadata for the file
  #5 Open the file
  #6 Automatically determines file type
  #7 Extracts metadata and body text
  #8 Setup ParseContext
  #9 Does all the work!
  #10 Index body content
  #11 Index metadata fields
  #12 Append to contents field
  #13 Separately store metadata fields
  #14 Index file path
*/

