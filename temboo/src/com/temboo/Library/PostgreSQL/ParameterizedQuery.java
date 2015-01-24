package com.temboo.Library.PostgreSQL;

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
ParameterizedQuery

Allows you to run a parameterized query.
*/
public class ParameterizedQuery extends Choreography {

	/**
	Create a new instance of the ParameterizedQuery Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public ParameterizedQuery(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/PostgreSQL/ParameterizedQuery"));
	}

	/** 
	Set the value of the DatabaseName input for this Choreo. 

	@param String - (required, string) The name of the database to connect to.
	*/
	public void setDatabaseName(String value) {
		this.inputs.setInput("DatabaseName", value);
	}


	/** 
	Set the value of the Password input for this Choreo. 

	@param String - (required, string) The password for the database user.
	*/
	public void setPassword(String value) {
		this.inputs.setInput("Password", value);
	}


	/** 
	Set the value of the Port input for this Choreo. 

	@param Integer - (optional, integer) The database port. Defaults to 5432.
	*/
	public void setPort(Integer value) {
		this.inputs.setInput("Port", value);
	}

	/** 
	Set the value of the Port input for this Choreo as a String. 

	@param String - (optional, integer) The database port. Defaults to 5432.
	*/
	public void setPort(String value) {
		this.inputs.setInput("Port", value);	
	}
	/** 
	Set the value of the ResponseFormat input for this Choreo. 

	@param String - (optional, string) The preferred format for the database results. Accepted formats are json (the default) and xml. This input only applies when providing a SELECT statement for the SQL input.
	*/
	public void setResponseFormat(String value) {
		this.inputs.setInput("ResponseFormat", value);
	}


	/** 
	Set the value of the SQL input for this Choreo. 

	@param String - (required, string) A SQL statement that includes placholders (e.g., select * from users where id = ?). Items in the Values input will be sanitized and substituted in for placeholders.
	*/
	public void setSQL(String value) {
		this.inputs.setInput("SQL", value);
	}


	/** 
	Set the value of the Server input for this Choreo. 

	@param String - (required, string) The name or IP address of the database server.
	*/
	public void setServer(String value) {
		this.inputs.setInput("Server", value);
	}


	/** 
	Set the value of the Username input for this Choreo. 

	@param String - (required, string) The database username.
	*/
	public void setUsername(String value) {
		this.inputs.setInput("Username", value);
	}


	/** 
	Set the value of the Values input for this Choreo. 

	@param String - (required, json) The values to be sanitized and passed into the parameterized SQL statement.
	*/
	public void setValues(String value) {
		this.inputs.setInput("Values", value);
	}


	/** 
	Set the value of the Version input for this Choreo. 

	@param Integer - (optional, integer) The version of the Postgres database. Allowed values are 8 and 9 (the default).
	*/
	public void setVersion(Integer value) {
		this.inputs.setInput("Version", value);
	}

	/** 
	Set the value of the Version input for this Choreo as a String. 

	@param String - (optional, integer) The version of the Postgres database. Allowed values are 8 and 9 (the default).
	*/
	public void setVersion(String value) {
		this.inputs.setInput("Version", value);	
	}
	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public ParameterizedQueryResultSet run() {
		JSONObject result = super.runWithResults();
		return new ParameterizedQueryResultSet(result);
	}
	
}
