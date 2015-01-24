package com.temboo.Library.Parse.PushNotifications;

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
DeleteInstallation

Deletes an installation object.
*/
public class DeleteInstallation extends Choreography {

	/**
	Create a new instance of the DeleteInstallation Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public DeleteInstallation(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Parse/PushNotifications/DeleteInstallation"));
	}

	/** 
	Set the value of the ObjectID input for this Choreo. 

	@param String - (required, string) The ID of the installation to delete.
	*/
	public void setObjectID(String value) {
		this.inputs.setInput("ObjectID", value);
	}


	/** 
	Set the value of the ApplicationID input for this Choreo. 

	@param String - (required, string) The Application ID provided by Parse.
	*/
	public void setApplicationID(String value) {
		this.inputs.setInput("ApplicationID", value);
	}


	/** 
	Set the value of the MasterKey input for this Choreo. 

	@param String - (required, string) The Master Key provided by Parse.
	*/
	public void setMasterKey(String value) {
		this.inputs.setInput("MasterKey", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public DeleteInstallationResultSet run() {
		JSONObject result = super.runWithResults();
		return new DeleteInstallationResultSet(result);
	}
	
}