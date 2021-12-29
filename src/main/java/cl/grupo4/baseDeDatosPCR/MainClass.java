package cl.grupo4.baseDeDatosPCR;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;



/**Clase principal que inicializa la aplicacion.
 * @author Grupo 4
 *
 */
@SpringBootApplication
@ComponentScan({"cl.grupo4.baseDeDatosPCR","cl.grupo4.baseDeDatosCliente"})
public class MainClass extends SpringBootServletInitializer {	

    public static void main(String[] args) {

      SpringApplication.run(MainClass.class, args);

    }
		
}
