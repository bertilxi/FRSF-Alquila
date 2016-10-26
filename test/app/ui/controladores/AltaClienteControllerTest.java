package app.ui.controladores;

import app.ui.componentes.ViewLauncher;
import org.junit.Test;

public class AltaClienteControllerTest {

    @Test
    public void testUI() throws Exception {
        ViewLauncher.launchView("/app/ui/vistas/altaCliente.fxml");
    }

}