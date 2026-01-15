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
            try {
                LocateRegistry.createRegistry(2003);
            } catch (RemoteException e) {
                LocateRegistry.getRegistry(2003);
            }
            UsuarioUI UI = new UsuarioUI(user);
            UI.mostrarMenu(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
