package Cliente;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.interfaces.CallbackUsuarioInterface;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Usuario {
    public static void main(String[] args) {
        try{
            CallbackUsuarioImpl cliente = new CallbackUsuarioImpl();

            LocateRegistry.createRegistry(2003);

            CallbackUsuarioInterface stubCliente = (CallbackUsuarioInterface)
                    UnicastRemoteObject.exportObject(cliente, 0);

            String URL_cliente = "rmi://localhost:2003/cliente/user1";

            Naming.rebind(URL_cliente, stubCliente);

            System.out.println("URL del Cliente corriendo en : "+URL_cliente);

            System.out.println("Cliente en marcha... Pulsa ENTER para detener.");

            System.in.read();

            UnicastRemoteObject.unexportObject(cliente, true);

            System.out.println("Cliente detenido.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
