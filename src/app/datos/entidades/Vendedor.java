package app.datos.entidades;

import javax.persistence.*;
import java.util.ArrayList;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(ArrayList<Venta> ventas) {
        this.ventas = ventas;
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
