package com.temboo.Library.Zoho.Sheet;

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
DownloadSpreadsheet

Downloads a specified spreadsheet in a user's Zoho Sheet Account.
*/
public class DownloadSpreadsheet extends Choreography {

	/**
	Create a new instance of the DownloadSpreadsheet Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public DownloadSpreadsheet(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Zoho/Sheet/DownloadSpreadsheet"));
	}

	/** 
	Set the value of the APIKey input for this Choreo. 

	@param String - (required, string) The API Key provided by Zoho
	*/
	public void setAPIKey(String value) {
		this.inputs.setInput("APIKey", value);
	}


	/** 
	Set the value of the BookId input for this Choreo. 

	@param Integer - (required, integer) Specifies the unique spreadsheet id to download.
	*/
	public void setBookId(Integer value) {
		this.inputs.setInput("BookId", value);
	}

	/** 
	Set the value of the BookId input for this Choreo as a String. 

	@param String - (required, integer) Specifies the unique spreadsheet id to download.
	*/
	public void setBookId(String value) {
		this.inputs.setInput("BookId", value);	
	}
	/** 
	Set the value of the DownloadFormat input for this Choreo. 

	@param String - (required, string) Specifies the file format in which the documents need to be downloaded. Possible values for documents: xls, xlsx, ods, sxc, pdf, html, csv, tsv.
	*/
	public void setDownloadFormat(String value) {
		this.inputs.setInput("DownloadFormat", value);
	}


	/** 
	Set the value of the LoginID input for this Choreo. 

	@param String - (required, string) Your Zoho username (or login id)
	*/
	public void setLoginID(String value) {
		this.inputs.setInput("LoginID", value);
	}


	/** 
	Set the value of the Password input for this Choreo. 

	@param String - (required, password) Your Zoho password
	*/
	public void setPassword(String value) {
		this.inputs.setInput("Password", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public DownloadSpreadsheetResultSet run() {
		JSONObject result = super.runWithResults();
		return new DownloadSpreadsheetResultSet(result);
	}
	
}
