package com.temboo.Library.Amazon.CloudDrive.Properties;

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
AddProperty

Adds a property to a file or folder.
*/
public class AddProperty extends Choreography {

	/**
	Create a new instance of the AddProperty Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public AddProperty(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Amazon/CloudDrive/Properties/AddProperty"));
	}

	/** 
	Set the value of the Value input for this Choreo. 

	@param String - (required, string) The value of the property. Max length 500 characters.
	*/
	public void setValue(String value) {
		this.inputs.setInput("Value", value);
	}


	/** 
	Set the value of the AccessToken input for this Choreo. 

	@param String - (optional, string) A valid access token retrieved during the OAuth process. This is required unless you provide the ClientID, ClientSecret, and RefreshToken to generate a new access token.
	*/
	public void setAccessToken(String value) {
		this.inputs.setInput("AccessToken", value);
	}


	/** 
	Set the value of the ClientID input for this Choreo. 

	@param String - (conditional, string) The Client ID provided by Amazon. Required unless providing a valid AccessToken.
	*/
	public void setClientID(String value) {
		this.inputs.setInput("ClientID", value);
	}


	/** 
	Set the value of the ClientSecret input for this Choreo. 

	@param String - (conditional, string) The Client Secret provided by Amazon. Required unless providing a valid AccessToken.
	*/
	public void setClientSecret(String value) {
		this.inputs.setInput("ClientSecret", value);
	}


	/** 
	Set the value of the HandleRequestThrottling input for this Choreo. 

	@param Boolean - (optional, boolean) Whether or not to perform a retry sequence if a throttling error occurs. Set to true to enable this feature. The request will be retried up-to five times when enabled.
	*/
	public void setHandleRequestThrottling(Boolean value) {
		this.inputs.setInput("HandleRequestThrottling", value);
	}

	/** 
	Set the value of the HandleRequestThrottling input for this Choreo as a String. 

	@param String - (optional, boolean) Whether or not to perform a retry sequence if a throttling error occurs. Set to true to enable this feature. The request will be retried up-to five times when enabled.
	*/
	public void setHandleRequestThrottling(String value) {
		this.inputs.setInput("HandleRequestThrottling", value);	
	}
	/** 
	Set the value of the ID input for this Choreo. 

	@param String - (required, string) The ID of the file or folder to which the properties needs to be added.
	*/
	public void setID(String value) {
		this.inputs.setInput("ID", value);
	}


	/** 
	Set the value of the Key input for this Choreo. 

	@param String - (required, string) The key of the properties which needs to be added. The key should just contain alphanumeric and "_". Max length is 50 characters.
	*/
	public void setKey(String value) {
		this.inputs.setInput("Key", value);
	}


	/** 
	Set the value of the MetaDataURL input for this Choreo. 

	@param String - (optional, string) The appropriate metadataUrl for your account. When not provided, the Choreo will lookup the URL using the Account.GetEndpoint Choreo.
	*/
	public void setMetaDataURL(String value) {
		this.inputs.setInput("MetaDataURL", value);
	}


	/** 
	Set the value of the Owner input for this Choreo. 

	@param String - (required, string) The "owner" of property.
	*/
	public void setOwner(String value) {
		this.inputs.setInput("Owner", value);
	}


	/** 
	Set the value of the RefreshToken input for this Choreo. 

	@param String - (conditional, string) An OAuth refresh token used to generate a new access token when the original token is expired. Required unless providing a valid AccessToken.
	*/
	public void setRefreshToken(String value) {
		this.inputs.setInput("RefreshToken", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public AddPropertyResultSet run() {
		JSONObject result = super.runWithResults();
		return new AddPropertyResultSet(result);
	}
	
}
