package com.temboo.Library.LastFm.User;

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
GetTopTracks

Retrieves the top tracks listened to by a user.
*/
public class GetTopTracks extends Choreography {

	/**
	Create a new instance of the GetTopTracks Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public GetTopTracks(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/LastFm/User/GetTopTracks"));
	}

	/** 
	Set the value of the APIKey input for this Choreo. 

	@param String - (string) Your Last.fm API Key.
	*/
	public void setAPIKey(String value) {
		this.inputs.setInput("APIKey", value);
	}


	/** 
	Set the value of the Limit input for this Choreo. 

	@param Integer - (optional, integer) The number of results to fetch per page. Defaults to 50.
	*/
	public void setLimit(Integer value) {
		this.inputs.setInput("Limit", value);
	}

	/** 
	Set the value of the Limit input for this Choreo as a String. 

	@param String - (optional, integer) The number of results to fetch per page. Defaults to 50.
	*/
	public void setLimit(String value) {
		this.inputs.setInput("Limit", value);	
	}
	/** 
	Set the value of the Page input for this Choreo. 

	@param Integer - (optional, integer) The page number to fetch. Defaults to 1.
	*/
	public void setPage(Integer value) {
		this.inputs.setInput("Page", value);
	}

	/** 
	Set the value of the Page input for this Choreo as a String. 

	@param String - (optional, integer) The page number to fetch. Defaults to 1.
	*/
	public void setPage(String value) {
		this.inputs.setInput("Page", value);	
	}
	/** 
	Set the value of the Period input for this Choreo. 

	@param String - (optional, string) The time period over which to retrieve top tracks for. Valid values are: overall, 7day, 3month, 6month, 12month. Defaults to 'overall'.
	*/
	public void setPeriod(String value) {
		this.inputs.setInput("Period", value);
	}


	/** 
	Set the value of the User input for this Choreo. 

	@param String - (string) The Last.fm username to fetch top tracks for.
	*/
	public void setUser(String value) {
		this.inputs.setInput("User", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public GetTopTracksResultSet run() {
		JSONObject result = super.runWithResults();
		return new GetTopTracksResultSet(result);
	}
	
}
