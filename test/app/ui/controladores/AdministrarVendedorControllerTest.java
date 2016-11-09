package app.ui.controladores;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.datos.entidades.Vendedor;
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
	public void testAgregarVendedor() throws Exception {

		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		AltaVendedorController altaVendedorControllerMock = mock(AltaVendedorController.class);

		when(scenographyChangerMock.cambiarScenography(any(), any())).thenReturn(altaVendedorControllerMock);

		AdministrarVendedorController administrarVendedorController = new AdministrarVendedorController() {

			@Override
			public void initialize(URL location, ResourceBundle resources) {
				inicializar(location, resources);
			}

			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.parentScenographyChanger = scenographyChangerMock;
			}
		};

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarVendedorController.URLVista, administrarVendedorController);
		administrarVendedorController.setStage(corredorTestEnJavaFXThread.getStagePrueba());
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				administrarVendedorController.agregarAction((ActionEvent) new Event(null));
				Mockito.verify(scenographyChangerMock, times(1)).cambiarScenography(ModificarVendedorController.URLVista, false);
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null);
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	@Test
	public void testModificarVendedor() throws Exception {

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
				administrarVendedorController.modificarAction((ActionEvent) new Event(null));
				//Mockito.verify(olimpoControlerMock).cambiarmeAScene(URLVISTA_ADMINISTRAR, URLVISTA_MODIFICAR);
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null);
		} catch(Throwable e){
			throw new Exception(e);
		}
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
