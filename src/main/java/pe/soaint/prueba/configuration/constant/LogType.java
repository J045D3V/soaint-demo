package pe.soaint.prueba.configuration.constant;

import java.util.Arrays;

import lombok.Getter;
import lombok.ToString;

/*
* @author  Jhonatan A.
*/
@Getter
@ToString
public enum LogType {

	INFO(1,"INFO"),
	WARNING(2,"WARNING"),
	ERROR(3,"ERROR");
	
	private int id;
	private String name;
	
	LogType(int id,String name){
		this.id=id;
		this.name=name;
	}

	public static String getName(int logTypeId) {
				
		
		return Arrays.stream(LogType.values())
	            .filter(e -> e.getId() == logTypeId).map(e -> e.getName()).findFirst().orElse("");
	}
}
