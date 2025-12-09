package Common.interfaces;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.Trino;
import Common.clases.UsuarioData;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface ServicioGestorInterface extends Remote {
    //Metodos de usuarios

    boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException;
    UsuarioData getUsuarioData(String nick) throws RemoteException;
    boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException;
    List<UsuarioData> getUsers () throws RemoteException;
    //Métodos de seguimiento

    List<String> getSeguidores(String nickSeguido) throws RemoteException;
    boolean seguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException;
    boolean dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException;

    //Métodos de trinos

    void crearTrino (String trino, String nickUsuario) throws RemoteException;
    List<Trino> getTrinos(String nicksUsuario) throws RemoteException;
    boolean borrarTrinoTotal(Trino trino) throws RemoteException;

}
