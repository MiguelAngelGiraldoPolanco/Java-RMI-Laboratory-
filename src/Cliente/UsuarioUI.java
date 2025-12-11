package Cliente;

import java.rmi.RemoteException;
import Cliente.Buffer;

public class UsuarioUI{
    private final CallbackUsuarioImpl user;

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
                case 3: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción inválida");
            }
        } while(option != 3);
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
        var trinos = user.ingresar(nick);
        int option = 0;
        if(trinos != null){
            System.out.println("Bienvenido " + nick + ". Trinos recientes:");
            for(var trino : trinos){
                System.out.println(trino.GetNickPropietario() + " : "+ trino.GetTrino());
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
}
