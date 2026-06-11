/**
 * Clase Estacion
 *
 * Representa una estación de vehículos disponibles para reservaciones.
 * 
 * Estructura:
 * - HashSet<Vehiculo> vehiculosDisponibles: asegura que no haya duplicados y permite búsquedas eficientes de vehículos disponibles.
 * - LinkedList<Usuario> listaEspera: permite añadir usuarios al final (cuando esperan) y sacarlos del inicio (cuando hay espacio), operaciones eficientes en una LinkedList.
 * - LinkedList<Reservacion> historialReservaciones: guarda reservaciones en el orden en que fueron creadas, ideal para recorrer cronológicamente.
 */
package src;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Estacion {
    private String nombre;
    private int capacidadMaxima;
    private Set<Vehiculo> vehiculosDisponibles;
    private Queue<Usuario> listaEspera;
    private LinkedList<Reservacion> historialReservaciones;

    public Estacion(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.vehiculosDisponibles = new HashSet<>();
        this.listaEspera = new LinkedList<>();
        this.historialReservaciones = new LinkedList<>();
    }

    //verifica si hay espacio, y si lo hay anade el vehiculo. 
    public boolean agregarVehiculo(Vehiculo agregarVehiculo) {
        if (vehiculosDisponibles.size() < capacidadMaxima) {
            vehiculosDisponibles.add(agregarVehiculo);
            return true;

        } else {
            System.out.println("No hay mas espacios disponibles.");
            return false;
        }
    }

    //busca el ID del vehiculo y compararlo con el que el usuario subio. si coincide, se borra el vehiculo.
    //Se usa el UniqueID ya que es un iD unico...wow. Pero solo el usuario se supone que lo tenga, por lo que no puede ser replicado por otro.
    public boolean removerVehiculoPorID(String vehiculoID) {
        for (Vehiculo v : vehiculosDisponibles) {
            if (v.getUniqueID().equals(vehiculoID)) {
                vehiculosDisponibles.remove(v);
                return true;
            }
        }
        return false;
    }

    //pretty self explanatory
    public void agregarUsuarioEspera(Usuario usuarioEspera) {
        listaEspera.add(usuarioEspera);
    }

    public Usuario sacarUsuarioEspera() {
        return listaEspera.poll(); 
    }

    //chequea si hay espacio disponible
    public void checkListaEspera() {
        if (!listaEspera.isEmpty() && vehiculosDisponibles.size() < capacidadMaxima) {
            System.out.println("Hay espacio en " + nombre);
        }
    }

    //self explanatory
    public int espaciosDisponibles() {
        return capacidadMaxima - vehiculosDisponibles.size();
    }

    //chequea si hay vehiculos disponibles...again. 
    //Si los hay, anadira la lista de los vehiculos disponibles
    public void mostrarVehiculos() {
        System.out.println("Vehículos en " + nombre + ":");
        if (vehiculosDisponibles.isEmpty()) {                           
            System.out.println("No hay vehículos disponibles.");

        } else {
            for (Vehiculo vehiculosDisp : vehiculosDisponibles) {
                System.out.println(vehiculosDisp);
            }
        }
    }
    //Getters
    public String getUniqueID() {
        return nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public Set<Vehiculo> getVehiculosDisponibles() {
        return vehiculosDisponibles;
    }
    
    public LinkedList<Reservacion> getHistorialReservaciones() {
        return historialReservaciones;
    }

    public Queue<Usuario> getListaEspera() {
        return listaEspera;
    }

    //un Metodo boolean en que primero verifica que el carro este disponible. Una vez confirme, verifica
    //que la hora en que esta siendo pedida sea entre las horas de negocio. Luego verifica que el uso de un
    //vehiculo no exceda las 6 horas de uso, y finalmente verifica que no tenga mas de un vehiculo activamente
    //siendo usado
    public boolean hacerReservacion(Usuario usuario, Vehiculo vehiculo, LocalTime inicio, LocalTime fin) {
        if (vehiculosDisponibles.contains(vehiculo)) {
            if(inicio.isAfter(fin)){
                System.out.println("La hora de inicio debe ser antes de la hora de fin.");
                return false;  
            }
            if (inicio.isBefore(LocalTime.of(7, 0)) || fin.isAfter(LocalTime.of(18, 0))) {
                System.out.println("La reservación debe ser entre 7:00 AM y 6:00 PM.");
                return false;
            }
            // Validar que el tiempo de uso no sea mas de 6h
            long horasDeUso = java.time.Duration.between(inicio, fin).toHours();
            if (horasDeUso > 6) {
                System.out.println("No se puede reservar más de 6 horas continuas.");
                return false;
            }
            // Validar que el usuario no tenga otra reservación activa
            for (Reservacion r : historialReservaciones) {
                if (r.getUsuario().getNumStudent().equals(usuario.getNumStudent())) {
                    // Ya tiene una reservación activa
                    System.out.println("Este usuario ya tiene una reservación activa. No puede reservar otro vehículo.");
                    return false;
                }
            }
    
            Reservacion nuevaReservacion = new Reservacion(usuario, vehiculo, inicio, fin);
            historialReservaciones.add(nuevaReservacion);
            vehiculosDisponibles.remove(vehiculo); // El vehículo ya no está disponible
            System.out.println("Reservación creada exitosamente para " + usuario.getName());
            return true;
        } else {
            System.out.println("El vehículo no está disponible para reservar.");
            return false;
        }
    }

    public void mostrarHistorialReservaciones() {
        System.out.println("\nHistorial de reservaciones en " + nombre + ":");
        if (historialReservaciones.isEmpty()) {
            System.out.println("No hay reservaciones registradas.");
        } else {
            for (Reservacion r : historialReservaciones) {
                System.out.println(r);
            }
        }
    }
    public boolean historialVacio() {
        return historialReservaciones.isEmpty();
    }

}
