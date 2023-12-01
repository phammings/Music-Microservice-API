package com.eecs3311.profilemicroservice;
/**
 * Represents the status of a database query within the profile microservice.
 * This class holds details about the outcome of a database operation,
 * including a status message, the result of the query execution, and any associated data.
 *
 * <p>The class encapsulates the status message, the result of the query (as an instance of {@link DbQueryExecResult}),
 * and any additional data that might be returned from the database operation.
 * This makes it convenient to convey detailed information about the query's outcome.
 * An instance of the class encapsulates the result of a database operation,
 * and then using getters and setters to retrieve or update the status details as needed.
 */
public class DbQueryStatus {
	
	private String message;
	private DbQueryExecResult dbQueryExecResult;
	private Object data = null;  // Data can be anything returned to the Db

	/**
	 * Constructs a new {@code DbQueryStatus} with the specified message and query execution result.
	 *
	 * @param message The status message associated with the database query.
	 * @param dbQueryExecResult The result of the database query execution, represented as an instance of {@link DbQueryExecResult}.
	 */
	public DbQueryStatus(String message, DbQueryExecResult dbQueryExecResult) {
		this.message = message;
		this.dbQueryExecResult = dbQueryExecResult;
	}

	/**
	 * Returns the status message of the database query.
	 *
	 * @return The status message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the status message of the database query.
	 *
	 * @param message The new status message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the result of the database query execution.
	 *
	 * @return The query execution result, as an instance of {@link DbQueryExecResult}.
	 */
	public DbQueryExecResult getdbQueryExecResult() {
		return dbQueryExecResult;
	}

	/**
	 * Sets the result of the database query execution.
	 *
	 * @param dbQueryExecResult The new query execution result.
	 */
	public void setdbQueryExecResult(DbQueryExecResult dbQueryExecResult) {
		this.dbQueryExecResult = dbQueryExecResult;
	}

	/**
	 * Returns the data associated with the database query.
	 * This can be any type of object returned from the database.
	 *
	 * @return The data object.
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data associated with the database query.
	 *
	 * @param obj The new data object to be associated with the query status.
	 */
	public void setData(Object obj) {
		this.data = obj;
	}

}
