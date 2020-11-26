package pe.soaint.prueba.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* @author  Jhonatan A.
*/
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LogDto {

	private String message;
	private Integer logTypeId;
	private Integer action;
	
}
