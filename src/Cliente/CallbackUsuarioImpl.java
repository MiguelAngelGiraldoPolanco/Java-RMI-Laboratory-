package Cliente;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.Trino;
import Common.clases.UsuarioData;
import Common.interfaces.CallbackUsuarioInterface;
import Common.interfaces.ServicioAutenticacionInterface;
import Common.interfaces.ServicioGestorInterface;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class CallbackUsuarioImpl implements CallbackUsuarioInterface {
    // login y logout
    @Override
    public List<Trino> ingresar(String nick) throws RemoteException{
        try {
            ServicioAutenticacionInterface autenticacion = (ServicioAutenticacionInterface)
                    Naming.lookup("rmi://localhost:2002/ServicioAutenticacion/SER1");
            return autenticacion.ingresar(nick);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void salir(String nick)throws RemoteException{
        try {
            ServicioAutenticacionInterface autenticacion = (ServicioAutenticacionInterface)
                    Naming.lookup("rmi://localhost:2002/ServicioAutenticacion/SER1");
            autenticacion.salir(nick);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodos user
    @Override
    public boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException{
        try {
            ServicioGestorInterface servidor = (ServicioGestorInterface)
                    Naming.lookup("rmi://localhost:2002/ServicioGestor/SER2");
            return servidor.registrarUsuario(nombre,nick,clave);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException{
        try {
            ServicioGestorInterface servidor = (ServicioGestorInterface)
                    Naming.lookup("rmi://localhost:2002/ServicioGestor/SER2");
            return servidor.bloquearDesbloquearCuenta(nick,bloquear);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodos Seguidores
    @Override
    public boolean seguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException{
        try {
            ServicioGestorInterface servidor = (ServicioGestorInterface)
                    Naming.lookup("rmi://localhost:2002/ServicioGestor/SER2");
            return servidor.seguirUsuario(nickSeguidor,nickSeguido);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException{
        try {
            ServicioGestorInterface servidor = (ServicioGestorInterface)
                    Naming.lookup("rmi://localhost:2002/ServicioGestor/SER2");
            return servidor.dejarDeSeguirUsuario(nickSeguidor,nickSeguido);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<UsuarioData> getUsers() throws RemoteException{
        try {
            ServicioGestorInterface servidor = (ServicioGestorInterface)
                    Naming.lookup("rmi://localhost:2002/ServicioGestor/SER2");
            return servidor.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Metodos trinos
    @Override
    public void crearTrino (String trino, String nickUsuario) throws RemoteException{
        try {
            ServicioGestorInterface servidor = (ServicioGestorInterface)
                    Naming.lookup("rmi://localhost:2002/ServicioGestor/SER2");
            servidor.crearTrino(trino, nickUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Trino> getTrinos(String nicksUsuario) throws RemoteException{
        return null;
    }
    @Override
    public boolean borrarTrinoTotal(Trino trino) throws RemoteException{
        return false;
    }

    // Trinos de los usuarios que sigue el cliente
    @Override
    public Trino recibirTrinos ()throws RemoteException{
        return null;
    }
}
