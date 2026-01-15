package Cliente;

import Common.clases.UsuarioData;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsuarioUI{
    private final CallbackUsuarioImpl user;
    private String usuarioActual = "" ;

    public UsuarioUI(CallbackUsuarioImpl user) {
        this.user = user;
    }

    public void mostrarMenu() throws RemoteException {
        int option;
        do {
            option = Buffer.readInt(
                    "1. Registrar un nuevo usuario.\n" +
                            "2. Hacer login.\n" +
                            "3. Salir."
            );

            switch(option){
                case 1: registrarse();
                    break;
                case 2: iniciarSesion();
                    break;
                case 3:   System.out.println("Cliente detenido.");
                    UnicastRemoteObject.unexportObject(user, true);
                    System.exit(0);
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
        UsuarioData usuario = user.getUser(nick);
        if ( usuario.isOnline() == true ){
            System.out.println("Ya ha iniciado sesion");
            return;
        }
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
                        "1. Informacion del Usuario\n" +
                                "2. Enviar Trino\n" +
                                "3.  Listar Usuarios del Sistema.\n" +
                                "4. Seguir a\n" +
                                "5. Dejar de seguir a.\n" +
                                "6. Logout\n"
                );

                switch(option){
                    case 1: infoUser();
                        break;
                    case 2: crearTrino();
                        break;
                    case 3: ListarUsuarios();
                        break;
                    case 4: seguirUsuario();
                        break;
                    case 5: dejarSeguirUsuario();
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
    private void infoUser() throws RemoteException {
        UsuarioData us = user.getUser(usuarioActual);
        System.out.println("=== INFORMACION DEL USUARIO" + usuarioActual +" ===");
        System.out.println("Nick: " + us.getNick() +
                            "\n Nombre: " + us.getNombre() +
                            "\n Clave: " + us.getClave());
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
        try {
            if (user.seguirUsuario(usuarioActual, usuario)) {
                System.out.println("Has empezado a seguir a: " + usuario);
            } else {
                System.out.println("Ya sigues a este usuario");
            }
        } catch (Exception e) {
            System.out.println("No puedes seguirte a ti mismo");
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

    private void salir() throws RemoteException {
       // if (usersOnline.contains(usuarioActual)) {
            user.salir(usuarioActual);
         //   usersOnline.remove(usuarioActual);
            usuarioActual = null;
            System.out.println("Adios");
           // mostrarMenu();
        }
    }
}
