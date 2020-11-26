package pe.soaint.prueba.configuration.service;

import pe.soaint.prueba.exception.ServiceException;
import pe.soaint.prueba.model.dto.LogDto;
/*
* @author  Jhonatan A.
*/
public interface LogService {
	boolean saveLogMessage(LogDto logDto) throws ServiceException;
}
