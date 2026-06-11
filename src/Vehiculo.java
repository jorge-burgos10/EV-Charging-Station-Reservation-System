/**
 * Clase Vehiculo
 *
 * Representa un vehículo que puede ser registrado en una estación y reservado.
 * 
 * Estructura:
 * - No utiliza estructuras de colección internas.
 * - Almacena atributos básicos: ID único, tipo, descripción, horas de disponibilidad, estación y dueño.
 */
package src;
import java.time.LocalTime;


public class Vehiculo{         // los atributos de los vehiculos

    private String uniqueID;
    private String tipoVehiculo;
    private String descripcion;
    private LocalTime comienzo;         //comienzo del dia
    private LocalTime termina;          //termina el dia
    private String lugar;
    private String owner;

//constructor
public Vehiculo(String uniqueID,String tipoVehiculo,String descripcion, LocalTime comienzo, LocalTime termina , String lugar, String owner){
    this.uniqueID = uniqueID;
    this.tipoVehiculo = tipoVehiculo;
    this.descripcion = descripcion;
    this.comienzo = comienzo;
    this.termina = termina;
    this.lugar = lugar;
    this.owner = owner;
}
    //verifica que el vehiculo este disponible en la hora escogida
public boolean estaDisponible(LocalTime inicio, LocalTime fin) {
    return (inicio.equals(comienzo) || inicio.isAfter(comienzo) 
            && fin.isBefore(termina) || fin.isBefore(termina));
}

@Override
public String toString() {
    return "Vehiculo{" +    "id ='" + uniqueID + '\'' +
                            ", tipo de vehiculo ='" + tipoVehiculo + '\'' +
                            ", descripcion del vehiculo ='" + descripcion + '\'' +
                            ", disponibilidad =" + comienzo + 
                            "-" + termina +
                            ", lugar ='" + lugar + '\'' +
                            ", owner ='" + owner + '\'' +
                            '}';
}




//Getters
public String getUniqueID(){
    return uniqueID;
}

public String getTipoVehiculo(){
    return tipoVehiculo;
}

public String getDescripcion(){
    return descripcion;
}

public LocalTime getComienzo(){
    return comienzo;
}

public LocalTime getTermina(){
    return termina;
}

public String getLugar(){
    return lugar;
}

public String getOwner(){
    return owner;
}

//Setters

public void setUniqueID(String uniqueID){
    this.uniqueID = uniqueID;
}

public void setTipoVehiculo(String tipoVehiculo){
    this.tipoVehiculo = tipoVehiculo;
}

public void setDescripcion(String descripcion){
    this.descripcion = descripcion;
}

public void setComienzo(LocalTime comienzo){
    this.comienzo = comienzo;
}

public void setTermina(LocalTime termina){
    this.termina = termina;
}

public void setLugar(String lugar){
    this.lugar = lugar;
}

public void setOwner(String owner){
    this.owner = owner;
}

}