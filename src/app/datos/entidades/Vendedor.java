package app.datos.entidades;

import app.datos.clases.Estado;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

@NamedQuery(name="obtenerVendedor", query="SELECT v FROM Vendedor v WHERE numeroDocumento = :documento AND tipoDocumento.tipo = :tipoDocumento")
@Entity
@Table(name = "vendedor", uniqueConstraints = @UniqueConstraint(name = "vendedor_numerodocumento_idtipo_uk", columnNames = { "numerodocumento", "idtipo" }))
public class Vendedor {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //ID

    @Column(name = "nombre", length = 30)
    private String nombre;

    @Column(name = "apellido", length = 30)
    private String apellido;

    @Column(name = "numerodocumento", length = 30)
    private String numeroDocumento;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipo", referencedColumnName = "id", foreignKey = @ForeignKey(name = "vendedor_idtipo_fk"))
    private TipoDocumento tipoDocumento;

    //@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vendedor")
    @Transient
    private ArrayList<Venta> ventas;

    // baja
    private Estado estado;

    public Vendedor() {
		super();
		this.ventas = new ArrayList<Venta>();
	}

	public Vendedor(Integer id, String nombre, String apellido, String numeroDocumento, String password, String salt, TipoDocumento tipoDocumento, ArrayList<Venta> ventas) {
        this();
		this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.password = password;
        this.salt = salt;
        this.tipoDocumento = tipoDocumento;
        this.ventas = ventas;
    }

    public Integer getId() {
        return id;
    }

    public Vendedor setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Vendedor setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public Vendedor setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public Vendedor setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Vendedor setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public Vendedor setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public Vendedor setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public Vendedor setVentas(ArrayList<Venta> ventas) {
        this.ventas = ventas;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendedor)) return false;

        Vendedor vendedor = (Vendedor) o;

        if (!getNumeroDocumento().equals(vendedor.getNumeroDocumento())) return false;
        if (!getTipoDocumento().equals(vendedor.getTipoDocumento())) return false;
        return getVentas() != null ? getVentas().equals(vendedor.getVentas()) : vendedor.getVentas() == null;

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getNombre().hashCode();
        result = 31 * result + getApellido().hashCode();
        result = 31 * result + getNumeroDocumento().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getSalt().hashCode();
        result = 31 * result + getTipoDocumento().hashCode();
        result = 31 * result + (getVentas() != null ? getVentas().hashCode() : 0);
        return result;
    }
}
