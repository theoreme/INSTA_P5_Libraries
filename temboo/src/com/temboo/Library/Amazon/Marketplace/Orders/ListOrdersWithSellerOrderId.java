package com.temboo.Library.Amazon.Marketplace.Orders;

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
ListOrdersWithSellerOrderId

Returns orders associated with a seller order id that you specify.
*/
public class ListOrdersWithSellerOrderId extends Choreography {

	/**
	Create a new instance of the ListOrdersWithSellerOrderId Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public ListOrdersWithSellerOrderId(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Amazon/Marketplace/Orders/ListOrdersWithSellerOrderId"));
	}

	/** 
	Set the value of the AWSAccessKeyId input for this Choreo. 

	@param String - (required, string) The Access Key ID provided by Amazon Web Services.
	*/
	public void setAWSAccessKeyId(String value) {
		this.inputs.setInput("AWSAccessKeyId", value);
	}


	/** 
	Set the value of the AWSMarketplaceId input for this Choreo. 

	@param String - (required, string) The Marketplace ID provided by Amazon Web Services.
	*/
	public void setAWSMarketplaceId(String value) {
		this.inputs.setInput("AWSMarketplaceId", value);
	}


	/** 
	Set the value of the AWSMerchantId input for this Choreo. 

	@param String - (required, string) The Merchant ID provided by Amazon Web Services.
	*/
	public void setAWSMerchantId(String value) {
		this.inputs.setInput("AWSMerchantId", value);
	}


	/** 
	Set the value of the AWSSecretKeyId input for this Choreo. 

	@param String - (required, string) The Secret Key ID provided by Amazon Web Services.
	*/
	public void setAWSSecretKeyId(String value) {
		this.inputs.setInput("AWSSecretKeyId", value);
	}


	/** 
	Set the value of the CreatedAfter input for this Choreo. 

	@param String - (optional, date) A date used for selecting orders created after (or at) a specified time, in ISO 8601 date format (i.e. 2012-01-01). Defaults to today's date if not provided.
	*/
	public void setCreatedAfter(String value) {
		this.inputs.setInput("CreatedAfter", value);
	}


	/** 
	Set the value of the CreatedBefore input for this Choreo. 

	@param String - (optional, date) A date used for selecting orders created before (or at) a specified time, in ISO 8601 date format (i.e. 2012-01-01).
	*/
	public void setCreatedBefore(String value) {
		this.inputs.setInput("CreatedBefore", value);
	}


	/** 
	Set the value of the Endpoint input for this Choreo. 

	@param String - (conditional, string) The base URL for the MWS endpoint. Defaults to mws.amazonservices.co.uk.
	*/
	public void setEndpoint(String value) {
		this.inputs.setInput("Endpoint", value);
	}


	/** 
	Set the value of the MaxResultsPerPage input for this Choreo. 

	@param Integer - (optional, integer) A number that indicates the maximum number of orders that can be returned per page. Valid values are: 1-100.
	*/
	public void setMaxResultsPerPage(Integer value) {
		this.inputs.setInput("MaxResultsPerPage", value);
	}

	/** 
	Set the value of the MaxResultsPerPage input for this Choreo as a String. 

	@param String - (optional, integer) A number that indicates the maximum number of orders that can be returned per page. Valid values are: 1-100.
	*/
	public void setMaxResultsPerPage(String value) {
		this.inputs.setInput("MaxResultsPerPage", value);	
	}
	/** 
	Set the value of the ResponseFormat input for this Choreo. 

	@param String - (optional, string) The format that the response should be in. Valid values are "xml" (the default) and "json".
	*/
	public void setResponseFormat(String value) {
		this.inputs.setInput("ResponseFormat", value);
	}


	/** 
	Set the value of the SellerOrderId input for this Choreo. 

	@param String - (required, string) An order identifier that is specified by the seller.
	*/
	public void setSellerOrderId(String value) {
		this.inputs.setInput("SellerOrderId", value);
	}


	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public ListOrdersWithSellerOrderIdResultSet run() {
		JSONObject result = super.runWithResults();
		return new ListOrdersWithSellerOrderIdResultSet(result);
	}
	
}