package pe.soaint.prueba.configuration.constant;

import lombok.Getter;
import lombok.ToString;

/*
* @author  Jhonatan A.
*/
@Getter
@ToString
public enum ActionType {
	SAVE_LOG(1),
	CONSOLE_LOG(2),
	WRITE_LOG(3);
	
	private int id;
	
	ActionType(int id){
		this.id=id;
	}
}
