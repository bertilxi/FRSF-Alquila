package app.datos.servicios.implementacion;

import org.hibernate.Query;
import org.hibernate.Session;

public interface FiltroHibernate {

	String getConsulta();

	String getSelect(String nombreEntidad);

	String getFrom(String nombreEntidad);

	String getWhere(String nombreEntidad);

	String getOrden(String nombreEntidad);

	void setParametros(Query query);

	void updateParametros(Session session);

}
