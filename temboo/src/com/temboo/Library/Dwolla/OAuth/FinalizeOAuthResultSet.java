package com.temboo.Library.Dwolla.OAuth;

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
import com.temboo.core.Choreography.ResultSet;

	
/**
	A ResultSet with methods tailored to the values returned by the FinalizeOAuth Choreo.
	The ResultSet object is used to retrieve the results of a Choreo execution.
*/
public  class FinalizeOAuthResultSet extends ResultSet {
		
	public FinalizeOAuthResultSet(JSONObject doc) {
		super(doc);
	}

	/** 
	Retrieve the value for the "AccessToken" output from this Choreo execution

	@return String - (string) The access token for the user that has granted access to your application.
	*/
	public String getAccessToken() {
		return this.getResultString("AccessToken");
	}
	/** 
	Retrieve the value for the "ExpiresIn" output from this Choreo execution

	@return String - (integer) The lifetime of the access token, in seconds.
	*/
	public String getExpiresIn() {
		return this.getResultString("ExpiresIn");
	}
	/** 
	Retrieve the value for the "RefreshExpiresIn" output from this Choreo execution

	@return String - (integer) The lifetime of the refresh token, in seconds.
	*/
	public String getRefreshExpiresIn() {
		return this.getResultString("RefreshExpiresIn");
	}
	/** 
	Retrieve the value for the "RefreshToken" output from this Choreo execution

	@return String - (string) A refresh token that can be used to generate a new access token when the current token expires.
	*/
	public String getRefreshToken() {
		return this.getResultString("RefreshToken");
	}
}