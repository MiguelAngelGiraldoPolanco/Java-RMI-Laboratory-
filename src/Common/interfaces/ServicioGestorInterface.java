package Common.interfaces;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.UsuarioData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface ServicioGestorInterface extends Remote {
    void addCallback(String nick, CallbackUsuarioInterface callback) throws RemoteException;
    void eliminarCallback(String nick) throws RemoteException;
    void crearTrino(String mensaje, String nickUsuario) throws RemoteException;
    boolean seguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException;
    boolean dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException;
    List<UsuarioData> getUsers() throws RemoteException;
    boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException;
    UsuarioData getUsuarioData(String nick) throws RemoteException;
    boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException;
    boolean isBlocked(String nick)throws  RemoteException;
    boolean onLine (String nick, boolean line) throws RemoteException;
}
