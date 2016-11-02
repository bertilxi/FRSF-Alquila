package app.ui;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.logica.CoordinadorJavaFX;
import app.ui.controladores.WindowTitleController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	private CoordinadorJavaFX coordinador;
	private ApplicationContext appContext;
	private Stage primaryStage;

	public static void main(String[] args) {
		//Ocultar logs
		java.util.Enumeration<String> loggers = java.util.logging.LogManager.getLogManager().getLoggerNames();
		while(loggers.hasMoreElements()){
			String log = loggers.nextElement();
			java.util.logging.Logger.getLogger(log).setLevel(java.util.logging.Level.WARNING);
		}
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		iniciarHibernate();
	}

	public FXMLLoader createFXMLLoader(URL location) {
		return new FXMLLoader(location, null, new JavaFXBuilderFactory(), null, Charset.forName(FXMLLoader.DEFAULT_CHARSET_NAME));
	}

	private void iniciarHibernate() {
		//Crear tarea para iniciar hibernate y el coordinador de la aplicacion
		Task<Boolean> task = new Task<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
				coordinador = appContext.getBean(CoordinadorJavaFX.class);
				return true;
			}
		};
		task.setOnSucceeded(
				(event) -> {
					Platform.runLater(() -> {
						URL location = getClass().getResource("/app/ui/vistas/altaVendedor.fxml");
						FXMLLoader loader = createFXMLLoader(location);

						Parent root = null;
						try{
							root = loader.load(location.openStream());
						} catch(IOException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
							Platform.exit();
						}
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("/app/ui/estilos/style.css").toExternalForm());

						WindowTitleController controller = loader.getController();

						// para emular el estilo de windows 10 se usa la ventana sin decorar
						primaryStage.initStyle(StageStyle.UNDECORATED);
						primaryStage.setScene(scene);
						controller.setCoordinador(coordinador);
						controller.controlerPassing(primaryStage);

						primaryStage.show();
					});
				});
		//Si falla, informa al usuario del error y cierra la aplicacion
		task.setOnFailed(
				(event) -> {
					try{
						throw task.getException();
					} catch(Throwable e){
						e.printStackTrace();
						if(appContext != null){
							SessionFactory sessionFact = (SessionFactory) appContext.getBean("sessionFactory");
							sessionFact.close();
						}
						System.exit(1);
					}
				});

		//Iniciar tarea
		Thread hiloHibernate = new Thread(task);
		hiloHibernate.setDaemon(false);
		hiloHibernate.start();
	}
}
