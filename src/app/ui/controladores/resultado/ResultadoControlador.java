package app.ui.controladores.resultado;

import app.ui.controladores.resultado.ResultadoControlador.ErrorResultadoLogin;


public class ResultadoControlador extends Resultado<ErrorResultadoLogin> {

    public ResultadoControlador(ErrorResultadoLogin... errores) {
        super(errores);
    }

    public enum ErrorResultadoLogin {
        Datos_Incorrectos
    }
}