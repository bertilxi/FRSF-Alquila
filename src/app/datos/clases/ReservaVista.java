package app.datos.clases;

import java.util.Date;

import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;

public class ReservaVista {

	private Cliente cliente;
	private Inmueble inmuble;
	private Double importe;
	private Date fechaInicio;
	private Date fechaFin;

	public ReservaVista(Cliente cliente, Inmueble inmuble, Double importe, Date fechaInicio, Date fechaFin) {
		super();
		this.cliente = cliente;
		this.inmuble = inmuble;
		this.importe = importe;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Inmueble getInmuble() {
		return inmuble;
	}

	public Double getImporte() {
		return importe;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}
}
