package BaseDeDatos;

// Miguel Angel Giraldo Polanco mgiraldopolanco@gmail.com

import Common.interfaces.ServicioDatosInterface;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class BaseDeDatos {
    public static void main(String[] args) throws Exception {

        try{
            ServicioDatosImpl servicioDatos = new ServicioDatosImpl();

            LocateRegistry.createRegistry(2001);

            ServicioDatosInterface stub = (ServicioDatosInterface)
                    UnicastRemoteObject.exportObject(servicioDatos, 2001);

            String URL_nombre = "rmi://localhost:2001/BaseDeDatos/BD1";
            Naming.rebind(URL_nombre, stub);

            System.out.println("URL de la base de datos : "+URL_nombre);
            System.out.println("Base de datos en marcha... Pulsa ENTER para detener.");

            System.in.read();

            UnicastRemoteObject.unexportObject(servicioDatos, true);
            System.out.println("Base de datos detenida.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

