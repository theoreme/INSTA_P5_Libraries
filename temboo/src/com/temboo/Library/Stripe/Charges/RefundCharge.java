package com.temboo.Library.Stripe.Charges;

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
RefundCharge

Issues a refund of an existing credit card charge.
*/
public class RefundCharge extends Choreography {

	/**
	Create a new instance of the RefundCharge Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public RefundCharge(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Stripe/Charges/RefundCharge"));
	}

	/** 
	Set the value of the APIKey input for this Choreo. 

	@param String - (required, string) The API Key provided by Stripe
	*/
	public void setAPIKey(String value) {
		this.inputs.setInput("APIKey", value);
	}


	/** 
	Set the value of the Amount input for this Choreo. 

	@param Integer - (optional, integer) The amount to refund to the customer in cents. When left empty, the entire charge is refunded.
	*/
	public void setAmount(Integer value) {
		this.inputs.setInput("Amount", value);
	}

	/** 
	Set the value of the Amount input for this Choreo as a String. 

	@param String - (optional, integer) The amount to refund to the customer in cents. When left empty, the entire charge is refunded.
	*/
	public void setAmount(String value) {
		this.inputs.setInput("Amount", value);	
	}
	/** 
	Set the value of the ChargeID input for this Choreo. 

	@param String - (required, string) The unique identifier of the charge to be refunded
	*/
	public void setChargeID(String value) {
		this.inputs.setInput("ChargeID", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public RefundChargeResultSet run() {
		JSONObject result = super.runWithResults();
		return new RefundChargeResultSet(result);
	}
	
}
