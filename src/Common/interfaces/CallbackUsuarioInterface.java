package Common.interfaces;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.Trino;
import Common.clases.UsuarioData;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CallbackUsuarioInterface extends Remote {

    // login y logout
    List<Trino> ingresar(String nick) throws RemoteException;
    void salir(String nick)throws RemoteException;

    // Metodos user
    boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException;
    boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException;
    List<UsuarioData> getUsers() throws RemoteException;
    boolean isBlocked(String nick) throws RemoteException;
    UsuarioData getUser(String nick) throws RemoteException;

    // Metodos Seguidores
    boolean seguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException;
    boolean dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException;

    // Metodos trinos
    void crearTrino (String trino, String nickUsuario) throws RemoteException;
    List<Trino> getTrinos(String nicksUsuario) throws RemoteException;
    boolean borrarTrinoTotal(Trino trino) throws RemoteException;

    // Trinos de los usuarios que sigue el cliente
    Trino recibirTrinos ()throws RemoteException;
}
