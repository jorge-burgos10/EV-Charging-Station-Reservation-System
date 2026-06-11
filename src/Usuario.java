package src;
public class Usuario {
    // Variables
    private String name;
    private String numStudent;
    private String emailUPR;
    private String phone;
    private boolean isClient;
    private boolean isOwner;

    // Constructor
    public Usuario(String name, String numStudent, String emailUPR, String phone, boolean isClient, boolean isOwner) {
        this.name = name;
        setNumStudent(numStudent); 
        setEmailUPR(emailUPR); 
        this.phone = phone;
        this.isClient = isClient;
        this.isOwner = isOwner;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getNumStudent() {
        return numStudent;
    }

    public String getEmailUPR() {
        return emailUPR;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isClient() {
        return isClient;
    }

    public boolean isOwner() {
        return isOwner;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNumStudent(String numStudent) {
        if (!numStudent.matches("\\d{3}-\\d{2}-\\d{4}")) {
            throw new IllegalArgumentException("El número de estudiante debe tener el formato 000-00-0000.");
        }
        this.numStudent = numStudent;
    }

    public void setEmailUPR(String emailUPR) {
        if (!emailUPR.toLowerCase().endsWith("@upr.edu")) {
            throw new IllegalArgumentException("El email debe ser del recinto (@upr.edu).");
        }
        this.emailUPR = emailUPR;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIsClient(boolean isClient) {
        this.isClient = isClient;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    // Método para actualizar tipo de usuario
    public void updateType(boolean isClient, boolean isOwner) {
        this.isClient = isClient;
        this.isOwner = isOwner;
    }

    // Método toString
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + name + '\'' +
                ", numeroEstudiante='" + numStudent + '\'' +
                ", emailUPR='" + emailUPR + '\'' +
                ", telefono='" + phone + '\'' +
                ", esCliente=" + isClient +
                ", esDueno=" + isOwner +
                '}';
    }
}
