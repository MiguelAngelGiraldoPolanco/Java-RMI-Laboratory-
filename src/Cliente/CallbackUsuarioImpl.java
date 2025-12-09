package Cliente;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.Trino;
import Common.interfaces.CallbackUsuarioInterface;
import Common.interfaces.ServicioAutenticacionInterface;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class CallbackUsuarioImpl implements CallbackUsuarioInterface {
    // login y logout
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
    public boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException{
        return false;
    }
    public boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException{
        return false;
    }

    // Metodos Seguidores
    public boolean seguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException{
        return false;
    }
    public boolean dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException{
        return false;
    }

    // Metodos trinos
    public void crearTrino (String trino, String nickUsuario) throws RemoteException{

    }
    public List<Trino> getTrinos(String nicksUsuario) throws RemoteException{
        return null;
    }
    public boolean borrarTrinoTotal(Trino trino) throws RemoteException{
        return false;
    }

    // Trinos de los usuarios que sigue el cliente
    public Trino recibirTrinos ()throws RemoteException{
        return null;
    }
}
