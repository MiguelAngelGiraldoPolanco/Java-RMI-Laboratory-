package Servidor;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import Common.interfaces.*;

public class Servidor {
    public static void main(String[] args) {
        try{
            ServicioAutenticacionImpl servicioAutenticacion = new  ServicioAutenticacionImpl();
            ServicioGestorImpl servicioGestor = new ServicioGestorImpl();

            LocateRegistry.createRegistry(2002);

            ServicioAutenticacionInterface stubAutenticacion = (ServicioAutenticacionInterface)
                    UnicastRemoteObject.exportObject(servicioAutenticacion, 0);

            ServicioGestorInterface stubGestor = (ServicioGestorInterface)
                    UnicastRemoteObject.exportObject(servicioGestor, 0);


            String URL_autenticacion = "rmi://localhost:2002/ServicioAutenticacion/SER1";
            String URL_gestor = "rmi://localhost:2002/ServicioGestor/SER2";

            Naming.rebind(URL_autenticacion, stubAutenticacion);
            Naming.rebind(URL_gestor,stubGestor);

            System.out.println("URL del Servidor Autenticacion  : "+URL_autenticacion);
            System.out.println("URL del Servidor Gestor  : "+URL_gestor);

            System.out.println("Servidor en marcha... Pulsa ENTER para detener.");

            System.in.read();

            UnicastRemoteObject.unexportObject(servicioAutenticacion, true);
            UnicastRemoteObject.unexportObject(servicioGestor, true);
            System.out.println("Servidor detenida.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
