package com.josuvladimir.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
 
public class SoapClient {
	
	public SoapClient(String soapAction, String methodName, String namespace, String uri, Boolean dotNet){
		setSoapAction(soapAction);
		setMethodName(methodName);
		setNamespace(namespace);
		setUri(uri);
		setDotNet(dotNet);
	}
	
	private String mSoapAction; 		//action name on webservice
	private String mMethodName; 		//method name to call
	private String mNamespace; 		//mNamespace
	private String mUri;				//path(wsdl, asmx..)	
	private Boolean mDotNet;			//is .NET?
	private Map<String,Object> parameters;	
	
	public String getSoapAction() {
		return mSoapAction;
	}

	public void setSoapAction(String soapAction) {
		this.mSoapAction = soapAction;
	}

	public String getMethodName() {
		return mMethodName;
	}

	public void setMethodName(String methodName) {
		this.mMethodName = methodName;
	}

	public String getNamespace() {
		return mNamespace;
	}

	public void setNamespace(String namespace) {
		this.mNamespace = namespace;
	}

	public String getUri() {
		return mUri;
	}

	public void setUri(String uri) {
		this.mUri = uri;
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public Boolean getDotNet() {
		return mDotNet;
	}

	public void setDotNet(Boolean dotNet) {
		this.mDotNet = dotNet;
	}
	public Object executeCallResponce(String method, Bundle params) throws IOException, XmlPullParserException {
		SoapObject soapObject = new SoapObject(mNamespace, method);
		Iterable<String> keySet = params.keySet();
		for (String key : keySet) {
			soapObject.addProperty(key, params.get(key));
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = getDotNet();
		HttpTransportSE httpTransportSE = new HttpTransportSE(getUri());
		httpTransportSE.call(mSoapAction, envelope);
		Object response = envelope.getResponse();
		return response;
	}

	public void addParameter(String parameterName, Object parameterValue){
		if(getParameters() == null){
			this.parameters = new HashMap<String, Object>();
		}		
		getParameters().put(parameterName, parameterValue);
	}
	
	public Object executeCallResponse() throws Exception{
		Object result = null;
		SoapObject request = new SoapObject(getNamespace(), getMethodName()); //create the soap method	            
		
		if(getParameters() != null){
			for (Map.Entry<String, Object> property : getParameters().entrySet()) {  
				String parameter = property.getKey();  
		        Object value = property.getValue();	            	
		        request.addProperty(parameter, value);	            	
			}	                                
			getParameters().clear();
		}
			            
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); //serialize the envelope 
	    envelope.dotNet= getDotNet();
	    envelope.setOutputSoapObject(request); //this method will return a response
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(getUri()); //open the requisition
	    androidHttpTransport.call(getSoapAction(), envelope);// call
	    result = envelope.getResponse(); // returns result of method called	           
		
		return result;
	}
	
	public void executeCall() throws Exception{
		SoapObject request = new SoapObject(getNamespace(), getMethodName()); //create the soap method	            
		
		if(getParameters() != null){
			for (Map.Entry<String, Object> property : getParameters().entrySet()) {  
				String parameter = property.getKey();  
		        Object value = property.getValue();	            	
		        request.addProperty(parameter, value);	            	
			}	                                
			getParameters().clear();
		}
			            
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); //serialize the envelope 
	    envelope.dotNet= getDotNet();
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(getUri()); //open the requisition
	    androidHttpTransport.call(getSoapAction(), envelope);// call

	}
	
}