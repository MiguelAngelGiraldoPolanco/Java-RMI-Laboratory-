package Cliente;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.interfaces.ServicioAutenticacionInterface;
import Common.interfaces.ServicioGestorInterface;
import java.rmi.Naming;

public class Usuario {

    public static void main(String[] args) {
        try {
            String urlAuth = "rmi://localhost:2002/ServicioAutenticacion/SER1";
            String urlGestor = "rmi://localhost:2002/ServicioGestor/SER2";

            System.out.println("Conectando con el servidor...");

            ServicioAutenticacionInterface auth = (ServicioAutenticacionInterface) Naming.lookup(urlAuth);
            ServicioGestorInterface gestor = (ServicioGestorInterface) Naming.lookup(urlGestor);

            UsuarioUI UI = new UsuarioUI(auth, gestor);

            System.out.println("Conexión establecida.");
            UI.mostrarMenu();

        } catch (Exception e) {
            System.err.println("Error al iniciar el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}