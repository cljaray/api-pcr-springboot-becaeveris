package cl.grupo4.baseDeDatosPCR.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import cl.grupo4.baseDeDatosPCR.entity.Cliente;


/**Clase que lista metodos de peticiones a la base de datos.
 * @author Grupo 4
 *
 */
public interface RepositorioCliente extends JpaRepository<Cliente, Integer>{
	
	/**
	 * @param rut
	 * @return
	 */
	List<Cliente> findByReclamoCfn(String cfn);
}
