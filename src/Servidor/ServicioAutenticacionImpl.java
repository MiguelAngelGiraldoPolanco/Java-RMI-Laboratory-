package Servidor;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import java.rmi.Naming;
import java.rmi.RemoteException;

import Common.clases.Trino;
import Common.interfaces.*;
import java.util.List;

public class ServicioAutenticacionImpl implements ServicioAutenticacionInterface {

    public List<Trino> ingresar(String nick) throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            if ( baseDatos.verificarCredenciales(nick) ){
                baseDatos.onLine(nick, true);
                return baseDatos.getAndClearTrinosPendientes(nick);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void salir(String nick)throws RemoteException{
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            baseDatos.onLine(nick,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}