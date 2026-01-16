package Common.interfaces;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.Trino;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServicioAutenticacionInterface extends Remote {

    boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException;

    List<Trino> ingresar(String nick, CallbackUsuarioInterface callbackDelCliente) throws RemoteException;

    void salir(String nick) throws RemoteException;

}

