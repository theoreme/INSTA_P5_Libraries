package com.temboo.Library.Amazon.FPS;

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
GetAccountBalance

Retrieves the current balance of your account.
*/
public class GetAccountBalance extends Choreography {

	/**
	Create a new instance of the GetAccountBalance Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public GetAccountBalance(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Amazon/FPS/GetAccountBalance"));
	}

	/** 
	Set the value of the AWSAccessKeyId input for this Choreo. 

	@param String - (required, string) The Access Key ID provided by Amazon Web Services.
	*/
	public void setAWSAccessKeyId(String value) {
		this.inputs.setInput("AWSAccessKeyId", value);
	}


	/** 
	Set the value of the AWSSecretKeyId input for this Choreo. 

	@param String - (required, string) The Secret Key ID provided by Amazon Web Services.
	*/
	public void setAWSSecretKeyId(String value) {
		this.inputs.setInput("AWSSecretKeyId", value);
	}


	/** 
	Set the value of the Endpoint input for this Choreo. 

	@param String - (optional, string) The endpoint should be fps.sandbox.amazonaws.com when accessing the sandbox. Defaults to production setting:  fps.amazonaws.com.
	*/
	public void setEndpoint(String value) {
		this.inputs.setInput("Endpoint", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public GetAccountBalanceResultSet run() {
		JSONObject result = super.runWithResults();
		return new GetAccountBalanceResultSet(result);
	}
	
}
