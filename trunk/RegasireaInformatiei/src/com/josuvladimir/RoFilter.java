package com.josuvladimir;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;


public final class RoFilter extends TokenFilter{
	  
	private Stemmer mStemmer;
	private TermAttribute mTermAttribute;
	public RoFilter(TokenStream input, Stemmer stemmer) 
	{
	    super(input);
	    this.mStemmer = stemmer;
	    mTermAttribute = (TermAttribute) addAttribute(TermAttribute.class);
	 }
	
	 public RoFilter(TokenStream input) 
	 {
	    super(input);
	    try 
	    {      
	      RoStemmer stemClass = new RoStemmer();
	      mStemmer = (Stemmer) stemClass;
	    } 
	    catch (Exception e) 
	    {
	      throw new RuntimeException(e.toString());
	    }
	    mTermAttribute = (TermAttribute) addAttribute(TermAttribute.class);
	  }

	  /** Returns the next input Token, after being stemmed */
	  @Override
	  public final boolean incrementToken() throws IOException 
	  {
	    if (input.incrementToken()) 
	    {
	      String originalTerm = mTermAttribute.term();
	      mStemmer.setCurrent(originalTerm);
	      mStemmer.stem();
	      String finalTerm = mStemmer.getCurrent();
	      if (!originalTerm.equals(finalTerm))
	        mTermAttribute.setTermBuffer(finalTerm);
	      return true;
	    } 
	    else 
	    {
	      return false;
	    }
	  }

}
