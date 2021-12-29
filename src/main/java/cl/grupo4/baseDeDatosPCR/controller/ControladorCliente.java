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


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cfn")
public class ControladorCliente {

	@Autowired
	private IServicioCliente servicioCliente;
	
	@GetMapping(value = "/{cfn}", produces = "application/json")
	public ResponseEntity<List<Cliente>> buscaPorCfn(@PathVariable String cfn){
		List<Cliente> resultado = null;
		try {
			resultado = servicioCliente.encuentraClientePorCfn(cfn);
		}catch(AccessControlException e){
			throw new RuntimeException("Hubo un fallo en el servidor.");
		}catch(Error e){
			throw new RuntimeException("Hubo un fallo al ingresar su peticion.");
		}
		return new ResponseEntity<List<Cliente>>(resultado, HttpStatus.OK);
	}
	
}
