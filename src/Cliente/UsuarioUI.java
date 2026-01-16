package Cliente;

// Miguel Angel Giraldo Polanco mgiraldopolanco@gmail.com

import Common.clases.Trino;
import Common.clases.UsuarioData;
import Common.interfaces.ServicioAutenticacionInterface;
import Common.interfaces.ServicioGestorInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class UsuarioUI{
    private final ServicioAutenticacionInterface servicioAuth;
    private final ServicioGestorInterface servicioGestor;
    private CallbackUsuarioImpl miCallback; // El objeto de escucha
    private String usuarioActual = "";


    public UsuarioUI(ServicioAutenticacionInterface auth, ServicioGestorInterface gestor) {
        this.servicioAuth = auth;
        this.servicioGestor = gestor;
    }

    public void mostrarMenu() throws RemoteException {
        int option;
        do {
            option = Buffer.readInt(
                    "\n--- MENÚ CLIENTE ---\n" +
                            "1. Registrar un nuevo usuario.\n" +
                            "2. Hacer login.\n" +
                            "3. Salir.\n"+
                            "Seleccione una opción: \n"
            );

            switch(option){
                case 1: registrarse();
                    break;
                case 2: iniciarSesion();
                    break;
                case 3:
                    System.out.println("Cerrando cliente...");
                    try {
                        if (this.servicioAuth != null) {
                            UnicastRemoteObject.unexportObject(this.servicioAuth, true);
                        }
                    } catch (Exception e) {
                        System.out.println("El objeto remoto ya se había liberado.");
                    }
                    System.out.println("Cliente detenido correctamente.");
                    System.exit(0);
                    break;
                default: System.out.println("Opción inválida");
            }
        } while(option != 0);
    }

    private void registrarse() throws RemoteException {
        String nombre = Buffer.readLine("Ingresa tu nombre\n");
        String nick = Buffer.readLine("Ingresa tu nick\n");
        String clave = Buffer.readLine("Ingresa tu clave\n");

        boolean exito = servicioAuth.registrarUsuario(nombre, nick, clave);
        System.out.println(exito ? "Registro exitoso" : "Error al registrar");
    }

    private void iniciarSesion() throws RemoteException {
        String nick = Buffer.readLine("Ingresa tu nick: ");

        this.miCallback = new CallbackUsuarioImpl();

        List<Trino> trinos = servicioAuth.ingresar(nick, miCallback);

        if (trinos != null) {
            usuarioActual = nick;
            System.out.println("Bienvenido " + nick);

            for (Trino t : trinos) {
                System.out.println("\n Trino >> " + t.GetNickPropietario() + " # " + t.GetTrino());
            }

            int option;
            do {
                option = Buffer.readInt(
                        "\n--- MENÚ PRINCIPAL ---\n" +
                                "1. Información del Usuario\n" +
                                "2. Enviar Trino\n" +
                                "3. Listar Usuarios del Sistema\n" +
                                "4. Seguir a\n" +
                                "5. Dejar de seguir a\n" +
                                "6. Logout\n" +
                                "Seleccione una opción: "
                );

                switch(option) {
                    case 1: infoUser(); break;
                    case 2: crearTrino(); break;
                    case 3: ListarUsuarios(); break;
                    case 4: seguirUsuario(); break;
                    case 5: dejarSeguirUsuario(); break;
                    case 6:
                        salir();
                        option = 0;
                        break;
                    default: System.out.println("Opción inválida");
                }
            } while (option != 0);
        } else {
            System.out.println("Error: Nick no registrado o fallo de conexión.");
        }
    }
    private void infoUser() throws RemoteException {
        UsuarioData us = servicioGestor.getUsuarioData(usuarioActual);
        System.out.println("=== INFORMACION DEL USUARIO" + usuarioActual +" ===");
        System.out.println("Nick: " + us.getNick() +
                            "\n Nombre: " + us.getNombre() +
                            "\n Clave: " + us.getClave());
    }

    private void crearTrino() throws RemoteException {
        String trino = Buffer.readLine("Escribe un trino\n");
        servicioGestor.crearTrino(trino, usuarioActual);
    }

    public void ListarUsuarios() throws RemoteException {
        List<UsuarioData> lista = servicioGestor.getUsers();
        if(lista.isEmpty()) {
            System.out.println("\n No hay usuarios registrados.");
            return;
        }
        System.out.println("=== USUARIOS DEL SISTEMA ===");
        for (UsuarioData us : lista) {
            System.out.println("Nick: " + us.getNick() + " | Nombre: " + us.getNombre());
        }
    }

    private void seguirUsuario() throws RemoteException {
        String usuarioDestino = Buffer.readLine("Escribe el nick del usuario que quieres seguir\n");

        if (usuarioActual.equals(usuarioDestino)) {
            System.out.println("No puedes seguirte a ti mismo");
            return;
        }

        if (servicioGestor.isBlocked(usuarioDestino)) {
            System.out.println("El usuario está bloqueado, no puedes seguirlo");
            return;
        }

        try {
            if (servicioGestor.seguirUsuario(usuarioActual, usuarioDestino)) {
                System.out.println("Has empezado a seguir a: " + usuarioDestino);
            } else {
                System.out.println("No se pudo seguir al usuario. Asegúrate de que el nick es correcto y no lo sigues ya.");
            }
        } catch (Exception e) {
            System.out.println("Error de conexión con el servidor.");
        }
    }

    private void dejarSeguirUsuario() throws RemoteException {
        String usuario = Buffer.readLine("Escribe el nick del usuario que quieres dejar seguir\n");
        if (usuarioActual.equals(usuario)){
            System.out.println("No puedes dejar de seguirte a ti mismo");
            return;
        }
        List<UsuarioData>usuarios = servicioGestor.getUsers();
        boolean existe = usuarios.stream()
                .anyMatch(u -> u.getNick().equals(usuario));
        if(existe) {
            servicioGestor.dejarDeSeguirUsuario(usuarioActual, usuario);
            System.out.println("Haz dejado de seguir a: "+ usuario);
        }else {
            System.out.println("El usuario que quieres dejar de seguir no existe");
        }
    }

    private void salir() throws RemoteException {
        servicioAuth.salir(usuarioActual);
        usuarioActual = "";
        System.out.println("Adios");
        mostrarMenu();
    }
}
