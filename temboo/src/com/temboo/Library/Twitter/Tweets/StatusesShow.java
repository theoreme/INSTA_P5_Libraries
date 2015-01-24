package com.temboo.Library.Twitter.Tweets;

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
StatusesShow

Retrieves a single Tweet with a given ID.
*/
public class StatusesShow extends Choreography {

	/**
	Create a new instance of the StatusesShow Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public StatusesShow(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Twitter/Tweets/StatusesShow"));
	}

	/** 
	Set the value of the AccessTokenSecret input for this Choreo. 

	@param String - (required, string) The Access Token Secret provided by Twitter or retrieved during the OAuth process.
	*/
	public void setAccessTokenSecret(String value) {
		this.inputs.setInput("AccessTokenSecret", value);
	}


	/** 
	Set the value of the AccessToken input for this Choreo. 

	@param String - (required, string) The Access Token provided by Twitter or retrieved during the OAuth process.
	*/
	public void setAccessToken(String value) {
		this.inputs.setInput("AccessToken", value);
	}


	/** 
	Set the value of the ConsumerKey input for this Choreo. 

	@param String - (required, string) The API Key (or Consumer Key) provided by Twitter.
	*/
	public void setConsumerKey(String value) {
		this.inputs.setInput("ConsumerKey", value);
	}


	/** 
	Set the value of the ConsumerSecret input for this Choreo. 

	@param String - (required, string) The API Secret (or Consumer Secret) provided by Twitter.
	*/
	public void setConsumerSecret(String value) {
		this.inputs.setInput("ConsumerSecret", value);
	}


	/** 
	Set the value of the ID input for this Choreo. 

	@param String - (required, string) The numerical ID of the desired Tweet.
	*/
	public void setID(String value) {
		this.inputs.setInput("ID", value);
	}


	/** 
	Set the value of the IncludeEntities input for this Choreo. 

	@param Boolean - (optional, boolean) The "entities" node containing extra metadata will not be included when set to false.
	*/
	public void setIncludeEntities(Boolean value) {
		this.inputs.setInput("IncludeEntities", value);
	}

	/** 
	Set the value of the IncludeEntities input for this Choreo as a String. 

	@param String - (optional, boolean) The "entities" node containing extra metadata will not be included when set to false.
	*/
	public void setIncludeEntities(String value) {
		this.inputs.setInput("IncludeEntities", value);	
	}
	/** 
	Set the value of the IncludeMyRetweet input for this Choreo. 

	@param Boolean - (optional, boolean) When set to true, any Tweets returned that have been retweeted by the authenticating user will include an additional current_user_retweet node, containing the ID of the source status for the retweet.
	*/
	public void setIncludeMyRetweet(Boolean value) {
		this.inputs.setInput("IncludeMyRetweet", value);
	}

	/** 
	Set the value of the IncludeMyRetweet input for this Choreo as a String. 

	@param String - (optional, boolean) When set to true, any Tweets returned that have been retweeted by the authenticating user will include an additional current_user_retweet node, containing the ID of the source status for the retweet.
	*/
	public void setIncludeMyRetweet(String value) {
		this.inputs.setInput("IncludeMyRetweet", value);	
	}
	/** 
	Set the value of the TrimUser input for this Choreo. 

	@param Boolean - (optional, boolean) When set to true, each tweet returned in a timeline will include a user object including only the status authors numerical ID. Defaults to false.
	*/
	public void setTrimUser(Boolean value) {
		this.inputs.setInput("TrimUser", value);
	}

	/** 
	Set the value of the TrimUser input for this Choreo as a String. 

	@param String - (optional, boolean) When set to true, each tweet returned in a timeline will include a user object including only the status authors numerical ID. Defaults to false.
	*/
	public void setTrimUser(String value) {
		this.inputs.setInput("TrimUser", value);	
	}
	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public StatusesShowResultSet run() {
		JSONObject result = super.runWithResults();
		return new StatusesShowResultSet(result);
	}
	
}
