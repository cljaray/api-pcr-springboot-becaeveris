package cl.grupo4.baseDeDatosCliente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import cl.grupo4.baseDeDatosPCR.entity.Cliente;
import cl.grupo4.baseDeDatosPCR.repository.RepositorioCliente;

@Service("servicioCliente")
public class ServicioCliente implements IServicioCliente {

	@Autowired
	private RepositorioCliente repositorioCliente;
	
	
	@Override
	public List<Cliente> encuentraClientePorCfn(String cfn){
		
		return repositorioCliente.findByReclamoCfn(cfn);
	}
	
}
