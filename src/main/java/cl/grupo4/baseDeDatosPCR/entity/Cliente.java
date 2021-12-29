package cl.grupo4.baseDeDatosPCR.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="FM_DEVOLUCION_CU")
@Getter @Setter
public class Cliente {
	@Id 
	@GeneratedValue
	private Integer ID_FM_DEVOLUCION_CU;
	@Column(name = "RECLAMO_CFN_CU")
	private String reclamoCfn;
	private String RECLAMO_AREA_FONO;
	private String RECLAMO_FECHA_INICIO;
	private String RECLAMO_HORA_INICIO;
	private String RECLAMO_CU_N3_RESOL;
	private String RECLAMO_FECHA_CIERRE;
	private String RECLAMO_HORA_CIERRE;
	private String RECLAMO_PLANTA;
	private String RECLAMO_CU_CABLE;
	private String RECLAMO_CU_PAR_INI;
	private String RECLAMO_CU_PAR_FINAL;
	private String RECLAMO_AGENCIA;
	private String TIPO_ERROR;
	private String PERIODO;
	private Date FEC_INGRESO;
	private String PCM_TECNO_ACCESO;
	private String RECLAMO_ESTADO;
	

}
