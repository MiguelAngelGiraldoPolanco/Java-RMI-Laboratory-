package Common.interfaces;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.UsuarioData;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServicioGestorInterface extends Remote {
    //Metodos de usuarios

    boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException;
    boolean verificarCredenciales(String nick) throws RemoteException;
    UsuarioData getUsuarioData(String nick) throws RemoteException;
    boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException;

    //Métodos de seguimiento


}
