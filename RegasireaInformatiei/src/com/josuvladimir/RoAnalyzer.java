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


public class RoAnalyzer extends Analyzer
{
	private final Version mVersion;
	private Set<?> mStopWords;
	
	public RoAnalyzer(Version version) {
		this.mVersion = version;
	}
	
	public RoAnalyzer(Version version, String[] stopwords) {
	    this(version);
	    mStopWords = StopFilter.makeStopSet(stopwords);
	}
	
	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
	    TokenStream result = new StandardTokenizer(mVersion, reader);
	    result = new StandardFilter(result);
	    result = new LowerCaseFilter(result);
	    if (mStopWords != null)
	    	result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(mVersion),
	                              result, mStopWords);
	    result = new RoFilter(result);
	    return result;
	}

	private class SavedStreams {
		Tokenizer source;
		TokenStream result;
	};

	@Override
	public TokenStream reusableTokenStream(String fieldName, Reader reader)
	throws IOException {
		if (overridesTokenStreamMethod) {
			return tokenStream(fieldName, reader);
	    }
	    SavedStreams streams = (SavedStreams) getPreviousTokenStream();
	    if (streams == null) {
	    	streams = new SavedStreams();
	    	streams.source = new StandardTokenizer(mVersion, reader);
	    	streams.result = new StandardFilter(streams.source);
	    	streams.result = new LowerCaseFilter(streams.result);
	    	if (mStopWords != null)
	    		streams.result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(mVersion),
	                                        streams.result, mStopWords);
	    	streams.result = new RoFilter(streams.result);
	    	setPreviousTokenStream(streams);
	    } else {
	    	streams.source.reset(reader);
	    }
	    return streams.result;
	}
}
