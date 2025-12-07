package reservas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entidad JPA que representa una Experiencia Turística en la base de datos.
 * Tabla asociada: "experiences"
 */
@Entity
@Table(name = "experiences")
public class Experience {

    /** Identificador único autogenerado */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    /** Descripción detallada de la experiencia, límite aumentado a 500 caracteres */
    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precio;

    private String ubicacion;

    /** * Relación Muchos-a-Uno con la entidad User.
     * Indica qué emprendedor creó esta experiencia.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User emprendedor;

    /** Constructor vacío requerido por JPA */
    public Experience() {
    }

    public Experience(String nombre, String descripcion, BigDecimal precio, String ubicacion, User emprendedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ubicacion = ubicacion;
        this.emprendedor = emprendedor;
    }

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public User getEmprendedor() { return emprendedor; }
    public void setEmprendedor(User emprendedor) { this.emprendedor = emprendedor; }    
}