package com.temboo.Library.Kiva.Loans;

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
GetLenders

Returns a list of public lenders to a loan.
*/
public class GetLenders extends Choreography {

	/**
	Create a new instance of the GetLenders Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public GetLenders(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Kiva/Loans/GetLenders"));
	}

	/** 
	Set the value of the AppID input for this Choreo. 

	@param String - (optional, string) Your unique application ID, usually in reverse DNS notation.
	*/
	public void setAppID(String value) {
		this.inputs.setInput("AppID", value);
	}


	/** 
	Set the value of the LoanID input for this Choreo. 

	@param String - (required, string) The ID of the loan for which to get details.
	*/
	public void setLoanID(String value) {
		this.inputs.setInput("LoanID", value);
	}


	/** 
	Set the value of the Page input for this Choreo. 

	@param Integer - (optional, integer) The page position of results to return. Defaults to 1.
	*/
	public void setPage(Integer value) {
		this.inputs.setInput("Page", value);
	}

	/** 
	Set the value of the Page input for this Choreo as a String. 

	@param String - (optional, integer) The page position of results to return. Defaults to 1.
	*/
	public void setPage(String value) {
		this.inputs.setInput("Page", value);	
	}
	/** 
	Set the value of the ResponseType input for this Choreo. 

	@param String - (optional, string) Output returned can be XML or JSON. Defaults to JSON.
	*/
	public void setResponseType(String value) {
		this.inputs.setInput("ResponseType", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public GetLendersResultSet run() {
		JSONObject result = super.runWithResults();
		return new GetLendersResultSet(result);
	}
	
}
