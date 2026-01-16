package Servidor;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import java.rmi.Naming;
import java.rmi.RemoteException;
import Common.clases.Trino;
import Common.clases.UsuarioData;
import Common.interfaces.*;
import java.util.List;

public class ServicioAutenticacionImpl implements ServicioAutenticacionInterface {

    private ServicioGestorImpl servicioGestor;

    public ServicioAutenticacionImpl(ServicioGestorImpl gestor) throws RemoteException {
        super(); // Exporta el objeto para RMI
        this.servicioGestor = gestor;
    }

    @Override
    public boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException {
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");

            if (baseDatos.getUsuarioData(nick) != null) return false;

            baseDatos.registrarUsuario(nombre, nick, clave);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Trino> ingresar(String nick, CallbackUsuarioInterface callbackDelCliente) throws RemoteException {
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");

            UsuarioData user = baseDatos.getUsuarioData(nick);
            if (user == null) return null;

            baseDatos.onLine(nick, true);

            this.servicioGestor.addCallback(nick, callbackDelCliente);

            List<Trino> pendientes = baseDatos.getAndClearTrinosPendientes(nick);
            return pendientes;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void salir(String nick) throws RemoteException {
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/BaseDeDatos/BD1");

            baseDatos.onLine(nick, false);

            this.servicioGestor.eliminarCallback(nick);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}