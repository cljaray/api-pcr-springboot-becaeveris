package cl.grupo4.baseDeDatosPCR.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import cl.grupo4.baseDeDatosPCR.entity.PCR;
import cl.grupo4.baseDeDatosPCR.repository.RepositorioPCR;
import lombok.extern.log4j.Log4j2;

/**
 * Clase que expone servicios para ser usados por el controlador y acceder a la base de datos.
 * @author Grupo 4
 *
 */
@Service
public class ServicioPCR implements IServicePCR  {
	@Autowired
	private RepositorioPCR repositorioPCR;
	
	//Creacion de constante con el valor "pendiente", para metodos de validacion y busqueda
	protected static final String PENDIENTE = "pendiente"; 
	//Creacion de constante con el valor "positivo", para metodos de validacion y busqueda
	protected static final String POSITIVO = "positivo";
	//Creacion de constante con el valor "negativo", para metodos de validacion y busqueda
	protected static final String NEGATIVO = "negativo";
	
	/** Metodo para crear pcr que recibe entidad de tipo PCR y retorna nuevo PCR.
	 * @param nuevoPCR
	 * @return nuevoPCR
	 */
	@Override
	@Transactional
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
		//se valida rut
		this.validarRut(rut);
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
	@Transactional
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
	@Transactional
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
		if(resultado.toLowerCase().equals(ServicioPCR.POSITIVO)) {
			busqueda = ServicioPCR.POSITIVO;
		} else if(resultado.toLowerCase().equals(ServicioPCR.NEGATIVO)) {
			busqueda = ServicioPCR.NEGATIVO;
		} else if(resultado.toLowerCase().equals(ServicioPCR.PENDIENTE)) {
			busqueda = ServicioPCR.PENDIENTE;
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
		//Se busca pcr por comuna utilizando el valor ingreado
		return repositorioPCR.findByComunaDeResidencia(comuna);
	}
	
	/**Metodo que permite buscar a todos los de alto riesgo.
	 * @return Lista PCR
	 */
	@Override
	public List<PCR> pacientesAltoRiesgo(){
		//Se realiza busqueda de pcr utilizando valor verdadero
		return repositorioPCR.findByAltoRiesgo(true);
	}
	
	
	/**Metodo que valida si rut esta escrito de la manera indicada(11.111.111-1).
	 * @param rut	  
	 */
	@Override
	public void validarRut(String rut) {
		//Se verifica que valor de rut sea valido
		this.validarDatosString(rut);
		
		//Se valida rut mediante expresion regular, si no coincide, envia error
		if(!Pattern.matches("^(\\d{1}|\\d{2})\\.(\\d{3}\\.\\d{3}-)([kK]{1}$|\\d{1}$)",rut)) {
			throw new RuntimeException("Rut no válido");
		} 
	}
	
	
	/**Metodo que valida la existencia de rut, y en caso de que exista, ejecuta una excepcion.
	 * @param rut
	 */
	@Override
	public void validarRutExistente(String rut) {
		//Se verifica que valor de rut sea valido
		this.validarRut(rut);
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
		//Se verifica que valor de rut sea valido
		validarRut(rut);
		//se valida la existencia de rut, y manda error si no existe
		if (!repositorioPCR.existsByRut(rut)) {
			throw new RuntimeException("Este rut no existe");		
		}
	}
	
	/**
	 * Metodo que retorna valor booleano para verificar la existencia de un rut en la base de datos.
	 * @param rut
	 * @return Boolean
	 */
	@Override
	public Boolean validarRutExistenteDirectoFrontEnd(String rut) {
		//Se verifica que valor de rut sea valido
		this.validarRut(rut);
		//se valida la existencia de rut, y manda error si existe
		if (repositorioPCR.existsByRut(rut)) {
			return true;		
		} 
		
		return false;		
	}
	
	/**
	 * Metodo que recibe un objeto String para verificar si sus valores son válidos. 
	 * @param valorInput
	 */
	public void validarDatosString(String valorInput) {
		//Se verifica que valorinput no sea null o este vacio
		if(valorInput == null || valorInput.trim() == null || valorInput.trim().isBlank()) {
			throw new RuntimeException("No se pueden ingresar campos vacios, por favor ingrese la informacion requerida.");
		}		
	}
	
	/**
	 * Metodo que recibe un objeto Integer para verificar si sus valores son válidos. 
	 * @param valorInput
	 */
	public void validarDatosInteger(Integer valorInput) {
		//Se verifica que valorinput no sea null
		 if (valorInput == null) {
		        throw new RuntimeException("Valor no puede estar vacío");
		 }
	}
	
	/**
	 * Metodo que recibe un objeto Integer para verificar si sus valores son válidos en referencia a las restricciones de valor de edad. 
	 * @param edad
	 */
	public void validarEdad(Integer edad) {
		//Se verifica que edad no sea null, menor a 0 o mayor a 150
		if (edad == null || edad < 0 || edad >= 150) {	
			throw new RuntimeException("Porfavor ingresa una edad valida");	
		}
	}
	
	/**
	 *  Metodo que recibe un objeto Integer para verificar si sus valores son válidos en referencia a las restricciones de valor telefono.
	 * @param telefono
	 */
	public void validarTelefono(Integer telefono) {
		//Se verifica que telefono no sea null o que su cantidad de digitos sea 9
		if(telefono == null || String.valueOf(telefono).length() != 9){			
			throw new RuntimeException("Numero de telefono invalido");
		}
	}
	
	/**
	 *  Metodo que recibe un objetos String para verificar si sus valores son válidos e iguales entre si.
	 * @param correo
	 * @param confirmacionCorreo
	 */
	public void validarCorreo(String correo, String confirmacionCorreo) {
		// //Se verifica que correo y confirmacionCorreo no sea null o este vacio
		this.validarDatosString(correo);		
		this.validarDatosString(confirmacionCorreo);
		
		//Se verifica que los valores de correo y confirmacionCorreo sean iguales entre si		
		if(!correo.trim().equals(confirmacionCorreo.trim())) {
			throw new RuntimeException("Porfavor revisa que tu correo este correcto");
		}
		
	}
	
	/**
	 *  Metodo que recibe un objeto String para verificar si sus valores son válidos en referencia a las 
	 *  restricciones del formato del valor hora.
	 * @param hora
	 */
	public void validarHora(String hora) {		
		//Se verifica que hora no sea null o este vacio
		this.validarDatosString(hora);
		//Se verifica que valor hora corresponda al formato de la expresion regular
		if (!Pattern.matches("^(1?[0-9]|2[0-3]):[0-5][0-9]$", hora.trim())) {
			throw new RuntimeException("Porfavor ingresa una hora en formato HH:MM");
		}
		
	}
	
	/**
	 *  Metodo que recibe un objeto String para verificar si el valor resultado corresponde a alguno de los valores constantes.
	 * @param resultado
	 * @return
	 */
	public Boolean validarResultadoExamen(String resultado) {
		//Se verifica que resultado no sea null o este vacio
		this.validarDatosString(resultado);
		
		//Se verifica que valor de resultado sea alguno de los siguientes casos comparando con valores constantes		
		if(resultado.equals(ServicioPCR.NEGATIVO)) {
			
			return true;
			
		} else if (resultado.equals(ServicioPCR.POSITIVO)) {
			
			return true;
			
		} else if (resultado.equals(ServicioPCR.PENDIENTE)) {
			
			return true;
		}
		
		throw new RuntimeException("Porfavor selecciona un valor");
	}
	
	/**
	 *  Metodo que recibe un objeto Boolean para verificar si su valor es válido.
	 * @param valorInput
	 */
	public void validarDatosBooleanos(Boolean valorInput) {
		//Se verifica que valorInput si valor input es null
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
		
		//Se utilizan metodos de validacion para verificar que los datos de objeto pcr son válidos
		this.validarDatosString(pcr.getNombre());
		this.validarDatosString(pcr.getApellido());
		this.validarDatosString(pcr.getDireccion());
		this.validarDatosString(pcr.getComunaDeResidencia());
		this.validarDatosString(pcr.getVillaPoblacion());
		this.validarDatosString(pcr.getLugarTomaExamen());
		this.validarResultadoExamen(pcr.getResultado());		
		this.validarCorreo(pcr.getCorreo(), pcr.getConfirmacionCorreo());
		this.validarHora(pcr.getHora());
		this.validarTelefono(pcr.getTelefono());
		this.validarTelefono(pcr.getTelefonoSecundario());
		this.validarDatosBooleanos(pcr.getAltoRiesgo());	
		this.validarEdad(pcr.getEdad());
		
	}
}