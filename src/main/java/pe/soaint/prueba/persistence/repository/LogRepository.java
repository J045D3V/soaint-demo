package pe.soaint.prueba.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.soaint.prueba.configuration.peristence.entity.Log;
/*
* @author  Jhonatan A.
*/
public interface LogRepository extends JpaRepository<Log, Integer>{

}
