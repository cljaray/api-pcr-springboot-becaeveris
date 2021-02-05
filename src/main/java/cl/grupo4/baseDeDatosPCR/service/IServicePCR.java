package cl.grupo4.baseDeDatosPCR.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cl.grupo4.baseDeDatosPCR.entity.PCR;

public interface IServicePCR {

	/** Metodo para crear pcr que recibe entidad de tipo PCR y retorna nuevo PCR.
	 * @param nuevoPCR
	 * @return nuevoPCR
	 */
	PCR crearPCR(PCR nuevoPCR);

	/**Metodo que devuelve una lista con todos los PCR en la base de datos.
	 * @return lista de PCR
	 */
	List<PCR> buscarTodosPCR();

	/**Metodo que permite la busqueda de PCR mediante identificacion Rut.
	 * @param rut
	 * @return PCR
	 */
	PCR buscarPorRut(String rut);

	/**Metodo que permite la busqueda de PCR mediante nombre o apellido, este metodo esta configurado para buscar palabras que contengan los parametros ingresado.
	 * @param nombre
	 * @param apellido
	 * @return lista PCR
	 */
	List<PCR> buscarNombreApellido(String nombre, String apellido);

	/**Metodo que permite actualizar PCR mediante Rut.
	 * @param pcrActualizado
	 * @param rut
	 * @return PCR actualizado
	 */
	PCR actualizarPCR(PCR pcrActualizado, String rut);

	/**Metodo que permite la eliminación de PCR mediante Rut.
	 * @param rut
	 * @return Map con mensaje de exito
	 */
	Map<String, String> eliminarPCR(String rut);

	/**Metodo que permite buscar los estados de Resultado de Examen, mediante la entrega de la palabra clave a buscar (positivo, negativo, pendiente).
	 * @param resultado
	 * @return Lista de PCR
	 */
	List<PCR> checkearResultados(String resultado);

	/**Metodo que permite la busqueda de PCR por comuna de residencia del paciente.
	 * @param comuna
	 * @return Lista de PCR
	 */
	List<PCR> buscarPorComuna(String comuna);

	/**Metodo que permite buscar a todos los de alto riesgo.
	 * @return Lista PCR
	 */
	List<PCR> pacientesAltoRiesgo();

	/**Metodo que valida si rut esta escrito de la manera indicada(11.111.111-1).
	 * @param rut	  
	 */
	void validarRut(String rut);

	/**Metodo que valida la existencia de rut, y en caso de que exista, ejecuta una excepcion.
	 * @param rut
	 */
	void validarRutExistente(String rut);

	/**Metodo que valida la existe de rut, y en caso de no existencia, ejecuta una excepcion.
	 * @param rut
	 */
	void validarRutParaModificar(String rut);

	/**
	 * Metodo que retorna valor booleano para verificar la existencia de un rut en la base de datos.
	 * @param rut
	 * @return Boolean
	 */
	Boolean validarRutExistenteDirectoFrontEnd(String rut);

	/**
	 * Metodo que recibe un objeto String para verificar si sus valores son válidos. 
	 * @param valorInput
	 */
	void validarDatosString(String valorInput);

	/**
	 * Metodo que recibe un objeto Integer para verificar si sus valores son válidos. 
	 * @param valorInput
	 */
	void validarDatosInteger(Integer valorInput);

	/**
	 * Metodo que recibe un objeto Integer para verificar si sus valores son válidos en referencia a las restricciones de valor de edad. 
	 * @param edad
	 */
	void validarEdad(Integer edad);

	/**
	 *  Metodo que recibe un objeto Integer para verificar si sus valores son válidos en referencia a las restricciones de valor telefono.
	 * @param telefono
	 */
	void validarTelefono(Integer telefono);

	/**
	 *  Metodo que recibe un objetos String para verificar si sus valores son válidos e iguales entre si.
	 * @param correo
	 * @param confirmacionCorreo
	 */
	void validarCorreo(String correo, String confirmacionCorreo);

	/**
	 *  Metodo que recibe un objeto String para verificar si sus valores son válidos en referencia a las 
	 *  restricciones del formato del valor hora.
	 * @param hora
	 */
	void validarHora(String hora);

	/**
	 *  Metodo que recibe un objeto String para verificar si el valor resultado corresponde a alguno de los valores constantes.
	 * @param resultado
	 * @return
	 */
	Boolean validarResultadoExamen(String resultado);

	/**
	 *  Metodo que recibe un objeto Boolean para verificar si su valor es válido.
	 * @param valorInput
	 */
	void validarDatosBooleanos(Boolean valorInput);

	/**Metodo que valida los parametros de entrada al agregar un nuevo PCR o actualizar un PCR existente.
	 * @param pcr
	 */
	void validarPCR(PCR pcr);

}