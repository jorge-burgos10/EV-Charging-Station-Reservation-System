# Personal Electric Vehicle Reservation System

A Java console application for managing reservations of personal electric vehicles (bikes, scooters, and skateboards) at university charging stations. Built as a university project for UPR (Universidad de Puerto Rico).

## Overview

The system lets students register as **clients** (renters) or **owners** (vehicle providers), list their vehicles at campus stations, and book available vehicles within defined time windows. Pricing is calculated in credits based on vehicle type and rental duration.

## Features

- **User management** — Register, update, delete, and list users with UPR student ID and `@upr.edu` email validation
- **Vehicle management** — Owners can register up to 2 vehicles (Bike, Scooter, or Skateboard) at a station
- **Reservations** — Clients can reserve available vehicles, view history, modify or cancel bookings
- **Station capacity** — Each station has a maximum capacity; wait lists are supported when stations are full
- **Business rules** — Reservations allowed between 7:00 AM and 6:00 PM, max 6 hours per booking, one active reservation per user
- **Credit-based pricing** — Cost varies by vehicle type and duration

## Project Structure

```
├── src/
│   ├── Main.java           # Main application entry point
│   ├── Usuario.java        # User model
│   ├── UsuarioManager.java # User CRUD operations
│   ├── Vehiculo.java       # Vehicle model
│   ├── Estacion.java       # Station model (capacity, wait list, reservations)
│   └── Reservacion.java    # Reservation model
└── User/
    └── MainUsuario.java    # Standalone user management demo
```

## Data Structures

| Structure | Usage |
|-----------|-------|
| `HashSet<Vehiculo>` | Registered vehicles (unique IDs, fast lookup) |
| `HashMap<String, Estacion>` | Stations indexed by name |
| `HashMap<String, Usuario>` | Users indexed by student number |
| `LinkedList<Reservacion>` | Reservation history per station |
| `Queue<Usuario>` | Wait list per station |
| `Stack<String>` | Undo stack for reservation actions |

## Campus Stations

| Station | Max Capacity |
|---------|--------------|
| Stefani | 40 |
| Centro de Estudiantes | 90 |
| Biologia | 35 |
| Ingenieria Quimica | 45 |
| Administracion Empresas | 45 |

## Pricing (Credits)

| Vehicle | Base | Per additional hour |
|---------|------|---------------------|
| Bike | 3 | +2 |
| Scooter | 2 | +1 |
| Skateboard | 1 | +0.5 |

Partial hours are rounded up to the next full hour.

## Requirements

- Java 8 or higher (uses `java.time.LocalTime`)

## How to Run

From the project root, compile and run the main application:

```bash
javac -d out src/*.java User/*.java
java -cp out src.Main
```

To run the standalone user management module:

```bash
java -cp out User.MainUsuario
```

On Windows (PowerShell), from the project directory:

```powershell
New-Item -ItemType Directory -Force -Path out
javac -d out src\*.java User\*.java
java -cp out src.Main
```

## Usage

The application menu is in Spanish. Main options:

1. **Users** — Add, modify, or delete users (client/owner roles)
2. **Vehicles** — Register vehicles linked to owner student IDs (last 4 digits)
3. **Reservations** — Create, modify, delete, or view reservation history
4. **View stations** — List vehicles currently available at each station

### User roles

- **Client** — Can reserve vehicles
- **Owner** — Can register vehicles (max 2 per owner)
- A user can be both client and owner

### Vehicle registration

- Vehicle ID is based on the last 4 digits of the owner's student number
- Owner must be registered before adding a vehicle
- Vehicle must be placed at one of the predefined stations

## Authors

Jorge and Malik — University project (UPR)

## License

Academic / educational use.
