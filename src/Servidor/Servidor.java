package Servidor;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import Cliente.Buffer;
import Common.clases.UsuarioData;
import Common.interfaces.*;

public class Servidor {
    public static void main(String[] args) {
        try{
            ServicioGestorImpl servicioGestor = new ServicioGestorImpl();
            ServicioAutenticacionImpl servicioAutenticacion = new  ServicioAutenticacionImpl(servicioGestor);

            try {
                LocateRegistry.createRegistry(2002);
            } catch (RemoteException e) {
                LocateRegistry.getRegistry(2002);
            }

            ServicioAutenticacionInterface stubAutenticacion = (ServicioAutenticacionInterface)
                    UnicastRemoteObject.exportObject(servicioAutenticacion, 0);

            ServicioGestorInterface stubGestor = (ServicioGestorInterface)
                    UnicastRemoteObject.exportObject(servicioGestor, 0);


            String URL_autenticacion = "rmi://localhost:2002/ServicioAutenticacion/SER1";
            String URL_gestor = "rmi://localhost:2002/ServicioGestor/SER2";

            Naming.rebind(URL_autenticacion, stubAutenticacion);
            Naming.rebind(URL_gestor,stubGestor);

            int option;
            do {
                option = Buffer.readInt(
                        "\n--- MENÚ SERVIDOR ---\n" +
                                "1. Información del servidor.\n" +
                                "2. Listar Usuarios Registrados.\n" +
                                "3. Listar Usuarios Logueados\n" +
                                "4. Bloquear Usuario\n" +
                                "5. Desbloquear Usuario\n" +
                                "6. Salir\n" +
                                "Seleccione una opción: \n"
                );

                switch(option){
                    case 1: System.out.println("URL del servidor : "+URL_gestor +"\nURL de autenticacion: "+ URL_autenticacion );
                        break;
                    case 2: mostrarUsuarios(servicioGestor);
                        break;
                    case 3: mostrarUsuariosLogueados(servicioGestor);
                        break;
                    case 4: bloquearusuario(true, servicioGestor);
                        break;
                    case 5: bloquearusuario(false, servicioGestor);
                        break;
                    case 6: System.out.println("Servidor detenido.");
                        UnicastRemoteObject.unexportObject(servicioAutenticacion, true);
                        UnicastRemoteObject.unexportObject(servicioGestor, true);
                        System.exit(0);
                        break;
                    default: System.out.println("Opción inválida");
                }
            } while(option != 6);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void mostrarUsuarios(ServicioGestorImpl servicio) throws RemoteException {
        List<UsuarioData> lista = servicio.getUsers();
        if(lista.isEmpty()) {
            System.out.println("\n No hay usuarios registrados.");
            return;
        }
        System.out.println("=== USUARIOS REGISTRADOS ===");
        for (UsuarioData us : lista) {
            System.out.println("Nick: " + us.getNick() + " | Nombre: " + us.getNombre());
        }
    }
    public static void mostrarUsuariosLogueados(ServicioGestorImpl servicio) throws RemoteException {
        List<UsuarioData> lista = servicio.getUsers();
        if(lista.isEmpty()) {
            System.out.println("\n No hay Usuarios Logueados.");
            return;
        }
        System.out.println("=== USUARIOS LOGUEADOS ===");
        for (UsuarioData us : lista) {
            if (us.isOnline() == true){
                System.out.println("Nick: " + us.getNick() + " | Nombre: " + us.getNombre());
            }
        }
    }
    public static void bloquearusuario(boolean bloquear, ServicioGestorImpl servicio) throws RemoteException {
        String nick = Buffer.readLine("Ingresa el nick de Usuario\n");

        boolean exito = servicio.bloquearDesbloquearCuenta(nick,bloquear);
        System.out.println(exito ? "Operacion exitoso" : "Error al Bloquear/desbloquear");
    }

}
