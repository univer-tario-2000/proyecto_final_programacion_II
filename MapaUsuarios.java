import java.util.HashMap;
import java.util.Map;

public class MapaUsuarios {
    private Map<String, Usuario> usuarios;

    public MapaUsuarios() {
        this.usuarios = new HashMap<>();
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getIdUsuario())) {
            System.out.println("Error: El usuario con ID " + usuario.getIdUsuario() + " ya esta registrado.");
        } else {
            usuarios.put(usuario.getIdUsuario(), usuario);
            System.out.println("Usuario registrado exitosamente.");
        }
    }

    public Usuario buscarUsuario(String idUsuario) {
        if (usuarios.containsKey(idUsuario)) {
            return usuarios.get(idUsuario);
        } else {
            return null;
        }
    }

    public void mostrarUsuarios() {
        System.out.println("\n lista de usuarios:");
        if (usuarios.isEmpty()) {
            System.out.println("Aun no hay usuarios en el sistema.");
            return;
        }

        for (Usuario u : usuarios.values()) {
            System.out.println("ID: " + u.getIdUsuario() + " | Nombre: " + u.getNombre());
        }
        System.out.println("Total de usuarios: " + usuarios.size());
    }
}