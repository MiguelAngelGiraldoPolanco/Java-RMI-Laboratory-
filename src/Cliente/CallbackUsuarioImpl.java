package Cliente;
//Miguel Angel Giraldo Polanco :: mgiraldopolanco@gmail.com

import Common.clases.Trino;
import Common.interfaces.CallbackUsuarioInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallbackUsuarioImpl extends UnicastRemoteObject implements CallbackUsuarioInterface {

   public CallbackUsuarioImpl() throws RemoteException {
      super();
   }

   @Override
   public void recibirTrino(Trino trino) throws RemoteException {
      System.out.println("\n> " + trino.GetNickPropietario() + "# " + trino.GetTrino());
   }
}

