 /*Clase UsuarioManager
 *
 * Representa el sistema desde el punto de vista de el operador, que mantiene una lista de todos los usuarios
 *
 *Estructura:
 *-Se utiliza un HashSet<String, Usuario> donde se guarda el nombre de los usuarios y sus numeros de estudiantes
 */
package src;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UsuarioManager {
    private HashMap<String, Usuario> users; // clave: número de estudiante

    public UsuarioManager() {
        users = new HashMap<>();
    }

    // Añadir usuario
    public void addUser(Usuario usuario) {
        if (users.containsKey(usuario.getNumStudent())) {
            System.out.println("Ya existe un usuario con ese número de estudiante.");
        } else {
            users.put(usuario.getNumStudent(), usuario);
            System.out.println("Usuario agregado exitosamente.");
        }
    }

    // Modificar usuario
    public void modUser(String numStudent, String newName, String newPhone) {
        Usuario usuario = users.get(numStudent);
        if (usuario != null) {
            usuario.setName(newName);
            usuario.setPhone(newPhone);
            System.out.println("Usuario modificado exitosamente.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    // Eliminar usuario
    public void deleteUser(String numStudent) {
        if (users.remove(numStudent) != null) {
            System.out.println("Usuario eliminado exitosamente.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    // Mostrar todos los usuarios
    public void showUsers() {
        if (users.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Map.Entry<String, Usuario> entry : users.entrySet()) {
                System.out.println(entry.getValue());
            }
        }
    }

    // Buscar un usuario específico
    public Usuario searchUser(String numStudent) {
        return users.get(numStudent);
    }
    public Collection<Usuario> getAllUsers() {
    return users.values();
}
    public boolean isEmpty() {
        return users.isEmpty();
    }
}
