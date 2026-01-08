package BaseDeDatos;

// Miguel Angel Giraldo Polanco mgiraldopolanco@gmail.com

import Cliente.Buffer;
import Common.clases.Trino;
import Common.interfaces.ServicioDatosInterface;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class BaseDeDatos {
    public static void main(String[] args) throws Exception {

        try{
            ServicioDatosImpl servicioDatos = new ServicioDatosImpl();

            LocateRegistry.createRegistry(2001);

            ServicioDatosInterface stub = (ServicioDatosInterface)
                    UnicastRemoteObject.exportObject(servicioDatos, 0);

            String URL_nombre = "rmi://localhost:2001/BaseDeDatos/BD1";
            Naming.rebind(URL_nombre, stub);

            int option;
            do {
                option = Buffer.readInt(
                        "\n--- MENÚ BASE DE DATOS ---\n" +
                                "1. Información de la Base de Datos.\n" +
                                "2. Listar Trinos (Solo nick y timestamp)\n" +
                                "3. Salir\n" +
                                "Seleccione una opción: "
                );

                switch(option){
                    case 1: System.out.println("URL de la base de datos : "+URL_nombre);
                        break;
                    case 2: mostrarTrino(servicioDatos);
                        break;
                    case 3: System.out.println("Base de datos detenida.");
                        UnicastRemoteObject.unexportObject(servicioDatos, true);
                        System.exit(0);
                        break;
                    default: System.out.println("Opción inválida");
                }
            } while(option != 3);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void mostrarTrino(ServicioDatosImpl servicio){
        List<Trino> lista = servicio.getTrinosResumen();
        if(lista.isEmpty()) {
            System.out.println("\n No hay trinos registrados.");
            return;
        }
        System.out.println("=== HISTORIAL DE TRINOS (RESUMEN) ===");
        for (Trino tr : lista) {
            System.out.println("Nick: " + tr.GetNickPropietario() + " | Fecha: " + tr.GetTimestamp());
        }
    }
}

