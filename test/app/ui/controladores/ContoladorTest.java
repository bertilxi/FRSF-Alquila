package app.ui.controladores;

import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class ContoladorTest implements TestRule {

	private static Thread javaFXThread = new Thread("Hilo de inicio de JavaFX") {
		@Override
		public void run() {
			Platform.setImplicitExit(false);
			Application.launch(ClaseAplicacion.class);
		}
	};

	public static class ClaseAplicacion extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			s.release();
		}
	}

	private static Semaphore s = new Semaphore(0);

	//Inicia el hilo de JavaFX e inyecta la vista en el cotrolador
	public <T> ContoladorTest(String direccionURLVista, T controladorAProbar) {
		try{
			//Iniciar el hilo de JavaFX
			if(!javaFXThread.isAlive()){
				javaFXThread.setDaemon(true);
				javaFXThread.start();
				s.acquire();
			}

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource(direccionURLVista));
			loader.setControllerFactory(claseControlador -> {
				if(claseControlador != null && !claseControlador.isInstance(controladorAProbar)){
					throw new IllegalArgumentException("¡Instancia del controlador inválida, esperada una instancia de la clase '" + claseControlador.getName() + "'!");
				}
				return controladorAProbar;
			});
			loader.load();
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	//Clase para correr un test en el hilo de JavaFX
	@Override
	public Statement apply(final Statement statement, final Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				FutureTask<Throwable> future = new FutureTask<>(() -> {
					try{
						statement.evaluate();
						return null;
					} catch(Throwable t){
						return t;
					}
				});

				Platform.runLater(future);
				Throwable excepcion = future.get();
				if(excepcion != null){
					throw excepcion;
				}
			}
		};
	}

}
