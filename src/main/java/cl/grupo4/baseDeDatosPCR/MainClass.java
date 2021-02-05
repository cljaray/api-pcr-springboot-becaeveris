package cl.grupo4.baseDeDatosPCR;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



/**Clase principal que inicializa la aplicacion.
 * @author Grupo 4
 *
 */
@SpringBootApplication
public class MainClass extends SpringBootServletInitializer {	

    public static void main(String[] args) {

      SpringApplication.run(MainClass.class, args);

    }
		
}
