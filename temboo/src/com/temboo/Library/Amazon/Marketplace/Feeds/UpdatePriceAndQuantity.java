package com.temboo.Library.Amazon.Marketplace.Feeds;

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
UpdatePriceAndQuantity

Updates only the price and quantity of a particular product with a given product SKU.
*/
public class UpdatePriceAndQuantity extends Choreography {

	/**
	Create a new instance of the UpdatePriceAndQuantity Choreo. A TembooSession object, containing a valid
	set of Temboo credentials, must be supplied.
	*/
	public UpdatePriceAndQuantity(TembooSession session) {
		super(session, TembooPath.pathFromStringNoException("/Library/Amazon/Marketplace/Feeds/UpdatePriceAndQuantity"));
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
	Set the value of the Endpoint input for this Choreo. 

	@param String - (conditional, string) The base URL for the MWS endpoint. Defaults to mws.amazonservices.co.uk.
	*/
	public void setEndpoint(String value) {
		this.inputs.setInput("Endpoint", value);
	}


	/** 
	Set the value of the Price input for this Choreo. 

	@param BigDecimal - (required, decimal) Enter the unit price for this product. The price must be greater than 0.00. Do NOT include the currency symbol (e.g. $).
	*/
	public void setPrice(BigDecimal value) {
		this.inputs.setInput("Price", value);
	}

	/** 
	Set the value of the Price input for this Choreo as a String. 

	@param String - (required, decimal) Enter the unit price for this product. The price must be greater than 0.00. Do NOT include the currency symbol (e.g. $).
	*/
	public void setPrice(String value) {
		this.inputs.setInput("Price", value);	
	}
	/** 
	Set the value of the Quantity input for this Choreo. 

	@param Integer - (required, integer) Enter the quantity of the product you have for sale. The quantity must be a whole number, and should be greater than zero.
	*/
	public void setQuantity(Integer value) {
		this.inputs.setInput("Quantity", value);
	}

	/** 
	Set the value of the Quantity input for this Choreo as a String. 

	@param String - (required, integer) Enter the quantity of the product you have for sale. The quantity must be a whole number, and should be greater than zero.
	*/
	public void setQuantity(String value) {
		this.inputs.setInput("Quantity", value);	
	}
	/** 
	Set the value of the SKU input for this Choreo. 

	@param String - (required, string) A SKU is a "Stock Keeping Unit" which you can assign to your products to track your inventory. Provide the SKU that you want to modify.
	*/
	public void setSKU(String value) {
		this.inputs.setInput("SKU", value);
	}


	/** 
	Set the value of the TimeToWait input for this Choreo. 

	@param Integer - (optional, integer) By default, the Choreo will wait for 10 minutes to see if the report is ready for retrieval. Max is 120 minutes.
	*/
	public void setTimeToWait(Integer value) {
		this.inputs.setInput("TimeToWait", value);
	}

	/** 
	Set the value of the TimeToWait input for this Choreo as a String. 

	@param String - (optional, integer) By default, the Choreo will wait for 10 minutes to see if the report is ready for retrieval. Max is 120 minutes.
	*/
	public void setTimeToWait(String value) {
		this.inputs.setInput("TimeToWait", value);	
	}
	
	/**
	 * Execute the Choreo, wait for the Choreo to complete 
	 * and return a ResultSet containing the execution results.
	 */
	@Override
	public UpdatePriceAndQuantityResultSet run() {
		JSONObject result = super.runWithResults();
		return new UpdatePriceAndQuantityResultSet(result);
	}
	
}