/**
 * Clase Reservacion
 *
 * Representa una reservación realizada por un usuario para un vehículo durante un horario específico.
 * 
 * Estructura:
 * - No usa estructuras internas.
 * - Almacena referencias a un Usuario y a un Vehiculo, junto con la hora de inicio y fin de la reservación.
 */

package src;

import java.time.LocalTime;

public class Reservacion {
    private Usuario usuario;
    private Vehiculo vehiculo;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    //constructor
    public Reservacion(Usuario usuario, Vehiculo vehiculo, LocalTime horaInicio, LocalTime horaFin) {
        this.usuario = usuario;
        this.vehiculo = vehiculo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
    //Getters and Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Reservación {" +
                "Usuario = '" + usuario.getName() + '\'' +
                ", Vehículo = '" + vehiculo.getUniqueID() + '\'' +
                ", Desde = " + horaInicio +
                ", Hasta = " + horaFin +
                '}';
    }
}

