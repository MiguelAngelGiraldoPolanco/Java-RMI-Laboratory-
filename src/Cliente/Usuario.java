package Cliente;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Cliente.UsuarioUI;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Usuario {

    public static void main(String[] args) {
        try{
            CallbackUsuarioImpl user = new CallbackUsuarioImpl();
            LocateRegistry.createRegistry(2003);
            UsuarioUI UI = new UsuarioUI(user);
            UI.mostrarMenu();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
