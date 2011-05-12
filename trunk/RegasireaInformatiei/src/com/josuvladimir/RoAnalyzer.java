package com.josuvladimir;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

import com.josuvladimir.util.MyRoFilter;

public class RoAnalyzer extends Analyzer
{
	private final Version version;
	private Set<?> stopWords;
	
	/** Builds the named analyzer with no stop words. */
	public RoAnalyzer(Version version) {
		this.version = version;
	}
	
	/** Builds the named analyzer with the given stop words. */
	public RoAnalyzer(Version version, String[] stopwords) {
	    this(version);
	    stopWords = StopFilter.makeStopSet(stopwords);
	}
	
	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
	    TokenStream result = new StandardTokenizer(version, reader);
	    result = new StandardFilter(result);
	    result = new LowerCaseFilter(result);
	    if (stopWords != null)
	    	result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(version),
	                              result, stopWords);
	    result = new MyRoFilter(result);
	    return result;
	}

	private class SavedStreams {
		Tokenizer source;
		TokenStream result;
	};


	/** Returns a (possibly reused) {@link StandardTokenizer} filtered by a 
	   * {@link StandardFilter}, a {@link LowerCaseFilter}, 
	   * a {@link StopFilter}, and a {@link SnowballFilter} */

	@Override
	public TokenStream reusableTokenStream(String fieldName, Reader reader)
	throws IOException {
		if (overridesTokenStreamMethod) {
			// LUCENE-1678: force fallback to tokenStream() if we
			// have been subclassed and that subclass overrides
			// tokenStream but not reusableTokenStream
			return tokenStream(fieldName, reader);
	    }
	    SavedStreams streams = (SavedStreams) getPreviousTokenStream();
	    if (streams == null) {
	    	streams = new SavedStreams();
	    	streams.source = new StandardTokenizer(version, reader);
	    	streams.result = new StandardFilter(streams.source);
	    	streams.result = new LowerCaseFilter(streams.result);
	    	if (stopWords != null)
	    		streams.result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(version),
	                                        streams.result, stopWords);
	    	streams.result = new MyRoFilter(streams.result);
	    	setPreviousTokenStream(streams);
	    } else {
	    	streams.source.reset(reader);
	    }
	    return streams.result;
	}
}
