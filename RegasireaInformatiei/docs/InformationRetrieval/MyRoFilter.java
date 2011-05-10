package informationRetrieval;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

public final class MyRoFilter extends TokenFilter{
	  
	private MyProgram stemmer;
	private TermAttribute termAtt;
	public MyRoFilter(TokenStream input, MyProgram stemmer) 
	{
	    super(input);
	    this.stemmer = stemmer;
	    termAtt = (TermAttribute) addAttribute(TermAttribute.class);
	 }
	
	 public MyRoFilter(TokenStream input) 
	 {
	    super(input);
	    try 
	    {      
	      MyRoStemmer stemClass = new MyRoStemmer();
	      stemmer = (MyProgram) stemClass;
	    } 
	    catch (Exception e) 
	    {
	      throw new RuntimeException(e.toString());
	    }
	    termAtt = (TermAttribute) addAttribute(TermAttribute.class);
	  }

	  /** Returns the next input Token, after being stemmed */
	  @Override
	  public final boolean incrementToken() throws IOException 
	  {
	    if (input.incrementToken()) 
	    {
	      String originalTerm = termAtt.term();
	      stemmer.setCurrent(originalTerm);
	      stemmer.stem();
	      String finalTerm = stemmer.getCurrent();
	      // Don't bother updating, if it is unchanged.
	      if (!originalTerm.equals(finalTerm))
	        termAtt.setTermBuffer(finalTerm);
	      return true;
	    } 
	    else 
	    {
	      return false;
	    }
	  }

}
