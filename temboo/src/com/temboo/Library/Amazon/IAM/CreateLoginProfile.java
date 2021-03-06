package com.temboo.Library.Amazon.IAM;

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
CreateLoginProfile

Creates a password for the specified user, which gives the user the ability to access AWS services through the AWS Management Console.
*/
public class CreateLoginProfile extends Choreography {

	/**
	Create a new instance of the CreateLoginProfile Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public CreateLoginProfile(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Amazon/IAM/CreateLoginProfile"));
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
	Set the value of the MustChangePassword input for this Choreo. 

	@param Boolean - (optional, boolean) Whether or not the user is required to change their password immediately.  Valid values are true/false or 1/0.
	*/
	public void setMustChangePassword(Boolean value) {
		this.inputs.setInput("MustChangePassword", value);
	}

	/** 
	Set the value of the MustChangePassword input for this Choreo as a String. 

	@param String - (optional, boolean) Whether or not the user is required to change their password immediately.  Valid values are true/false or 1/0.
	*/
	public void setMustChangePassword(String value) {
		this.inputs.setInput("MustChangePassword", value);	
	}
	/** 
	Set the value of the Password input for this Choreo. 

	@param String - (required, string) The new password for the user.
	*/
	public void setPassword(String value) {
		this.inputs.setInput("Password", value);
	}


	/** 
	Set the value of the ResponseFormat input for this Choreo. 

	@param String - (optional, string) The format that the response should be in. Valid values are "xml" (the default) and "json".
	*/
	public void setResponseFormat(String value) {
		this.inputs.setInput("ResponseFormat", value);
	}


	/** 
	Set the value of the UserName input for this Choreo. 

	@param String - (required, string) The name of the user to create a password for.
	*/
	public void setUserName(String value) {
		this.inputs.setInput("UserName", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public CreateLoginProfileResultSet run() {
		JSONObject result = super.runWithResults();
		return new CreateLoginProfileResultSet(result);
	}
	
}
