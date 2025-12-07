package Common.interfaces;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.Trino;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServicioAutenticacionInterface extends Remote {

    List<Trino> ingresar(String nick) throws RemoteException;
    void salir(String nick)throws RemoteException;

}

