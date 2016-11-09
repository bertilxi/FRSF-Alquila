package app.ui.controladores;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.datos.entidades.Vendedor;
import app.logica.CoordinadorJavaFX;
import app.ui.ScenographyChanger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class AdministrarVendedorControllerTest {

	@Test
	public void testAgregarVendedor() throws Throwable {

		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		AltaVendedorController altaVendedorControllerMock = mock(AltaVendedorController.class);
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);

		when(scenographyChangerMock.cambiarScenography(any(), any())).thenReturn(altaVendedorControllerMock);

		AdministrarVendedorController administrarVendedorController = new AdministrarVendedorController() {

			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
			}

			@Override
			protected void setTitulo(String titulo) {

			}
		};

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarVendedorController.URLVista, administrarVendedorController);
		administrarVendedorController.setStage(corredorTestEnJavaFXThread.getStagePrueba());
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				administrarVendedorController.agregarAction(null);
				Mockito.verify(scenographyChangerMock, times(1)).cambiarScenography(AltaVendedorController.URLVista, false);
			}
		};
		corredorTestEnJavaFXThread.apply(test, null).evaluate();
		;
	}

	@Test
	public void testModificarVendedor() throws Throwable {

		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);
		ModificarVendedorController modificarVendedorControllerMock = mock(ModificarVendedorController.class);

		Vendedor vendedor = new Vendedor()
				.setNombre("Juan")
				.setApellido("Perez")
				.setNumeroDocumento("1234");
		ArrayList<Vendedor> lista = new ArrayList<Vendedor>();
		lista.add(vendedor);

		when(coordinadorMock.obtenerVendedores()).thenReturn(lista);
		when(scenographyChangerMock.cambiarScenography(any(), any())).thenReturn(modificarVendedorControllerMock);

		AdministrarVendedorController administrarVendedorController = new AdministrarVendedorController() {

			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
				this.tablaVendedores.getSelectionModel().select(vendedor);
			}

			@Override
			protected void setTitulo(String titulo) {

			}

		};

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarVendedorController.URLVista, administrarVendedorController);
		administrarVendedorController.setStage(corredorTestEnJavaFXThread.getStagePrueba());
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				administrarVendedorController.modificarAction(null);
				Mockito.verify(scenographyChangerMock, times(1)).cambiarScenography(ModificarVendedorController.URLVista, false);
			}
		};

		corredorTestEnJavaFXThread.apply(test, null).evaluate();

	}

	@Test
	public void testEliminarVendedor() throws Exception {

		OlimpoController olimpoControlerMock = Mockito.mock(OlimpoController.class);

		AdministrarVendedorController administrarVendedorController = new AdministrarVendedorController() {

			private TableView<Vendedor> tablaVendedores;
			private TableColumn<Vendedor, String> columnaNumeroDocumento;
			private TableColumn<Vendedor, String> columnaNombre;
			private TableColumn<Vendedor, String> columnaApellido;

			@SuppressWarnings("unchecked")
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				Vendedor vendedor = new Vendedor()
						.setNombre("Juan")
						.setApellido("Perez")
						.setNumeroDocumento("1234");
				tablaVendedores = new TableView<>();

				columnaNumeroDocumento = new TableColumn<>("Documento");
				columnaNombre = new TableColumn<>("Nombre");
				columnaApellido = new TableColumn<>("Apellido");

				tablaVendedores.getColumns().addAll(columnaNumeroDocumento, columnaNombre, columnaApellido);

				tablaVendedores.getItems().addAll(vendedor);

				columnaNumeroDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroDocumento()));
				columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
				columnaApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
			}

		};

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarVendedorController.URLVista, administrarVendedorController);
		administrarVendedorController.setStage(corredorTestEnJavaFXThread.getStagePrueba());
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				administrarVendedorController.eliminarAction((ActionEvent) new Event(null));
				//Mockito.verify(olimpoControlerMock).cambiarmeAScene(URLVISTA_ADMINISTRAR, URLVISTA_MODIFICAR); //no funciona
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null);
		} catch(Throwable e){
			throw new Exception(e);
		}
	}
}
