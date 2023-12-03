package com.eecs3311.profilemicroservice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import okhttp3.RequestBody;

import java.util.Map;

/**
 * Utility class providing common functionalities used across the profile microservice.
 * Includes methods for handling HTTP requests and responses.
 *
 * <p>It includes methods for constructing an empty request body, extracting the URL from an HTTP request,
 * and setting the response status and data for server responses.
 */
public class Utils {


	public static RequestBody emptyRequestBody = RequestBody.create(null, "");
	
	// Used to determine path that was called from within each REST route, you don't need to modify this

	/**
	 * Retrieves the full URL from a HttpServletRequest.
	 * This method concatenates the request URL with its query string if it exists.
	 *
	 * @param req The HttpServletRequest from which to extract the URL.
	 * @return The full URL as a String.
	 */
	public static String getUrl(HttpServletRequest req) {
		String requestUrl = req.getRequestURL().toString();
		String queryString = req.getQueryString();

		if (queryString != null) {
			requestUrl += "?" + queryString;
		}
		return requestUrl;
	}
	
	// Sets the response status and data for a response from the server. You might not always be able to use this function
	/**
	 * Sets the response status and data for an HTTP response.
	 * This method is used to construct a ResponseEntity object with the specified
	 * status and data based on the result of a database query.
	 *
	 * @param response The map containing response data to be sent to the client.
	 * @param dbQueryExecResult The result of the database query to determine the HTTP status.
	 * @param data The data to be included in the response body.
	 * @return A ResponseEntity object containing the status code and response body.
	 */
	public static ResponseEntity<Map<String, Object>> setResponseStatus(Map<String, Object> response, DbQueryExecResult dbQueryExecResult, Object data) {	
		HttpStatus status = HttpStatus.NOT_IMPLEMENTED; // default value of HTTP 501 NOT IMPLEMENTED
		switch (dbQueryExecResult) {
			case QUERY_OK:
				status = HttpStatus.OK;
				if (data != null) {
					response.put("data", data);
				}
				break;
			case QUERY_ERROR_NOT_FOUND:
				status = HttpStatus.NOT_FOUND;
				break;
			case QUERY_ERROR_GENERIC:
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
		}
		response.put("status", status);
		
		return ResponseEntity.status(status).body(response);
	}
}