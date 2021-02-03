package cl.grupo4.baseDeDatosPCR.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="pcr")
public class PCR {

	protected static final String ALUMNOS_SEQ = "alumnos_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ALUMNOS_SEQ)
	@SequenceGenerator(sequenceName = ALUMNOS_SEQ, allocationSize = 1, name = "ALUMNOS_SEQ")
	
	private Integer id;
	private String nombre;
	private String apellido;
	@Column (unique = true)
	private String rut;
	private String direccion;	
	private Integer telefono;
	private String correo;
	@Column (name = "confirmacion_correo")
	private String confirmacionCorreo;
	@Column(name = "telefono_secundario")
	private Integer telefonoSecundario;
	@Column(name = "villa_poblacion")
	private String villaPoblacion;
	@Column(name = "fecha_examen")
	private Date fechaExamen;
	private String hora;
	private Integer edad;
	@Column(name = "lugar_toma_examen")
	private String lugarTomaExamen;
	private String resultado;
	@Column(name = "comuna_de_residencia")
	private String comunaDeResidencia;
	private Boolean altoRiesgo;
	

}
