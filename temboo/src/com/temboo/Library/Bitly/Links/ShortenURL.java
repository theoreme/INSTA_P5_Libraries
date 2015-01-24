package com.temboo.Library.Bitly.Links;

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
ShortenURL

Returns a shortened URL for a long URL that you provide.
*/
public class ShortenURL extends Choreography {

	/**
	Create a new instance of the ShortenURL Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public ShortenURL(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Bitly/Links/ShortenURL"));
	}

	/** 
	Set the value of the AccessToken input for this Choreo. 

	@param String - (required, string) The oAuth access token provided by Bitly.
	*/
	public void setAccessToken(String value) {
		this.inputs.setInput("AccessToken", value);
	}


	/** 
	Set the value of the LongURL input for this Choreo. 

	@param String - (required, string) The long url that you want to shorten.
	*/
	public void setLongURL(String value) {
		this.inputs.setInput("LongURL", value);
	}


	/** 
	Set the value of the ResponseFormat input for this Choreo. 

	@param String - (optional, string) The format that you want the response to be in. Defaults to simple "txt" format which will just return the shortened URL. "json" and "xml" are also supported.
	*/
	public void setResponseFormat(String value) {
		this.inputs.setInput("ResponseFormat", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public ShortenURLResultSet run() {
		JSONObject result = super.runWithResults();
		return new ShortenURLResultSet(result);
	}
	
}
