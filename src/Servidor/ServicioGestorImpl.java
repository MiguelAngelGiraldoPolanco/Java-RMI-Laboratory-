package Servidor;

//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import Common.interfaces.*;
import Common.clases.*;

public class ServicioGestorImpl implements ServicioGestorInterface {
   //Metodos de usuarios

    public boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            return baseDatos.registrarUsuario(nombre,nick,clave);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UsuarioData getUsuarioData(String nick) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            return baseDatos.getUsuarioData(nick);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            return baseDatos.bloquearDesbloquearCuenta(nick, bloquear);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Métodos de seguimiento

    public List<String> getSeguidores(String nickSeguido) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            return baseDatos.getSeguidores(nickSeguido);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean seguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            return baseDatos.seguirUsuario(nickSeguidor, nickSeguido);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            return baseDatos.dejarDeSeguirUsuario(nickSeguidor, nickSeguido);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Métodos de trinos

    public void crearTrino (String trino, String nickUsuario) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            baseDatos.crearTrino(trino, nickUsuario);
            Trino trinito = new Trino(trino,nickUsuario);
            // agregar los trinos nuevos como pendientes si tiene seguidores que estan offline
            List<String> seguidores = baseDatos.getSeguidores(nickUsuario);
            // verificar si los seguidores estan online o offline
            if (seguidores != null){
                List<UsuarioData> usuarios = new ArrayList<>();
                    seguidores.forEach( seguidor ->{
                        try {
                            usuarios.add(baseDatos.getUsuarioData(seguidor));
                        } catch (RemoteException e) {
                            System.out.println("no se pudo procesar y añadir usuario");
                        }
                    });
                    usuarios.forEach(user ->{
                        if (user.isOnline() == false){
                            try {
                                baseDatos.addTrinoPendiente(user.getNick(),trinito);
                            } catch (RemoteException e) {
                                System.out.println("no se pudo procesar y añadir trino");
                            }
                        }
                    });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Trino> getTrinos(String nicksUsuario) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            return baseDatos.getTrinos(nicksUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean borrarTrinoTotal(Trino trino) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            return baseDatos.borrarTrinoTotal(trino);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
