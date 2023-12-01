package com.eecs3311.profilemicroservice;

/**
 * Represents the result of a database query execution in the profile microservice.
 * This enumeration defines the various possible outcomes of a database operation.
 *
 * <p>Each constant in this enumeration corresponds to a sucess or specific type of failure
 * that a database query might return. This allows for more readable and maintainable
 * code by providing predefined, meaningful names for common database query outcomes.
 *
 * <p>Enum Values:
 * <ul>
 *     <li>{@code QUERY_OK} - Indicates that the database query was executed successfully.</li>
 *     <li>{@code QUERY_ERROR_NOT_FOUND} - Indicates that the query execution did not find
 *     the requested data in the database.</li>
 *     <li>{@code QUERY_ERROR_GENERIC} - Represents a generic error during the query
 *     execution, used for unspecified or unexpected errors.</li>
 * </ul>
 */
public enum DbQueryExecResult {
	QUERY_OK,
	QUERY_ERROR_NOT_FOUND,
	QUERY_ERROR_GENERIC,
}
