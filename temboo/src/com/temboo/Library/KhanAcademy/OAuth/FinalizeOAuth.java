package com.temboo.Library.KhanAcademy.OAuth;

/*
Copyright 2014 Temboo, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import processing.data.JSONArray;
import processing.data.JSONObject;
import java.math.BigDecimal;
import com.temboo.core.Choreography;
import com.temboo.core.Choreography.ResultSet;
import com.temboo.core.TembooException;
import com.temboo.core.TembooPath;
import com.temboo.core.TembooSession;

/** 
FinalizeOAuth

Completes the OAuth process by retrieving a Khan Academy OAuth token and token secret for a user, after they have visited the authorization URL returned by the InitializeOAuth Choreo and clicked "allow."
*/
public class FinalizeOAuth extends Choreography {

	/**
	Create a new instance of the FinalizeOAuth Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public FinalizeOAuth(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/KhanAcademy/OAuth/FinalizeOAuth"));
	}

	/** 
	Set the value of the AccountName input for this Choreo. 

	@param String - (optional, string) Deprecated (retained for backward compatibility only).
	*/
	public void setAccountName(String value) {
		this.inputs.setInput("AccountName", value);
	}


	/** 
	Set the value of the AppKeyName input for this Choreo. 

	@param String - (optional, string) Deprecated (retained for backward compatibility only).
	*/
	public void setAppKeyName(String value) {
		this.inputs.setInput("AppKeyName", value);
	}


	/** 
	Set the value of the AppKeyValue input for this Choreo. 

	@param String - (optional, string) Deprecated (retained for backward compatibility only).
	*/
	public void setAppKeyValue(String value) {
		this.inputs.setInput("AppKeyValue", value);
	}


	/** 
	Set the value of the CallbackID input for this Choreo. 

	@param String - (required, string) The callback token returned by the InitializeOAuth Choreo. Used to retrieve the callback data after the user authorizes.
	*/
	public void setCallbackID(String value) {
		this.inputs.setInput("CallbackID", value);
	}


	/** 
	Set the value of the ConsumerKey input for this Choreo. 

	@param String - (required, string) The Consumer Key provided by Khan Academy.
	*/
	public void setConsumerKey(String value) {
		this.inputs.setInput("ConsumerKey", value);
	}


	/** 
	Set the value of the ConsumerSecret input for this Choreo. 

	@param String - (required, string) The OAuth Consumer Secret provided by Khan Academy.
	*/
	public void setConsumerSecret(String value) {
		this.inputs.setInput("ConsumerSecret", value);
	}


	/** 
	Set the value of the Timeout input for this Choreo. 

	@param Integer - (optional, integer) The amount of time (in seconds) to poll your Temboo callback URL to see if your app's user has allowed or denied the request for access. Defaults to 20. Max is 60.
	*/
	public void setTimeout(Integer value) {
		this.inputs.setInput("Timeout", value);
	}

	/** 
	Set the value of the Timeout input for this Choreo as a String. 

	@param String - (optional, integer) The amount of time (in seconds) to poll your Temboo callback URL to see if your app's user has allowed or denied the request for access. Defaults to 20. Max is 60.
	*/
	public void setTimeout(String value) {
		this.inputs.setInput("Timeout", value);	
	}
	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public FinalizeOAuthResultSet run() {
		JSONObject result = super.runWithResults();
		return new FinalizeOAuthResultSet(result);
	}
	
}