package Servidor;

//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Common.interfaces.*;
import Common.clases.*;

public class ServicioGestorImpl implements ServicioGestorInterface {

    private Map<String, CallbackUsuarioInterface> mapaCallbacks = new HashMap<>();

    public ServicioGestorImpl() throws RemoteException {
        super();
    }

    @Override
    public void addCallback(String nick, CallbackUsuarioInterface callback) throws RemoteException {
        this.mapaCallbacks.put(nick, callback);
        System.out.println(">>> Callback registrado para el usuario: " + nick);
    }

    @Override
    public void eliminarCallback(String nick) throws RemoteException {
        this.mapaCallbacks.remove(nick);
        System.out.println(">>> Callback eliminado (Logout): " + nick);
    }
   //Metodos de usuarios
    @Override
    public boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");

            return baseDatos.registrarUsuario(nombre,nick,clave);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public UsuarioData getUsuarioData(String nick) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");

            return baseDatos.getUsuarioData(nick);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");

            return baseDatos.bloquearDesbloquearCuenta(nick, bloquear);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<UsuarioData> getUsers() throws RemoteException {
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");

            return baseDatos.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean isBlocked(String nick)throws  RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");
            UsuarioData user = baseDatos.getUsuarioData(nick);
            if (user == null) {
                return false;
            }
            return user.isBlocked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onLine (String nick, boolean line) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");
            return baseDatos.onLine(nick,line);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean seguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException {
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");
            UsuarioData user = getUsuarioData(nickSeguido);
            if (user != null) {
                return baseDatos.seguirUsuario(nickSeguidor, nickSeguido);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");

            return baseDatos.dejarDeSeguirUsuario(nickSeguidor, nickSeguido);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Métodos de trinos
    @Override
    public void crearTrino(String trino, String nickUsuario) throws RemoteException {
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");

            UsuarioData autor = baseDatos.getUsuarioData(nickUsuario);
            if (autor == null || autor.isBlocked()) {
                System.out.println("El usuario " + nickUsuario + " está bloqueado o no existe. Trino rechazado.");
                return;
            }

            // CORRECCIÓN: Usar la variable 'trino' que viene por parámetro
            Trino trinito = new Trino(trino, nickUsuario);
            baseDatos.crearTrino(trino, nickUsuario);

            List<String> seguidores = baseDatos.getSeguidores(nickUsuario);

            if (seguidores != null) {
                for (String nickSeguidor : seguidores) {
                    if (this.mapaCallbacks.containsKey(nickSeguidor)) {
                        try {
                            CallbackUsuarioInterface clienteDestino = mapaCallbacks.get(nickSeguidor);
                            // RECUERDA: El cliente debe tener implementado 'recibirTrino'
                            clienteDestino.recibirTrino(trinito);
                            System.out.println("Trino enviado en tiempo real a: " + nickSeguidor);
                        } catch (RemoteException e) {
                            System.out.println("Fallo de conexión con " + nickSeguidor + ". Guardando como pendiente.");
                            baseDatos.addTrinoPendiente(nickSeguidor, trinito);
                            this.mapaCallbacks.remove(nickSeguidor);
                        }
                    } else {
                        baseDatos.addTrinoPendiente(nickSeguidor, trinito);
                        System.out.println("Trino guardado como pendiente para: " + nickSeguidor);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor al crear trino: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
