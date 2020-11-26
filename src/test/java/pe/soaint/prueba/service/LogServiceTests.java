package pe.soaint.prueba.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pe.soaint.prueba.configuration.service.LogService;
import pe.soaint.prueba.exception.ServiceException;
import pe.soaint.prueba.model.dto.LogDto;

/*
* @author  Jhonatan A.
*/
@SpringBootTest
class LogServiceTests {

	@Autowired
	private LogService logService;
	
	/*
	 * ACCIONES
	 * 1: SAVE_LOG
	 * 2: CONSOLE_LOG
	 * 3: WRITE_LOG
	 * 
	 * LOG TYPE ID
	 * 1: INFO
	 * 2: WARNING
	 * 3: ERROR
	 * */
	
	
	@Test
	@DisplayName("debería registrar en la base de datos de tipo info")
	void shouldRegisterInTheDatabaseOfTypeInfo() throws ServiceException {
				
		//Preparar el dto
		LogDto logDto = LogDto.builder()
				.message("Este es un mensaje de INFO desde JUNIT")
				.action(1)
				.logTypeId(1)
				.build();
		
		boolean process = logService.saveLogMessage(logDto);
		
		assertEquals(true, process);
	}
	
	@Test
	@DisplayName("debería mostar por consola tipo warning")
	void shouldShowTheConsoleTypeWarning() throws ServiceException {
				
		//Preparar el dto
		LogDto logDto = LogDto.builder()
				.message("Este es un mensaje de WARNING desde JUNIT")
				.action(2)
				.logTypeId(2)
				.build();
		
		boolean process = logService.saveLogMessage(logDto);
		
		assertEquals(true, process);
	}
	
	@Test
	@DisplayName("debería escribir el mensaje en un txt de tipo error")
	void shouldwriteTheMessageInATxtOfTypeError() throws ServiceException {
				
		//Preparar el dto
		LogDto logDto = LogDto.builder()
				.message("Este es un mensaje de ERROR desde JUNIT")
				.action(3)
				.logTypeId(3)
				.build();
		
		boolean process = logService.saveLogMessage(logDto);
		
		assertEquals(true, process);
	}
	
}
