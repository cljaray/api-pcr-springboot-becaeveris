package cl.grupo4.baseDeDatosPCR.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.AccessControlException;
import java.util.List;
import java.util.Map;

import cl.grupo4.baseDeDatosCliente.service.IServicioCliente;
import cl.grupo4.baseDeDatosPCR.entity.Cliente;
import cl.grupo4.baseDeDatosPCR.entity.PCR;
import cl.grupo4.baseDeDatosPCR.service.IServicePCR;


/**Clase que expone las rutas de acceso a la aplicacion.
 * @author Grupo 4
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pcr")
public class ControladorPCR {
	
	@Autowired
	private IServicePCR servicioPCR;

	
	 /**Metodo que expone ruta /crearPCR y que permite la creacion de un nuevo pcr.
	 * @param nuevoPCR
	 * @return ResponseEntity (http response)
	 */
	@PostMapping(value = "/crearPCR", produces ="application/json")
	 public ResponseEntity<PCR> crearPCR(@RequestBody PCR nuevoPCR){
		PCR resultado = null;
		try {
			resultado = servicioPCR.crearPCR(nuevoPCR);
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}

		return new ResponseEntity<PCR>(resultado, HttpStatus.OK);
	 }
	
	 
	 /**Metodo que expone ruta /buscarTodosPCR y que permite recibir todos los PCRs en la base de datos.
	 * @return
	 */
	@GetMapping(value = "/buscarTodosPCR", produces ="application/json")
	 public ResponseEntity<List<PCR>> buscarTodosPCR(){
		List<PCR> resultado = null;
		try {
			resultado = servicioPCR.buscarTodosPCR();
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		return new ResponseEntity<List<PCR>>(resultado, HttpStatus.OK);
	 }
	 
	 /**Metodo que expone ruta /buscarPorRut y que permite la entrega de datos PCR por rut.
	 * @param rut
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value = "/buscarPorRut/{rut}", produces ="application/json")
	 public ResponseEntity<PCR> buscarPorRut(@PathVariable String rut){
		PCR resultado = null;
		try {
			resultado = servicioPCR.buscarPorRut(rut);
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		return new ResponseEntity<PCR>(resultado, HttpStatus.OK);	 
		}
	 	
	 /**Metodo que expone ruta /buscarNombreApellido y que permite la entrega de PCR con nombres y apellido que incluyan los parametros consultados.
	 * @param nombre
	 * @param apellido
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value = "/buscarNombreApellido", produces ="application/json")
	 public ResponseEntity<List<PCR>> buscarNombreApellido(@RequestParam String nombre, @RequestParam String apellido){
		List<PCR> resultado = null;
		try {
			resultado = servicioPCR.buscarNombreApellido(nombre, apellido);
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		return new ResponseEntity<List<PCR>>(resultado, HttpStatus.OK);	 
		}
	 
	 /**Metodo que expone ruta /actualizarPCR/{rut} y que permite la actualizar los datos PCR existente consultando por Rut.
	 * @param pcrActualizado
	 * @param rut
	 * @return ResponseEntity (http response)
	 */
	@PutMapping(value = "/actualizarPCR/{rut}", produces ="application/json")
	 public ResponseEntity<PCR> actualizarPCR(@RequestBody PCR pcrActualizado, @PathVariable String rut){
		PCR resultado = null;
		try {
			resultado = servicioPCR.actualizarPCR(pcrActualizado, rut);
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		return new ResponseEntity<PCR>(resultado, HttpStatus.OK);
		} 
	 
	 /**Metodo que expone ruta /checkearResultado y que permite la entrega de PCR mediante la busqueda de palabras clave (positivo, negativo o pendiente).
	 * @param resultado
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value = "/checkearResultado", produces = "application/json")
	 public ResponseEntity<List<PCR>> checkearResultado(@RequestParam String resultadoExamen){
		List<PCR> resultado = null;
		try {
			resultado = servicioPCR.checkearResultados(resultadoExamen);
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		return new ResponseEntity<List<PCR>>(resultado, HttpStatus.OK);
		}
	 
	 
	 /**Metodo que expone ruta /eliminarPCR y que permite la eliminacion de PCR mediante la entrega de Rut.
	 * @param rut
	 * @return ResponseEntity (http response)
	 */
	@DeleteMapping(value = "/eliminarPCR/{rut}", produces = "application/json")
	 public ResponseEntity<Map<String,String>> eliminarPCR(@PathVariable String rut){
		Map<String, String> resultado = null;
		try {
			resultado = servicioPCR.eliminarPCR(rut);
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		 
		return new ResponseEntity<Map<String,String>>(resultado, HttpStatus.OK);
		}
	 
	 /**Metodo que expone ruta /buscarPorComuna y que permite la entrega de datos PCR mediante la busqueda por comuna.
	 * @param comuna
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value ="/buscarPorComuna", produces = "application/json")
	 public ResponseEntity<List<PCR>> buscarPorComuna(@RequestParam String comuna){
		List<PCR> resultado = null;
		try {
			resultado = servicioPCR.buscarPorComuna(comuna);
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		 
		return new ResponseEntity<List<PCR>>(resultado, HttpStatus.OK);
		}
	 
	 /**Metodo que expone ruta /pacientesAltoRiesgo y que permite la entrega de datos PCR con paciente de alto riesgo.
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value = "/pacientesAltoRiesgo", produces = "application/json")
	 public ResponseEntity<List<PCR>> pacientesAltoRiesgo(){
		List<PCR> resultado = null;
		try {
			resultado = servicioPCR.pacientesAltoRiesgo();
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		return new ResponseEntity<List<PCR>>(resultado, HttpStatus.OK);
	}
	
	@GetMapping(value = "/comprobarRutExistente", produces = "application/json")
	 public ResponseEntity<Boolean> verificarRutExistente(@RequestParam String rut){
		Boolean resultado = null;
		try {
			resultado = servicioPCR.validarRutExistenteDirectoFrontEnd(rut);
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		return new ResponseEntity<Boolean>(resultado, HttpStatus.OK);
	}
	
	
	
	
	
	
	
}