package pe.soaint.prueba.configuration.peristence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* @author  Jhonatan A.
*/
@Entity
@Table(name="logs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Log implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 321932059188586053L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="log_type_id")
	private Integer logTypeId;
	
	@Column(name="message")
	private String message;
	
	@Column(name="reg_date")	
	private LocalDateTime regDate;
		
}
