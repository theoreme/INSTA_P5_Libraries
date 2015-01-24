package com.temboo.Library.Xively.APIKeys;

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
DeleteKey

Deletes a specific API Key.
*/
public class DeleteKey extends Choreography {

	/**
	Create a new instance of the DeleteKey Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public DeleteKey(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Xively/APIKeys/DeleteKey"));
	}

	/** 
	Set the value of the APIKey input for this Choreo. 

	@param String - (required, string) The API Key you would like to delete.
	*/
	public void setAPIKey(String value) {
		this.inputs.setInput("APIKey", value);
	}


	/** 
	Set the value of the MasterAPIKey input for this Choreo. 

	@param String - (optional, string) Specify a MasterAPIKey with more permissions if the APIKey you would like to delete does not have sufficient permissions.
	*/
	public void setMasterAPIKey(String value) {
		this.inputs.setInput("MasterAPIKey", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public DeleteKeyResultSet run() {
		JSONObject result = super.runWithResults();
		return new DeleteKeyResultSet(result);
	}
	
}
