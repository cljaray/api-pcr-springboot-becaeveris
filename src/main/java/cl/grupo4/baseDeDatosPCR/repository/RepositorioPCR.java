package cl.grupo4.baseDeDatosPCR.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import cl.grupo4.baseDeDatosPCR.entity.PCR;

/**Clase que lista metodos de peticiones a la base de datos.
 * @author Grupo 4
 *
 */
public interface RepositorioPCR extends JpaRepository<PCR, Integer>{
	
	/**
	 * @param rut
	 * @return
	 */
	PCR findByRut(String rut);
	List<PCR> findByNombreIgnoreCaseLikeOrApellidoIgnoreCaseLike(String nombre, String apellido);
	List<PCR> findByResultadoIgnoreCase(String resultado);	
	List<PCR> findByComunaDeResidencia(String comuna);
	List<PCR> findByAltoRiesgo(Boolean estado);
	Boolean existsByRut(String rut);
}
