/**
 * Clase Main
 *
 * Controla el flujo principal del sistema: usuarios, vehículos y reservaciones.
 * 
 * Estructura:
 * - Se usa un HashSet para almacenar los vehículos registrados porque asegura unicidad (no duplicados) y operaciones rápidas de búsqueda y eliminación.
 * - Se usa un HashMap para almacenar las estaciones, ya que permite acceso rápido basado en el nombre (clave) de la estación.
 */
package src;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;

public class Main {
    private static HashSet<Vehiculo> registrado = new HashSet<>();
    private static HashMap<String,Estacion> estaciones = new HashMap<>();
    private static UsuarioManager usuarioManager = new UsuarioManager();
    private static Stack<String> acciones = new Stack<>();



    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
//----------------------------------Menu--------------------------------------------
        //Todas las estaciones que tenemos
        estaciones.put("Stefani", new Estacion("Stefani", 40));
        estaciones.put("Centro de Estudiantes", new Estacion("Centro de Estudiantes", 90));
        estaciones.put("Biologia", new Estacion("Biologia", 35));
        estaciones.put("Ingenieria Quimica", new Estacion("Ingenieria Quimica", 45));
        estaciones.put("Administracion Empresas", new Estacion("Administracion Empresas", 45));
        
        //Menu Principal
        int botonMenu = -1;

        do{

            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Anadir,Modificar o eliminar Usuario");
            System.out.println("2. Anadir,Modificar o eliminar Vehículo");
            System.out.println("3. Anadir,Modificar o eliminar Reservacion");
            System.out.println("4. Mostrar Vehículos de Estación");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            botonMenu = Integer.parseInt(scanner.nextLine());
        
        switch (botonMenu) {
            case 1:
                menuUsuario(scanner);      
                break;
            case 2:
                menuVehiculo(scanner);
                break;
            case 3:
                menuReservacion(scanner);
            case 4:
                mostrarVehiculos();
                break;
            case 0:
                System.out.println("Cerrando sesión..");
                break;
            default:
                System.out.println("Opción erronea, escoger otro numero");
        }
    }while (botonMenu != 0);

    scanner.close();
    } 
//------------------------------------Vehiculo--------------------------------------------
    //display que nos envia una vez escogemos seleccion de vehiculos
    private static void menuVehiculo(Scanner scanner) {
        int botonVehiculo = -1;
        do {
            System.out.println("\n=== Selección de Vehículo ===");
            System.out.println("1. Añadir Vehículo");
            System.out.println("2. Modificar Vehículo");
            System.out.println("3. Eliminar Vehículo");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            botonVehiculo = Integer.parseInt(scanner.nextLine());

            switch (botonVehiculo) {
                case 1:
                    registrarVehiculo(scanner);
                    break;
                case 2:
                    modificarVehiculo(scanner);
                    break;
                case 3:
                    eliminarVehiculo(scanner);
                    break;
                case 0:
                    System.out.println("Regresando al Menu..");
                    break;
                default:
                    System.out.println("Opción erronea, esocge otro numero");
            }
        } while (botonVehiculo != 0);
    }

    //selecciones dentro de vehiculo
    //agregar vehiculo
    private static void registrarVehiculo(Scanner scanner){

    
            System.out.println("=== Agregar Vehículo ===");
            String id;
            while (true) {
                System.out.print("Ingrese los últimos 4 de su # de estudiante como ID unico: ");
                id = scanner.nextLine();
                if (id.matches("\\d{4}")) {
                    break;
                } else {
                    System.out.println("Debe ingresar exactamente 4 números. Intente de nuevo.");
                }
            }
        
            // Generar un ID único basado en los últimos 4 dígitos
            String idFinal = id; // Empezamos con el ID básico
            int contador = 1;
            boolean idExistente = true;

            while (idExistente) {
                idExistente = false;
                for (Vehiculo v : registrado) {
                    if (v.getUniqueID().equals(idFinal)) {
                        idExistente = true;
                        break;
                    }
                }
                if (idExistente) {
                    idFinal = id + "-" + contador;
                    contador++;
                }
            }
            // Buscar si existe un usuario registrado con esos últimos 4 dígitos
            Usuario ownerUsuario = null;
            for (Usuario u : usuarioManager.getAllUsers()) { 
                if (u.getNumStudent().endsWith(id)) {
                    ownerUsuario = u;
                    break;
                }
            }

            if (ownerUsuario == null) {
                System.out.println("No existe un usuario registrado con esos últimos 4 dígitos de estudiante.");
                return;
            }

            // Verificar que sea dueño o cliente/dueño
            if (!ownerUsuario.isOwner()) {
                System.out.println("El usuario no está registrado como dueño. No puede registrar un vehículo.");
                return;
            }

    
            // Mostrar tipos de vehículos permitidos
            System.out.println("\nTipos de vehículos disponibles:");
            System.out.println("- Bike");
            System.out.println("- Scooter");
            System.out.println("- Skateboard");

            String tipo;
            while (true) {
                System.out.print("Tipo de vehículo: ");
                tipo = scanner.nextLine();
                if (tipo.equalsIgnoreCase("Bike") || 
                    tipo.equalsIgnoreCase("Scooter") || 
                    tipo.equalsIgnoreCase("Skateboard")) {
                    break;
                } else {
                    System.out.println("Tipo inválido. Solo se permiten Bike, Scooter o Skateboard.");
                }
            }
    
            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();
    
            System.out.print("Hora de comienzo: ");
            LocalTime comienzo = LocalTime.parse(scanner.nextLine());
    
            System.out.print("Hora de terminar: ");
            LocalTime termina = LocalTime.parse(scanner.nextLine());


            System.out.println("\n=== Estaciones Disponibles ===");
            for (String nombre : estaciones.keySet()) {
                System.out.println("- " + nombre);
            }
        
            System.out.print("Lugar: ");
            String lugar = scanner.nextLine();
        
            Estacion estacionSeleccionada = estaciones.get(lugar);
        
            if (estacionSeleccionada == null) {
                System.out.println("Estación '" + lugar + "' no encontrada.");
                return; 
            }

    
            // Verificar cuántos vehículos tiene este dueño ya registrados
            int cantidadVehiculosDelDueno = 0;
            for (Vehiculo v : registrado) {
                if (v.getOwner().equalsIgnoreCase(ownerUsuario.getName())) {
                    cantidadVehiculosDelDueno++;
                }
            }

            if (cantidadVehiculosDelDueno >= 2) {
                System.out.println("El dueño '" + ownerUsuario.getName() + "' ya tiene 2 vehículos registrados. No se puede registrar más.");
                return; // No deja agregar el nuevo vehículo
            }
    
            Vehiculo vehiculoRegistrado = new Vehiculo(idFinal, tipo, descripcion, comienzo, termina, lugar, ownerUsuario.getName());

            boolean agregado = estacionSeleccionada.agregarVehiculo(vehiculoRegistrado);

            if(agregado){
                registrado.add(vehiculoRegistrado);
                System.out.println("Vehículo registrado exitosamente para " + ownerUsuario.getName()); 
            }else{
                System.out.println("No se pudo anadir el vehiculo");
            }
           
            //mostrarVehiculos();
        }

        //verifica si el vehiculo esta registrado. Si lo esta puedes modificarlo
    private static void modificarVehiculo(Scanner scanner) {
        System.out.println("\n=== Modificar Vehículo ===");


        if (registrado.isEmpty()) {
            System.out.println("No hay vehículos registrados aún.");
            return;
        }

        System.out.println("Ingrese el ID del vehículo que quiere modificar: ");
        String buscarID = scanner.nextLine();

        Vehiculo encontrado = null;
        for (Vehiculo v : registrado) {
            if (v.getUniqueID().equals(buscarID)) {
                encontrado = v;
                break;
            }
        }

        if (encontrado != null) {
            System.out.println("Vehículo encontrado: " + encontrado);

            System.out.print("Nueva descripción: ");
            String nuevaDescripcion = scanner.nextLine();
            encontrado.setDescripcion(nuevaDescripcion);

            System.out.print("Nuevo dueño: ");
            String nuevoOwner = scanner.nextLine();
            encontrado.setOwner(nuevoOwner);

            System.out.println("Vehículo modificado.");
        } else {
            System.out.println("No encontrado.");
        }
    }

        //verifica si el vehiculo esta. Si lo esta, lo remueve de la lista
    private static void eliminarVehiculo(Scanner scanner) {
        System.out.println("\n=== Eliminar Vehículo ===");

        if (registrado.isEmpty()) {
            System.out.println("No hay vehículos registrados aún.");
            return;
        }

        System.out.print("Ingrese el ID del vehículo que quiere eliminar: ");
        String buscarID = scanner.nextLine();

        Vehiculo encontrado = null;
        for (Vehiculo v : registrado) {
            if (v.getUniqueID().equals(buscarID)) {
                encontrado = v;
                break;
            }
        }

        if (encontrado != null) {
            registrado.remove(encontrado);

            Estacion estacion = estaciones.get(encontrado.getLugar());
            if (estacion != null) {
                estacion.removerVehiculoPorID(buscarID);
                estacion.checkListaEspera();
            }

            System.out.println("Vehículo eliminado.");
        } else {
            System.out.println("No encontrado.");
        }
    }
    //muestra los vehiculos actualmente registrado
    private static void mostrarVehiculos() {
        System.out.println("\n=== Vehículos Registrados en Estaciones ===");
    
        for (String nombre : estaciones.keySet()) {
            System.out.println("\n>> Estación: " + nombre);

            Estacion estacionNombre = estaciones.get(nombre);
            estacionNombre.mostrarVehiculos();
        }
    }

//----------------------------Reservaciones-------------------------------------------
    private static void menuReservacion(Scanner scanner) {
        int botonReservacion = -1;
        do {
            System.out.println("\n=== Menú de Reservaciones ===");
            System.out.println("1. Anadir una reservación");
            System.out.println("2. Modificar una reservación");
            System.out.println("3. Eliminar una reservación");
            System.out.println("4. Mostrar historial de reservaciones");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            botonReservacion = Integer.parseInt(scanner.nextLine());
    
            switch (botonReservacion) {
                case 1:
                    crearReservacion(scanner);
                    break;
                case 2:
                    modificarReservacion(scanner);
                    break;
                case 3:
                    eliminarReservacion(scanner);
                    break;
                case 4:
                    mostrarHistorialReservaciones(scanner);
                    break;
                case 5:
                    undo();
                    break;
                case 0:
                    System.out.println("Regresando al menú principal..");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (botonReservacion != 0);
    }
    
    private static void crearReservacion(Scanner scanner) {

        System.out.println("\n=== Crear Reservación ===");
        System.out.println("Vehículos disponibles para reservar:");
        boolean hayVehiculosDisponibles = false;
        for (Estacion estacion : estaciones.values()) {
            for (Vehiculo v : estacion.getVehiculosDisponibles()) {
                System.out.println("- ID: " + v.getUniqueID() + ", Tipo: " + v.getTipoVehiculo() + ", Lugar: " + v.getLugar() 
                + ", Horario disponible: " + v.getComienzo() + " a " + v.getTermina());
                hayVehiculosDisponibles = true;
            }
        }
    
        if (!hayVehiculosDisponibles) {
            System.out.println("No hay vehículos disponibles para reservar.");
            return;
        }
    
        System.out.print("\nIngrese el ID del vehículo a reservar: ");
        String idVehiculo = scanner.nextLine();
    
        Vehiculo vehiculoSeleccionado = null;
        Estacion estacionDelVehiculo = null;
        for (Estacion est : estaciones.values()) {
            for (Vehiculo v : est.getVehiculosDisponibles()) {
                if (v.getUniqueID().equals(idVehiculo)) {
                    vehiculoSeleccionado = v;
                    estacionDelVehiculo = est;
                    break;
                }
            }
            if (vehiculoSeleccionado != null) break;
        }
    
        if (vehiculoSeleccionado == null) {
            System.out.println("Vehículo no encontrado.");
            return;
        }
    
        // Ahora pedimos el número de estudiante
        System.out.print("\nNúmero de estudiante del usuario (formato 000-00-0000): ");
        String numEstudiante = scanner.nextLine();
    
        Usuario usuarioReservacion = usuarioManager.searchUser(numEstudiante);
    
        if (usuarioReservacion == null) {
            System.out.println("Usuario no encontrado. ¿Desea registrarlo? (si/no): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
    
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
                String telefono = scanner.nextLine();
    
                // Por defecto lo registramos como cliente para poder reservar
                usuarioReservacion = new Usuario(nombre, numEstudiante, email, telefono, true, false);
                usuarioManager.addUser(usuarioReservacion);
                System.out.println("Usuario registrado exitosamente.");
            } else {
                System.out.println("Debe estar registrado para hacer una reservación.");
                return;
            }
        }
    
        // Verificar que pueda reservar
        if (!usuarioReservacion.isClient()) {
            System.out.println("Este usuario no está registrado como cliente o dueño, no puede reservar.");
            return;
        }
    
        // Hora de inicio y fin
        System.out.print("Hora de inicio (formato 00:00): ");
        LocalTime inicio = LocalTime.parse(scanner.nextLine());
    
        System.out.print("Hora de fin (formato 00:00): ");
        LocalTime fin = LocalTime.parse(scanner.nextLine());
    
        // Verificar disponibilidad en ese horario
        if (!vehiculoSeleccionado.estaDisponible(inicio, fin)) {
            System.out.println("El vehículo no está disponible en ese horario.");
            return;
        }
    
        // Mostrar el costo
        double costoTotal = calcularCosto(vehiculoSeleccionado.getTipoVehiculo(), inicio, fin);
        System.out.println("Costo total: " + costoTotal + " créditos.");
    
        // Hacer la reservación
        boolean reservacionExitosa = estacionDelVehiculo.hacerReservacion(usuarioReservacion, vehiculoSeleccionado, inicio, fin);
    
        if (reservacionExitosa) {
            registrado.remove(vehiculoSeleccionado); // Ya no está disponible en el set de registrados
            acciones.push("crear");
        }
    }

    
        //Primero chequea que la estacion exista, una vez confirmado este busca el nombre del usuario 
        //provisto en la base de datos, donde si se encuentra le permite cambiar la hora al que hizo la reservacion
    private static void modificarReservacion(Scanner scanner) {
        System.out.println("\n=== Modificar Reservación ===");
    
        System.out.print("Nombre de la estación: ");
        String nombreEstacion = scanner.nextLine();
        Estacion estacion = estaciones.get(nombreEstacion);
    
        if (estacion == null) {
            System.out.println("Estación no encontrada.");
            return;
        }
    
        System.out.print("Nombre del usuario: ");
        String nombreUsuario = scanner.nextLine();
    
        Reservacion reservacionEncontrada = null;
        for (Reservacion reserv : estacion.getHistorialReservaciones()) {
            if (reserv.getUsuario().getName().equalsIgnoreCase(nombreUsuario)) {
                reservacionEncontrada = reserv;
                break;
            }
        }
    
        if (reservacionEncontrada == null) {
            System.out.println("Reservación no encontrada.");
            return;
        }
    
        System.out.println("Reservación encontrada: " + reservacionEncontrada);
    
        System.out.print("Nueva hora de inicio (00:00): ");
        LocalTime nuevaInicio = LocalTime.parse(scanner.nextLine());
    
        System.out.print("Nueva hora de fin (00:00): ");
        LocalTime nuevaFin = LocalTime.parse(scanner.nextLine());
    
        reservacionEncontrada.setHoraInicio(nuevaInicio);
        reservacionEncontrada.setHoraFin(nuevaFin);
    
        double nuevoCosto = calcularCosto(reservacionEncontrada.getVehiculo().getTipoVehiculo(), nuevaInicio, nuevaFin);
        System.out.println("Nuevo costo recalculado: " + nuevoCosto + " créditos.");
    
        acciones.push("modificar");
    }


        //primero verifica que la estacion existe, luego. Una vez hecho, busca en la lista de datos
        //el nombre del usuario, una vez encontrado elimina la reservacion y se pone el vehiculo disponible de nuevo
    private static void eliminarReservacion(Scanner scanner) {
        System.out.println("\n=== Eliminar Reservación ===");
    
        System.out.print("Nombre de la estación: ");
        String nombreEstacion = scanner.nextLine();
        Estacion estacion = estaciones.get(nombreEstacion);
    
        if (estacion == null) {
            System.out.println("Estación no encontrada.");
            return;
        }
    
        System.out.print("Nombre del usuario: ");
        String nombreUsuario = scanner.nextLine();
    
        Reservacion reservacionEncontrada = null;
        for (Reservacion reserv : estacion.getHistorialReservaciones()) {
            if (reserv.getUsuario().getName().equalsIgnoreCase(nombreUsuario)) {
                reservacionEncontrada = reserv;
                break;
            }
        }
    
        if (reservacionEncontrada == null) {
            System.out.println("Reservación no encontrada.");
            return;
        }
    
        Vehiculo vehiculoLiberado = reservacionEncontrada.getVehiculo();
        estacion.getVehiculosDisponibles().add(vehiculoLiberado);
        estacion.getHistorialReservaciones().remove(reservacionEncontrada);
    
        System.out.println("Reservación eliminada y vehículo liberado.");
    
        if (!estacion.getListaEspera().isEmpty()) {
            Usuario usuarioEnEspera = estacion.sacarUsuarioEspera();
            System.out.println("Hay un usuario en lista de espera: " + usuarioEnEspera.getName());
        }
        acciones.push("eliminar");
    }


        //muestra el historial de reservaciones de la estacion seleccionada
        private static void mostrarHistorialReservaciones(Scanner scanner) {
            System.out.println("\n=== Mostrar Historial de Reservaciones ===");
        
            System.out.println("Seleccione estación:");
            for (String nombre : estaciones.keySet()) {
                System.out.println("- " + nombre);
            }
        
            System.out.print("Nombre de la estación: ");
            String nombreEstacion = scanner.nextLine();
            
            Estacion estacion = estaciones.get(nombreEstacion);
            if (estacion != null) {
                estacion.mostrarHistorialReservaciones();
            } else {
                System.out.println("Estación no encontrada.");
            }
        }
    
    
            //acciones guarda la ultima accion tomada en reservaciones, y al llamarlo elimina la ultima accion
        private static void undo() {
            if (acciones.isEmpty()) {
                System.out.println("No hay acciones para deshacer.");
                return;
            }
        
            String ultimaAccion = acciones.pop();
        
            switch (ultimaAccion) {
                case "crear":
                    System.out.println("Última acción fue crear una reservación.Deschacer ultima accion.");
                    break;
                case "modificar":
                    System.out.println("Última acción fue modificar una reservación.Deschacer ultima accion");
                    break;
                case "eliminar":
                    System.out.println("Última acción fue eliminar una reservación.Deschacer ultima accion");
                    break;
                default:
                    System.out.println("Acción desconocida: " + ultimaAccion);
                    break;
            }
        }
        
//-------------------------Calcular---------------------------------
    
            //metodo para calcular el credito
        private static double calcularCosto(String tipo, LocalTime inicio, LocalTime fin) {
            Duration dur = Duration.between(inicio, fin);
            long horas = dur.toHours();
            long minutos = dur.toMinutesPart();
            double total = horas + (minutos > 0 ? 1 : 0);
            switch (tipo.toLowerCase()) {
                case "bike":
                    return 3 + 2 * (total - 1);
                case "scooter":
                    return 2 + 1 * (total - 1);
                case "skateboard":
                    return 1 + 0.5 * (total - 1);
                default:
                    return 0;
            }
        }

//-------------------------------------Usuario-------------------------------------
    private static void menuUsuario(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n=== Sistema de Usuarios ===");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Modificar usuario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Mostrar usuarios");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());
    
            switch (opcion) {
                case 1:
                    addUserMenu(scanner);
                    break;
                case 2:
                    modUserMenu(scanner);
                    break;
                case 3:
                    deleteUserMenu(scanner);
                    break;
                case 4:
                    showUsersMenu();
                    break;
                case 0:
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }
    
    private static void addUserMenu(Scanner scanner) {
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
    
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
        boolean isClient = Boolean.parseBoolean(scanner.nextLine());
    
        System.out.print("¿Es dueño? (true/false): ");
        boolean isOwner = Boolean.parseBoolean(scanner.nextLine());
    
        try {
            Usuario newUser = new Usuario(name, numStudent, email, phone, isClient, isOwner);
            usuarioManager.addUser(newUser);
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
        }
    }
    
    private static void modUserMenu(Scanner scanner) {
        if (usuarioManager.isEmpty()) {
            System.out.println("No hay usuarios registrados aún.");
            return;
        }
        System.out.print("Número de estudiante del usuario a modificar: ");
        String numMod = scanner.nextLine();
        System.out.print("Nuevo nombre: ");
        String newName = scanner.nextLine();
        System.out.print("Nuevo teléfono: ");
        String newPhone = scanner.nextLine();
        usuarioManager.modUser(numMod, newName, newPhone);
    }
    
    private static void deleteUserMenu(Scanner scanner) {
        if (usuarioManager.isEmpty()) {
            System.out.println("No hay usuarios registrados aún.");
            return;
        }
        System.out.print("Número de estudiante del usuario a eliminar: ");
        String numStudent = scanner.nextLine();
        usuarioManager.deleteUser(numStudent);
    }
    
    private static void showUsersMenu() {
        usuarioManager.showUsers();
    }
    
    
    
}