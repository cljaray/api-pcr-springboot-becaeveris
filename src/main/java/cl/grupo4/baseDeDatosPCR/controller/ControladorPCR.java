package cl.grupo4.baseDeDatosPCR.controller;



import org.springframework.beans.factory.annotation.Autowired;

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

import cl.grupo4.baseDeDatosPCR.entity.PCR;
import cl.grupo4.baseDeDatosPCR.service.IServicioPCR;

/**Clase que expone las rutas de acceso a la aplicacion.
 * @author Claudio
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pcr")
public class ControladorPCR {
	@Autowired
	private IServicioPCR servicioPCR;
	
	
	 /**Metodo que expone ruta /crearPCR y que permite la creacion de un nuevo pcr.
	 * @param nuevoPCR
	 * @return ResponseEntity (http response)
	 */
	@PostMapping(value = "/crearPCR", produces ="application/json")
	 public ResponseEntity<PCR> crearPCR(@RequestBody PCR nuevoPCR){
		
		
		//TODO validar parametros de entrada, a nivel de negocio y nulos
		//Validacion de errores de servicio
		
	//	try {
		//	
	//	} catch(AccessControlException e) {
		//	throw new RuntimeException();
	//	} catch(Exception e) {
		//	throw new RuntimeException();
	//	}
		
		 return new ResponseEntity<PCR>(servicioPCR.crearPCR(nuevoPCR), HttpStatus.OK);
	 }
	
	 
	 /**Metodo que expone ruta /buscarTodosPCR y que permite recibir todos los PCRs en la base de datos.
	 * @return
	 */
	@GetMapping(value = "/buscarTodosPCR", produces ="application/json")
	 public ResponseEntity<List<PCR>> buscarTodosPCR(){
		 return new ResponseEntity<List<PCR>>(servicioPCR.buscarTodosPCR(), HttpStatus.OK);
	 }
	 
	 /**Metodo que expone ruta /buscarPorRut y que permite la entrega de datos PCR por rut.
	 * @param rut
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value = "/buscarPorRut", produces ="application/json")
	 public ResponseEntity<PCR> buscarPorRut(@RequestParam String rut){
		 return new ResponseEntity<PCR>(servicioPCR.buscarPorRut(rut), HttpStatus.OK);
	 }
	 	
	 /**Metodo que expone ruta /buscarNombreApellido y que permite la entrega de PCR con nombres y apellido que incluyan los parametros consultados.
	 * @param nombre
	 * @param apellido
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value = "/buscarNombreApellido", produces ="application/json")
	 public ResponseEntity<List<PCR>> buscarNombreApellido(@RequestParam String nombre, @RequestParam String apellido){
		 return new ResponseEntity<List<PCR>>(servicioPCR.buscarNombreApellido(nombre, apellido), HttpStatus.OK);
	 }
	 
	 /**Metodo que expone ruta /actualizarPCR/{rut} y que permite la actualizar los datos PCR existente consultando por Rut.
	 * @param pcrActualizado
	 * @param rut
	 * @return ResponseEntity (http response)
	 */
	@PutMapping(value = "/actualizarPCR/{rut}", produces ="application/json")
	 public ResponseEntity<PCR> actualizarPCR(@RequestBody PCR pcrActualizado, @PathVariable String rut){
		 return new ResponseEntity<PCR>(servicioPCR.actualizarPCR(pcrActualizado, rut), HttpStatus.OK);
	 } 
	 
	 /**Metodo que expone ruta /checkearResultado y que permite la entrega de PCR mediante la busqueda de palabras clave (positivo, negativo o pendiente).
	 * @param resultado
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value = "/checkearResultado", produces = "application/json")
	 public ResponseEntity<List<PCR>> checkearResultado(@RequestParam String resultado){
		 return new ResponseEntity<List<PCR>>(servicioPCR.checkearResultados(resultado), HttpStatus.OK);
	 }
	 
	 
	 /**Metodo que expone ruta /eliminarPCR y que permite la eliminacion de PCR mediante la entrega de Rut.
	 * @param rut
	 * @return ResponseEntity (http response)
	 */
	@DeleteMapping(value = "/eliminarPCR", produces = "application/json")
	 public ResponseEntity<Map<String,String>> eliminarPCR(@RequestBody String rut){
		 
		 return new ResponseEntity<Map<String,String>>(servicioPCR.eliminarPCR(rut), HttpStatus.OK);
	 }
	 
	 /**Metodo que expone ruta /buscarPorComuna y que permite la entrega de datos PCR mediante la busqueda por comuna.
	 * @param comuna
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value ="/buscarPorComuna", produces = "application/json")
	 public ResponseEntity<List<PCR>> buscarPorComuna(@RequestParam String comuna){
		 
		 return new ResponseEntity<List<PCR>>(servicioPCR.buscarPorComuna(comuna), HttpStatus.OK);
	 }
	 
	 /**Metodo que expone ruta /pacientesAltoRiesgo y que permite la entrega de datos PCR con paciente de alto riesgo.
	 * @return ResponseEntity (http response)
	 */
	@GetMapping(value = "/pacientesAltoRiesgo", produces = "application/json")
	 public ResponseEntity<List<PCR>> pacientesAltoRiesgo(){
		 return new ResponseEntity<List<PCR>>(servicioPCR.pacientesAltoRiesgo(), HttpStatus.OK);
	 }
	 
	 
	 
}
