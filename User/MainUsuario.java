package User;
import java.util.Scanner;

import src.Usuario;
import src.UsuarioManager;

public class MainUsuario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsuarioManager usuarioManager = new UsuarioManager();

        int opcion;
        do {
            System.out.println("\n=== Sistema de Usuarios ===");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Modificar usuario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Mostrar usuarios");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine();

                    // Validar número de estudiante
                    String numStudent;
                    while (true) {
                        System.out.print("Número de estudiante (formato 000-00-0000): ");
                        numStudent = scanner.nextLine();
                        if (numStudent.matches("\\d{3}-\\d{2}-\\d{4}")) {
                            break;
                        } else {
                            System.out.println("Formato incorrecto. Intente de nuevo.");
                        }
                    }

                    // Validar email UPR
                    String email;
                    while (true) {
                        System.out.print("Email UPR: ");
                        email = scanner.nextLine();
                        if (email.toLowerCase().endsWith("@upr.edu")) {
                            break;
                        } else {
                            System.out.println("Email debe terminar en @upr.edu. Intente de nuevo.");
                        }
                    }

                    System.out.print("Teléfono: ");
                    String phone = scanner.nextLine();

                    System.out.print("¿Es cliente? (true/false): ");
                    boolean isClient = scanner.nextBoolean();
                    System.out.print("¿Es dueño? (true/false): ");
                    boolean isOwner = scanner.nextBoolean();
                    scanner.nextLine(); // limpiar buffer

                    try {
                        Usuario newUser = new Usuario(name, numStudent, email, phone, isClient, isOwner);
                        usuarioManager.addUser(newUser);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error al crear usuario: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Número de estudiante del usuario a modificar: ");
                    String numMod = scanner.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String newName = scanner.nextLine();
                    System.out.print("Nuevo teléfono: ");
                    String newPhone = scanner.nextLine();
                    usuarioManager.modUser(numMod, newName, newPhone);
                    break;

                case 3:
                    System.out.print("Número de estudiante del usuario a eliminar: ");
                    String numeroElim = scanner.nextLine();
                    usuarioManager.deleteUser(numeroElim);
                    break;

                case 4:
                    usuarioManager.showUsers();
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}
