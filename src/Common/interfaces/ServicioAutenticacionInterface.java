package Common.interfaces;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterface extends Remote {

    boolean verificarExistencia (String nick) throws RemoteException;
    boolean ingresar(String nick, String clave) throws RemoteException;
    void salir()throws RemoteException;

}

