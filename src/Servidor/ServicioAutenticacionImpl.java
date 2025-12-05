package Servidor;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import java.rmi.Naming;
import java.rmi.RemoteException;
import Common.interfaces.*;

public class ServicioAutenticacionImpl implements ServicioAutenticacionInterface {

    public boolean verificarExistencia (String nick) throws RemoteException {
        try {
            ServicioDatosInterface baseDatos = (ServicioDatosInterface)
                    Naming.lookup("rmi://localhost:2001/ServicioDatos/BD1");

            return baseDatos.getUsuarioData(nick) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean ingresar(String nick, String clave) throws RemoteException{
        return false;
    }
    public void salir()throws RemoteException{}
}