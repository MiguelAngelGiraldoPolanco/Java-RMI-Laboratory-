package Cliente;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Cliente.Buffer;
import Common.clases.UsuarioData;

public class UsuarioUI{
    private final CallbackUsuarioImpl user;
    private Set<String> usersOnline = new HashSet<>();

    public UsuarioUI(CallbackUsuarioImpl user) {
        this.user = user;
    }

    public void mostrarMenu() throws RemoteException {
        int option;
        do {
            option = Buffer.readInt(
                    "1. Iniciar sesión\n" +
                            "2. Registrarse\n" +
                            "3. Salir\n"
            );

            switch(option){
                case 1: iniciarSesion(); break;
                case 2: registrarse(); break;
                case 3: salir(); break;
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
        int option = 0;
        if(trinos != null){
            System.out.println("Bienvenido " + nick);
            for(var trino : trinos){
                System.out.println("Trino reciente: "+trino.GetNickPropietario() + " : "+ trino.GetTrino());
            }
            switch (option){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default: break;
            }
        } else {
            System.out.println("Error al iniciar sesión usuario incorrecto");
        }

    }

    private void salir() throws RemoteException {
        String nick = Buffer.readLine("Ingresa tu nick\n");
        if (usersOnline.contains(nick)){
            user.salir(nick);
            usersOnline.remove(nick);
            System.out.println("Adios");
        }else{
            System.out.println("El Usuario ingresado no esta online");
        }
    }
}
