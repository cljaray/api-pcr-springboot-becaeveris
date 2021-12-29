package cl.grupo4.baseDeDatosCliente.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.grupo4.baseDeDatosPCR.entity.Cliente;

public interface IServicioCliente {

	List<Cliente> encuentraClientePorCfn(String cfn);

}