package cl.grupo4.baseDeDatosPCR.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import cl.grupo4.baseDeDatosPCR.entity.PCR;
import cl.grupo4.baseDeDatosPCR.repository.RepositorioPCR;

/**
 * Clase que expone servicios para ser usados por el controlador y acceder a la base de datos.
 * @author wladi
 *
 */
@Service
public class ServicioPCR implements IServicioPCR {
	@Autowired
	private RepositorioPCR repositorioPCR;
	
	//Creacion de constante con el valor "pendiente", para metodos de validacion y busqueda
	protected static final String PENDIENTE = "pendiente"; 
	//Creacion de constante con el valor "positivo", para metodos de validacion y busqueda
	protected static final String POSITIVO = "positivo";
	//Creacion de constante con el valor "negativo", para metodos de validacion y busqueda
	protected static final String NEGATIVO = "negativo";
	
	/**Metodo para crear pcr que recibe entidad de tipo PCR y retorna nuevo PCR.
	 * @param nuevoPCR
	 * @return nuevoPCR
	 */
	@Override
	public PCR crearPCR(PCR nuevoPCR) {
		//Se asigna nueva fecha a nuevoPCR
		nuevoPCR.setFechaExamen(new Date());
		
		//Validacion de rut
		validarRutExistente(nuevoPCR.getRut());
		//validacion de todos los parametros de nuevoPCR
		validarPCR(nuevoPCR);
		
		//Se retorna nuevoPCR en base de datos
		return repositorioPCR.save(nuevoPCR);
	}
	
	/**Metodo que devuelve una lista con todos los PCR en la base de datos.
	 * @return lista de PCR
	 */
	@Override
	public List<PCR> buscarTodosPCR(){
		//Encuentra todos los PCR en la base de datos
		return repositorioPCR.findAll();		
	}
	
	
	/**Metodo que permite la busqueda de PCR mediante identificacion Rut.
	 * @param rut
	 * @return PCR
	 */
	@Override
	public PCR buscarPorRut(String rut){	
		//Realiza busqueda de PCR por Rut
		return repositorioPCR.findByRut(rut);
	}
	
	
	/**Metodo que permite la busqueda de PCR mediante nombre o apellido, este metodo esta configurado para buscar palabras que contengan los parametros ingresado.
	 * @param nombre
	 * @param apellido
	 * @return lista PCR
	 */
	@Override
	public List<PCR> buscarNombreApellido(String nombre, String apellido){
		//Se define el tipo de sintaxis de LIKE de SQL para encontrar terminos que contengan nombre o apellido
		String busquedaLikeNombre = "%" + nombre + "%";  
		String busquedaLikeApellido = "%" + apellido + "%"; 
		//se realiza busqueda
		return repositorioPCR.findByNombreIgnoreCaseLikeOrApellidoIgnoreCaseLike(busquedaLikeNombre, busquedaLikeApellido);
	}
	
	
	
	/**Metodo que permite actualizar PCR mediante Rut.
	 * @param pcrActualizado
	 * @param rut
	 * @return PCR actualizado
	 */
	@Override
	public PCR actualizarPCR(PCR pcrActualizado, String rut) {
		//se valida rut
		validarRut(rut);
		//se validan todos los datos de PCR actualizado
		validarPCR(pcrActualizado);
		//Se busca PCR por rut
		PCR pcr = repositorioPCR.findByRut(rut);
		//Se asigna pcr actualizado a pcr original
		pcr = pcrActualizado;
		//se guarda pcr actualizado y se retorna
		return repositorioPCR.save(pcr);
	}
	
	
	/**Metodo que permite la eliminación de PCR mediante Rut.
	 * @param rut
	 * @return Map con mensaje de exito
	 */
	@Override
	public Map<String,String> eliminarPCR(String rut) {
		//Se valida rut
		validarRutParaModificar(rut);
		//Se busca PCR por rut
		PCR pcrEliminado = repositorioPCR.findByRut(rut.trim());		
		//Se elimina PCR		
		repositorioPCR.delete(pcrEliminado);
		//Se crea variable mensaje
		Map<String,String> mensaje = new HashMap<String,String>();
		//Se asignan valores a mensaje
		mensaje.put("Mensaje", "Borrado exitosamente");
		//se retorna mensaje
		return mensaje;
	}
	
	/**Metodo que permite buscar los estados de Resultado de Examen, mediante la entrega de la palabra clave a buscar (positivo, negativo, pendiente).
	 * @param resultado
	 * @return Lista de PCR
	 */
	@Override
	public List<PCR> checkearResultados(String resultado){	
		//Se crea variable busqueda
		String busqueda = "";
		//Se crea condicional para obtener de busqueda y asignar a variable
		if(resultado.toLowerCase().equals(POSITIVO)) {
			busqueda = POSITIVO;
		} else if(resultado.toLowerCase().equals(NEGATIVO)) {
			busqueda = NEGATIVO;
		} else if(resultado.toLowerCase().equals(PENDIENTE)) {
			busqueda = PENDIENTE;
		} else {
			throw new RuntimeException("no valido");
		}
		//se retorna lista con la busqueda realizada
		return repositorioPCR.findByResultadoIgnoreCase(busqueda);
	}
		
	/**Metodo que permite la busqueda de PCR por comuna de residencia del paciente.
	 * @param comuna
	 * @return Lista de PCR
	 */
	@Override
	public List<PCR> buscarPorComuna(String comuna){
		return repositorioPCR.findByComunaDeResidencia(comuna);
	}
	
	/**Metodo que permite buscar a todos los de alto riesgo.
	 * @return Lista PCR
	 */
	@Override
	public List<PCR> pacientesAltoRiesgo(){
		return repositorioPCR.findByAltoRiesgo(true);
	}
	
	
	/**Metodo que valida si rut esta escrito de la manera indicada(11.111.111-1).
	 * @param rut	  
	 */
	@Override
	public void validarRut(String rut) {
		
		this.validarDatosString(rut);
		
		//Se valida rut mediante expresion regular, si no coincide, envia error
		if(!Pattern.matches("^(\\d{2}\\.\\d{3}\\.\\d{3}-)([a-zA-Z]{1}$|\\d{1}$)",rut.trim())) {
			throw new RuntimeException("Rut no válido");
		} 
	}
	
	
	/**Metodo que valida la existencia de rut, y en caso de que exista, ejecuta una excepcion.
	 * @param rut
	 */
	@Override
	public void validarRutExistente(String rut) {
		validarRut(rut);
		//se valida la existencia de rut, y manda error si existe
		if (repositorioPCR.existsByRut(rut)) {
			throw new RuntimeException("Este rut ya existe");		
		}
	}
	
	/**Metodo que valida la existe de rut, y en caso de no existencia, ejecuta una excepcion.
	 * @param rut
	 */
	@Override
	public void validarRutParaModificar(String rut) {
		validarRut(rut);
		//se valida la existencia de rut, y manda error si no existe
		if (!repositorioPCR.existsByRut(rut)) {
			throw new RuntimeException("Este rut no existe");		
		}
	}
	
	public void validarDatosString(String valorInput) {
		if(valorInput.trim() == null || valorInput.trim().isBlank()) {
			throw new RuntimeException("No se pueden ingresar campos vacios, por favor ingrese la informacion requerida.");
		}		
	}
	
	public void validarDatosInteger(Integer valorInput) {
		
	}
	
	public void validarEdad(Integer edad) {
		if (edad == null || edad < 0 || edad >= 150) {	
			throw new RuntimeException("Porfavor ingresa una edad valida");	
		}
	}
	
	public void validarTelefono(Integer telefono) {
		if(telefono == null || String.valueOf(telefono).length() != 9){			
			throw new RuntimeException("Numero de telefono invalido");
		}
	}
	
	public void validarCorreo(String correo, String confirmacionCorreo) {
		
		this.validarDatosString(correo);
		
		this.validarDatosString(confirmacionCorreo);
		
		if(!correo.trim().equals(confirmacionCorreo.trim())) {
			throw new RuntimeException("Porfavor revisa que tu correo este correcto");
		}
		
	}
	
	public void validarHora(String hora) {		
		this.validarDatosString(hora);
		
		if (!Pattern.matches("^(1?[0-9]|2[0-3]):[0-5][0-9]$", hora.trim())) {
			throw new RuntimeException("Porfavor ingresa una hora en formato HH:MM");
		}
		
	}
	
	public void validarResultadoExamen(String resultado) {
		this.validarDatosString(resultado);
		
		if(resultado != ServicioPCR.PENDIENTE || resultado != ServicioPCR.POSITIVO || resultado != ServicioPCR.NEGATIVO) {
			throw new RuntimeException("Porfavor selecciona un valor");
		}
	}
	
	public void validarDatosBooleanos(Boolean valorInput) {
		if(valorInput == null) {
			throw new RuntimeException("Por favor selecione verdadero o falso");
		}
	}
	/**Metodo que valida los parametros de entrada al agregar un nuevo PCR o actualizar un PCR existente.
	 * @param pcr
	 */

	@Override
	public void validarPCR(PCR pcr) {
		//Se validan todos los parametros de entidad PCR 
		if(pcr == null) {
			throw new RuntimeException("No se ingresaron datos.");
		}		
		
		this.validarDatosString(pcr.getNombre());
		this.validarDatosString(pcr.getApellido());
		this.validarDatosString(pcr.getDireccion());
		this.validarDatosString(pcr.getComunaDeResidencia());
		this.validarDatosString(pcr.getVillaPoblacion());
		this.validarDatosString(pcr.getLocacion());
		this.validarResultadoExamen(pcr.getResultado());
		this.validarRut(pcr.getRut());
		this.validarCorreo(pcr.getCorreo(), pcr.getConfirmacionCorreo());
		this.validarHora(pcr.getHora());
		this.validarTelefono(pcr.getTelefono());
		this.validarTelefono(pcr.getTelefonoSecundario());
		this.validarDatosBooleanos(pcr.getAltoRiesgo());	
		
	}
}