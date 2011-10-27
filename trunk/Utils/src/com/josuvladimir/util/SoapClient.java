package com.josuvladimir.util;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;

public class SoapClient {

	private static SoapClient mInstance;
	private String mNameSpace, mSoapAction, mUrl;
	private boolean mDebug = false;
	
	public static synchronized SoapClient getInstance() {
		 
			if (mInstance == null) {
				mInstance = new SoapClient();
			
		}
		return mInstance;
	}
	
	public void setNameSpace(String mNameSpace) {
		this.mNameSpace = mNameSpace;
	}
	public void setUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	public void setSoapAction(String mSoapAction) {
		this.mSoapAction = mSoapAction;
	}
	
	public SoapObject request(String methodName, Bundle params) throws IOException, XmlPullParserException {
		SoapObject request = new SoapObject(mNameSpace, methodName); //set up request
		Iterable<String> keySet = params.keySet();
		for (String string : keySet) {
			request.addProperty(string, params.getString(string));
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); //put all required data into a soap envelope
		envelope.setOutputSoapObject(request);  //prepare request
		HttpTransportSE httpTransport = new HttpTransportSE(mUrl);
		httpTransport.debug = isDebug();
//		httpTransport.debug = LocationTrackerApplication.getInstance().isDebugable();  //this is optional, use it if you don't want to use a packet sniffer to check what the sent 
		httpTransport.call(mSoapAction, envelope); //send request
		SoapObject result=(SoapObject)envelope.getResponse(); //get response
		return result;
		
	}

	public boolean isDebug() {
		return mDebug;
	}

	public void setDebug(boolean mDebug) {
		this.mDebug = mDebug;
	}

}
