package BaseDeDatos;

// Miguel Angel Giraldo Polanco mgiraldopolanco@gmail.com


import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import Common.clases.*;
import Common.interfaces.ServicioDatosInterface;

public class ServicioDatosImpl extends RemoteObject implements ServicioDatosInterface {

    private final Map <String, List<Trino>> historialTrinos = new ConcurrentHashMap<>();
    private final Map <String, UsuarioData> usuariosRegistrados = new ConcurrentHashMap<>();
    private final Map <String, Set<String>> relacionesSeguimiento = new ConcurrentHashMap<>();
    private final Map <String, List<Trino>> trinosPendientes = new ConcurrentHashMap<>();

    public ServicioDatosImpl (){
        super();
    }
    // -------------- Metodos Usuarios -----------------
    @Override
    public boolean registrarUsuario(String nombre, String nick, String clave) throws RemoteException {
        if (usuariosRegistrados.containsKey(nick)){
            return false;
        }
        UsuarioData user = new UsuarioData(nombre,nick,clave);
        usuariosRegistrados.put(nick,user);
        relacionesSeguimiento.put(nick, ConcurrentHashMap.newKeySet());
        trinosPendientes.put(nick, Collections.synchronizedList(new ArrayList<>()));
        historialTrinos.put(nick, Collections.synchronizedList(new ArrayList<>()));
        return true;
    }

    @Override
    public boolean verificarCredenciales(String nick ) throws RemoteException {
        return usuariosRegistrados.get(nick) != null;
    }

    @Override
    public UsuarioData getUsuarioData(String nick) throws RemoteException {
        return usuariosRegistrados.get(nick);
    }

    @Override
    public boolean bloquearDesbloquearCuenta(String nick, boolean bloquear) throws RemoteException {
        UsuarioData user = usuariosRegistrados.get(nick);
        if (user != null) {
            user.setBlocked(bloquear);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLine (String nick, boolean Online) throws RemoteException{
        UsuarioData user = usuariosRegistrados.get(nick);
        if (user != null) {
            if (Online == user.isOnline()){
                return false;
            }else{
                user.setOnline(Online);
                return true;
            }
        }
        return false;
    }

    // ----------------------- Metodos Seguidores --------------------------------------
    @Override
    public List<String> getSeguidores(String nickSeguido) throws RemoteException {
        Set<String> seguidores = relacionesSeguimiento.get(nickSeguido);
        List<String> listaSeguidores = new ArrayList<>(seguidores);
        return listaSeguidores;
    }

    @Override
    public boolean seguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException {
        Set<String> seguido = relacionesSeguimiento.get(nickSeguido);
        if (seguido != null) {
            return seguido.add(nickSeguidor);
        }
        return false;
    }

    @Override
    public boolean dejarDeSeguirUsuario(String nickSeguidor, String nickSeguido) throws RemoteException {
        Set<String> seguido = relacionesSeguimiento.get(nickSeguido);
        if (seguido != null){
            return seguido.remove(nickSeguidor);
        }
        return false;
    }

    //------------------------------ Metodos Trinos ----------------------------
    @Override
    public void crearTrino (String trino, String nickUsuario) throws RemoteException {
        Trino nuevoTrino = new Trino(trino, nickUsuario);
        List<Trino> trinosDelUsuario = historialTrinos.get(nickUsuario);
        if (trinosDelUsuario != null){
            trinosDelUsuario.add(nuevoTrino);
        }
    }

    @Override
    public List<Trino> getTrinos(String nickUsuario) throws RemoteException {
        return historialTrinos.get(nickUsuario);
    }

    @Override
    public void addTrinoPendiente(String recipientNick, Trino trino) throws RemoteException {
        List<Trino> nuevoTrino = trinosPendientes.get(recipientNick);
        if (nuevoTrino != null){
            nuevoTrino.add(trino);
        }
    }

    @Override
    public List<Trino> getAndClearTrinosPendientes(String recipientNick) throws RemoteException {
        List<Trino> trinosP = trinosPendientes.get(recipientNick);
        if (trinosP != null){
            trinosPendientes.put(recipientNick,new ArrayList<>());
            return trinosP;
        }
        return Collections.emptyList();
    }

    // Metodo para borrar un trino una vez se publica y revisa si este trino esta en lista de pendientes y lo borra tambien de ahi.
    @Override
    public boolean borrarTrinoTotal(Trino trino) throws RemoteException{
        String nickUsuario = trino.GetNickPropietario();
        List<Trino> historial = historialTrinos.get(nickUsuario);
        Collection<List<Trino>> pendientes = trinosPendientes.values();
        if (historial != null){
            historial.removeIf(p->
                    p.GetNickPropietario().equals(trino.GetNickPropietario()) &&
                            p.GetTrino().equals(trino.GetTrino()) &&
                            p.GetTimestamp() == trino.GetTimestamp()
            );

            pendientes.forEach( listaPendientes -> listaPendientes.removeIf(p->
                            p.GetNickPropietario().equals(trino.GetNickPropietario()) &&
                                    p.GetTrino().equals(trino.GetTrino()) &&
                                    p.GetTimestamp() == trino.GetTimestamp()
                    )

            );
            return true;
        }
        return false;
    }
}

