package com.temboo.Library.Zoho.CRM;

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
ConvertLead

Converts a lead to a potential, account, or contact in your Zoho CRM account.
*/
public class ConvertLead extends Choreography {

	/**
	Create a new instance of the ConvertLead Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public ConvertLead(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Zoho/CRM/ConvertLead"));
	}

	/** 
	Set the value of the Amount input for this Choreo. 

	@param BigDecimal - (conditional, decimal) Corresponds to the Amount field in Zoho. Required if CreatePotential is set to 1.
	*/
	public void setAmount(BigDecimal value) {
		this.inputs.setInput("Amount", value);
	}

	/** 
	Set the value of the Amount input for this Choreo as a String. 

	@param String - (conditional, decimal) Corresponds to the Amount field in Zoho. Required if CreatePotential is set to 1.
	*/
	public void setAmount(String value) {
		this.inputs.setInput("Amount", value);	
	}
	/** 
	Set the value of the AssignTo input for this Choreo. 

	@param String - (optional, string) Corresponds to the Assign To field in Zoho
	*/
	public void setAssignTo(String value) {
		this.inputs.setInput("AssignTo", value);
	}


	/** 
	Set the value of the AuthenticationToken input for this Choreo. 

	@param String - (required, string) A valid authentication token. Permanent authentication tokens can be generated by the GenerateAuthToken Choreo.
	*/
	public void setAuthenticationToken(String value) {
		this.inputs.setInput("AuthenticationToken", value);
	}


	/** 
	Set the value of the ClosingDate input for this Choreo. 

	@param String - (conditional, date) Corresponds to the Closing Date field in Zoho. Formatted like MM/dd/yyyy. Required if CreatePotential is set to 1.
	*/
	public void setClosingDate(String value) {
		this.inputs.setInput("ClosingDate", value);
	}


	/** 
	Set the value of the ContactRole input for this Choreo. 

	@param String - (conditional, string) Corresponds to the Contact Role field in Zoho. Required if CreatePotential is set to 1.
	*/
	public void setContactRole(String value) {
		this.inputs.setInput("ContactRole", value);
	}


	/** 
	Set the value of the CreatePotential input for this Choreo. 

	@param Boolean - (optional, boolean) Whether or not to create a potential
	*/
	public void setCreatePotential(Boolean value) {
		this.inputs.setInput("CreatePotential", value);
	}

	/** 
	Set the value of the CreatePotential input for this Choreo as a String. 

	@param String - (optional, boolean) Whether or not to create a potential
	*/
	public void setCreatePotential(String value) {
		this.inputs.setInput("CreatePotential", value);	
	}
	/** 
	Set the value of the ID input for this Choreo. 

	@param Integer - (required, integer) The ID for the lead that you wish to convert to a potential
	*/
	public void setID(Integer value) {
		this.inputs.setInput("ID", value);
	}

	/** 
	Set the value of the ID input for this Choreo as a String. 

	@param String - (required, integer) The ID for the lead that you wish to convert to a potential
	*/
	public void setID(String value) {
		this.inputs.setInput("ID", value);	
	}
	/** 
	Set the value of the NotifyLeadOwner input for this Choreo. 

	@param Boolean - (optional, boolean) Whether or not to notifiy the lead owner. Defaults to 0 for false.
	*/
	public void setNotifyLeadOwner(Boolean value) {
		this.inputs.setInput("NotifyLeadOwner", value);
	}

	/** 
	Set the value of the NotifyLeadOwner input for this Choreo as a String. 

	@param String - (optional, boolean) Whether or not to notifiy the lead owner. Defaults to 0 for false.
	*/
	public void setNotifyLeadOwner(String value) {
		this.inputs.setInput("NotifyLeadOwner", value);	
	}
	/** 
	Set the value of the NotifyNewEntityOwner input for this Choreo. 

	@param Boolean - (optional, boolean) Whether or not to notify the new entity owner. Defaults to 0 for false.
	*/
	public void setNotifyNewEntityOwner(Boolean value) {
		this.inputs.setInput("NotifyNewEntityOwner", value);
	}

	/** 
	Set the value of the NotifyNewEntityOwner input for this Choreo as a String. 

	@param String - (optional, boolean) Whether or not to notify the new entity owner. Defaults to 0 for false.
	*/
	public void setNotifyNewEntityOwner(String value) {
		this.inputs.setInput("NotifyNewEntityOwner", value);	
	}
	/** 
	Set the value of the PotentialName input for this Choreo. 

	@param String - (conditional, string) Corresponds to the Potential Name field in Zoho. Required if CreatePotential is set to 1.
	*/
	public void setPotentialName(String value) {
		this.inputs.setInput("PotentialName", value);
	}


	/** 
	Set the value of the PotentialStage input for this Choreo. 

	@param String - (conditional, string) Corresponds to the Potential Stage field in Zoho. Required if CreatePotential is set to 1.
	*/
	public void setPotentialStage(String value) {
		this.inputs.setInput("PotentialStage", value);
	}


	/** 
	Set the value of the Probability input for this Choreo. 

	@param Integer - (conditional, integer) Corresponds to the Probability field in Zoho. Required if CreatePotential is set to 1.
	*/
	public void setProbability(Integer value) {
		this.inputs.setInput("Probability", value);
	}

	/** 
	Set the value of the Probability input for this Choreo as a String. 

	@param String - (conditional, integer) Corresponds to the Probability field in Zoho. Required if CreatePotential is set to 1.
	*/
	public void setProbability(String value) {
		this.inputs.setInput("Probability", value);	
	}
	/** 
	Set the value of the ResponseFormat input for this Choreo. 

	@param String - (optional, string) The format that the response should be in. Valid formats are: json and xml (the default).
	*/
	public void setResponseFormat(String value) {
		this.inputs.setInput("ResponseFormat", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public ConvertLeadResultSet run() {
		JSONObject result = super.runWithResults();
		return new ConvertLeadResultSet(result);
	}
	
}
