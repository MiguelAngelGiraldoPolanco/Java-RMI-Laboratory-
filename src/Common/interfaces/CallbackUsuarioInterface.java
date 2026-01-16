package Common.interfaces;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.Trino;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackUsuarioInterface extends Remote {
    void recibirTrino(Trino trino) throws RemoteException;
}
