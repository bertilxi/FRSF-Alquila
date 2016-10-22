package app.ui.controladores;

import java.net.URL;
import java.nio.charset.Charset;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewLauncher extends Application {

	private String path;

	public ViewLauncher(String path) {
		super();
		this.path = path;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL location = getClass().getResource(path);
		FXMLLoader loader = createFXMLLoader(location);
		Parent root = loader.load(location.openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/app/ui/estilos/style.css").toExternalForm());

		// para emular el estilo de windows 10 se usa la ventana sin decorar
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);

		WindowTitleController controller = loader.getController();
		controller.controlerPassing(primaryStage);

		primaryStage.show();
	}

	public FXMLLoader createFXMLLoader(URL location) {
		return new FXMLLoader(location, null, new JavaFXBuilderFactory(), null, Charset.forName(FXMLLoader.DEFAULT_CHARSET_NAME));
	}

	public void main(String[] args) {
		launch(args);
	}

	public static void launchView(String path) throws InterruptedException {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel(); // Initializes the JavaFx Platform
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try{
							new ViewLauncher(path).start(new Stage()); // Create and
						} catch(Exception e){
							e.printStackTrace();
						}

					}
				});
			}
		});
		thread.start();// Initialize the thread
		Thread.sleep(1000000); // Time to use the app, with out this, the thread

	}
}
