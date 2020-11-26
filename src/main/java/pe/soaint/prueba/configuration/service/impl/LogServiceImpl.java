package pe.soaint.prueba.configuration.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import pe.soaint.prueba.configuration.constant.ActionType;
import pe.soaint.prueba.configuration.constant.LogType;
import pe.soaint.prueba.configuration.peristence.entity.Log;
import pe.soaint.prueba.configuration.service.LogService;
import pe.soaint.prueba.exception.ServiceException;
import pe.soaint.prueba.model.dto.LogDto;
import pe.soaint.prueba.persistence.repository.LogRepository;

/*
* @author  Jhonatan A.
*/
@Slf4j
@Service
public class LogServiceImpl implements LogService {

	@Value("${app.log.directoryPath}")
	private String directoryPath;
	
	@Value("${app.log.fileName}")
	private String fileName;

	private LogRepository logRepository;

	public LogServiceImpl(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	public boolean saveLogMessage(LogDto logDto) throws ServiceException {

		// validar que todos los campos esten llenos;
		validateDto(logDto);

		// segun la accion
		showAction(logDto);
		
		return true;
	}

	private void validateDto(LogDto logDto) throws ServiceException {

		if (logDto == null) {
			throw new ServiceException("LogDto no puede ser nulo");
		}

		if (logDto.getAction() == null || logDto.getAction() <= 0) {
			throw new ServiceException("action no puede ser nulo");
		}
		
		if (!StringUtils.hasText(logDto.getMessage())) {
			throw new ServiceException("message no puede ser nulo");
		}
		
		if (logDto.getLogTypeId() == null || logDto.getLogTypeId() <= 0) {
			throw new ServiceException("logTypeId no puede ser nulo");
		}

	}

	private void saveLog(LogDto logDto) throws ServiceException {

		Log logEntity = Log.builder()
				.message(logDto.getMessage())
				.regDate(LocalDateTime.now())
				.logTypeId(logDto.getLogTypeId())
				.build();

		try {
			logRepository.save(logEntity);
			log.info("Se registro en la base de datos satisfactoriamente logType: "+LogType.getName(logDto.getLogTypeId().intValue()));
		} catch (Exception e) {
			throw new ServiceException("No puedo registrar el log en la base de datos");
		}
	}

	private void showAction(LogDto logDto) throws ServiceException {
		
		if (logDto.getAction().intValue() == ActionType.CONSOLE_LOG.getId()) {
			printConsole(logDto);
			return;
		}

		if (logDto.getAction().intValue() == ActionType.SAVE_LOG.getId()) {
			saveLog(logDto);
			return;
		}
		
		if (logDto.getAction().intValue() == ActionType.WRITE_LOG.getId()) {
			writeLog(logDto);
			return;
		}
	}

	private void writeLog(LogDto logDto) throws ServiceException {
		String fileTxt = directoryPath+File.separator+fileName;
		
		File file = new File(fileTxt);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new ServiceException("No puedo crear el archivo donde se registraran los logs: "+fileTxt);
			}
		}
		
		Path path = Paths.get(fileTxt);
		
		String newLog = new StringBuilder()
				.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.append("\t-\t")
				.append(LogType.getName(logDto.getLogTypeId().intValue()))
				.append("\t-\t")
				.append(logDto.getMessage())
				.append("\r\n")
				.toString();
	     
		try {
			 Files.write(path, newLog.getBytes(),
			            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			log.info("Log generado en "+fileTxt);
			
		} catch (IOException e) {
			throw new ServiceException("No pudo escribir el log en el archivo: "+fileTxt);
		}
		
	}

	private void printConsole(LogDto logDto) throws ServiceException {
		if(logDto.getLogTypeId().intValue()==LogType.INFO.getId()) {
			log.info(logDto.getMessage());
			return;
		}
		
		if(logDto.getLogTypeId().intValue()==LogType.WARNING.getId()) {
			log.warn(logDto.getMessage());
			return;
		}
		
		if(logDto.getLogTypeId().intValue()==LogType.ERROR.getId()) {
			log.error(logDto.getMessage());
			return;
		}
		
		throw new ServiceException("No existe el tipo de log: "+ logDto.getLogTypeId());
	}

}
