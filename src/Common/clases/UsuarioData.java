package Common.clases;
// Miguel Angel Giraldo Polanco mgiraldopolanco@gmail.com


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UsuarioData implements Serializable {

    private static final long serialVersionUID = 3L;

    private final String nombre;
    private final String nick;
    private final String clave;
    private boolean isBlocked;
    private boolean isOnline;
    private Set<String> following;

    public UsuarioData(String nombre, String nick, String clave) {
        this.nick = nick;
        this.nombre = nombre;
        this.clave = clave;
        this.isBlocked = false;
        this.isOnline = true;
        this.following = new HashSet<>();
    }

    // getters basicos
    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public String getNick() {
        return nick;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public Set<String> getSeguidores() {
        return following;
    }

    public boolean isOnline(){
        return  isOnline;
    }

    // setters necesarios
    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setFollow(String targetNick) {
        following.add(targetNick);
    }

    // Dejar de seguir
    public void removeFollow(String targetNick){
        following.remove(targetNick);
    }

    //comprobar si la contraseña es correcta
    public boolean checkClave(String password){
        return this.clave.equals(password);
    }

    @Override
    public String toString() {
        return "UsuarioData{" +
                "nick='" + nick + '\'' +
                ", nombre='" + nombre + '\'' +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
