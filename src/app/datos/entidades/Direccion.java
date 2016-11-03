package app.datos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "direccion")
public class Direccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "numero", length = 30, nullable = false)
	private String numero;

	@Column(name = "piso", length = 30)
	private String piso;

	@Column(name = "departamento", length = 30)
	private String departamento;

	@Column(name = "otros", length = 100)
	private String otros;

	//Relaciones
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcalle", referencedColumnName = "id", foreignKey = @ForeignKey(name = "direccion_idcalle_fk"))
	private Calle calle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idbarrio", referencedColumnName = "id", foreignKey = @ForeignKey(name = "direccion_idbarrio_fk"))
	private Barrio barrio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idlocalidad", referencedColumnName = "id", foreignKey = @ForeignKey(name = "direccion_idlocalidad_fk"))
	private Localidad localidad;

	public Direccion() {
		super();
	}

	public Direccion(String numero, String piso, String departamento, Calle calle, Barrio barrio,
			Localidad localidad) {
		super();
		this.numero = numero;
		this.piso = piso;
		this.departamento = departamento;
		this.calle = calle;
		this.barrio = barrio;
		this.localidad = localidad;
	}

	public Integer getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public Direccion setNumero(String numero) {
		this.numero = numero;
		return this;
	}

	public String getPiso() {
		return piso;
	}

	public Direccion setPiso(String piso) {
		this.piso = piso;
		return this;
	}

	public String getDepartamento() {
		return departamento;
	}

	public Direccion setDepartamento(String departamento) {
		this.departamento = departamento;
		return this;
	}

	public String getOtros() {
		return otros;
	}

	public Direccion setOtros(String otros) {
		this.otros = otros;
		return this;
	}

	public Calle getCalle() {
		return calle;
	}

	public Direccion setCalle(Calle calle) {
		this.calle = calle;
		return this;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public Direccion setBarrio(Barrio barrio) {
		this.barrio = barrio;
		return this;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public Direccion setLocalidad(Localidad localidad) {
		this.localidad = localidad;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barrio == null) ? 0 : barrio.hashCode());
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((localidad == null) ? 0 : localidad.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((otros == null) ? 0 : otros.hashCode());
		result = prime * result + ((piso == null) ? 0 : piso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		Direccion other = (Direccion) obj;
		if(id == null){
			if(other.id != null){
				return false;
			}
		}
		else if(!id.equals(other.id)){
			return false;
		}
		else{
			return true;
		}
		if(barrio == null){
			if(other.barrio != null){
				return false;
			}
		}
		else if(!barrio.equals(other.barrio)){
			return false;
		}
		if(calle == null){
			if(other.calle != null){
				return false;
			}
		}
		else if(!calle.equals(other.calle)){
			return false;
		}
		if(departamento == null){
			if(other.departamento != null){
				return false;
			}
		}
		else if(!departamento.equals(other.departamento)){
			return false;
		}
		if(otros == null){
			if(other.otros != null){
				return false;
			}
		}
		else if(!otros.equals(other.otros)){
			return false;
		}
		if(localidad == null){
			if(other.localidad != null){
				return false;
			}
		}
		else if(!localidad.equals(other.localidad)){
			return false;
		}
		if(numero == null){
			if(other.numero != null){
				return false;
			}
		}
		else if(!numero.equals(other.numero)){
			return false;
		}
		if(piso == null){
			if(other.piso != null){
				return false;
			}
		}
		else if(!piso.equals(other.piso)){
			return false;
		}
		return true;
	}

}
