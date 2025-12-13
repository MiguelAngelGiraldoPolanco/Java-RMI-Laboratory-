package Cliente;

import Common.clases.UsuarioData;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsuarioUI{
    private final CallbackUsuarioImpl user;
    private Set<String> usersOnline = new HashSet<>();
    private String usuarioActual ;

    public UsuarioUI(CallbackUsuarioImpl user) {
        this.user = user;
    }

    public void mostrarMenu() throws RemoteException {
        int option;
        do {
            option = Buffer.readInt(
                    "1. Iniciar sesión\n" +
                            "2. Registrarse\n"
            );

            switch(option){
                case 1: iniciarSesion();
                    break;
                case 2: registrarse();
                    break;
                default: System.out.println("Opción inválida");
            }
        } while(option != 0);
    }

    private void registrarse() throws RemoteException {
        String nombre = Buffer.readLine("Ingresa tu nombre\n");
        String nick = Buffer.readLine("Ingresa tu nick\n");
        String clave = Buffer.readLine("Ingresa tu clave\n");

        boolean exito = user.registrarUsuario(nombre, nick, clave);
        System.out.println(exito ? "Registro exitoso" : "Error al registrar");
    }

    private void iniciarSesion() throws RemoteException {
        String nick = Buffer.readLine("Ingresa tu nick\n");
        if (!usersOnline.add(nick)){
            System.out.println("Ya ha iniciado sesion");
            return;
        }
        usersOnline.add(nick);
        var trinos = user.ingresar(nick);
        usuarioActual = nick;
        int option = 0;
        if(trinos != null){
            System.out.println("Bienvenido " + nick);
            for(var trino : trinos){
                System.out.println("Trino reciente: "+trino.GetNickPropietario() + " : "+ trino.GetTrino());
            }
            do {
                option = Buffer.readInt(
                        "1. Crear Trino\n" +
                                "2. Seguir Usuario\n" +
                                "3. Dejar De Seguir Usuario\n" +
                                "4. Trinos De Los Usuarios Que Sigo\n" +
                                "5. Configuracion de Cuenta\n" +
                                "6. Cerrar Secion\n"
                );

                switch(option){
                    case 1: crearTrino();
                        break;
                    case 2: seguirUsuario();
                        break;
                    case 3: dejarSeguirUsuario();
                        break;
                    case 4: trinosUsuariosSeguidos();
                        break;
                    case 5: configuracion();
                        break;
                    case 6: salir();
                        break;
                    default: System.out.println("Opción inválida");
                }
            } while(option != 0);

        } else {
            System.out.println("Error al iniciar sesión usuario incorrecto");
        }

    }

    private void crearTrino() throws RemoteException {
        String trino = Buffer.readLine("Escribe un trino\n");
        user.crearTrino(trino, usuarioActual);
    }

    private void seguirUsuario() throws RemoteException {
        String usuario = Buffer.readLine("Escribe el nick del usuario que quieres seguir\n");
        if (usuarioActual.equals(usuario)){
            System.out.println("No puedes seguirte a ti mismo");
        } else if (user.isBlocked(usuario)) {
            System.out.println("El usuario esta bloqueado no puedes seguirlo");
        } else if( user.seguirUsuario(usuarioActual, usuario)) {
            System.out.println("Haz empezado a seguir a: "+ usuario);
        }else {
            System.out.println("El usuario que quieres seguir no existe");
        }
    }

    private void dejarSeguirUsuario() throws RemoteException {
        String usuario = Buffer.readLine("Escribe el nick del usuario que quieres dejar seguir\n");
        if (usuarioActual.equals(usuario)){
            System.out.println("No puedes dejar de seguirte a ti mismo");
            return;
        }
        List<UsuarioData>usuarios = user.getUsers();
        boolean existe = usuarios.stream()
                .anyMatch(u -> u.getNick().equals(usuario));
        if(existe) {
            user.dejarDeSeguirUsuario(usuarioActual, usuario);
            System.out.println("Haz dejado de seguir a: "+ usuario);
        }else {
            System.out.println("El usuario que quieres dejar de seguir no existe");
        }
    }

    private void trinosUsuariosSeguidos() throws RemoteException {
        if (usersOnline.contains(usuarioActual)) {
            user.salir(usuarioActual);
            usersOnline.remove(usuarioActual);
            usuarioActual = null;
            System.out.println("Adios");
            mostrarMenu();
        }
    }

    private void configuracion() throws RemoteException {
        int option;
        do {
            option = Buffer.readInt(
                    "1. Bloquear Cuenta\n" +
                            "2. Debloquear Cuenta\n"
            );

            switch(option){
                case 1: user.bloquearDesbloquearCuenta(usuarioActual,true);
                    break;
                case 2: user.bloquearDesbloquearCuenta(usuarioActual,false);
                    break;
                default: System.out.println("Opción inválida");
            }
        } while(option != 0);
    }

    private void salir() throws RemoteException {
        if (usersOnline.contains(usuarioActual)) {
            user.salir(usuarioActual);
            usersOnline.remove(usuarioActual);
            usuarioActual = null;
            System.out.println("Adios");
            mostrarMenu();
        }
    }
}
