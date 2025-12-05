package Servidor;

//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import java.rmi.RemoteException;
import Common.interfaces.*;
import Common.clases.*;

public class ServicioGestorImpl implements ServicioGestorInterface {
    // Metodos Usuario

    public boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException {
        return false;
    }

    public boolean verificarCredenciales(String nick) throws RemoteException{
        return false;
    }
    public UsuarioData getUsuarioData(String nick) throws RemoteException{
        UsuarioData UsuarioData = null;
        return UsuarioData;
    }
    public boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException{
        return false;
    }

    //Métodos de seguimiento


}
