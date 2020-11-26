package pe.soaint.prueba.exception;

import lombok.Getter;
import lombok.ToString;

/*
* @author  Jhonatan A.
*/
@Getter
@ToString
public class ServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3457273698180680512L;
	private final String message;
	
	public ServiceException(String message) {
		super(message);
		this.message=message;
	}
}
