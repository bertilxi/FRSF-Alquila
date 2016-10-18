package app.ui.controladores.Resultado;

import app.ui.controladores.Resultado.ResultadoControlador.ErrorResultadoLogin;


public class ResultadoControlador extends Resultado<ErrorResultadoLogin> {

    public enum ErrorResultadoLogin {
        Datos_Incorrectos
    }

    public ResultadoControlador(ErrorResultadoLogin... errores) {
        super(errores);
    }
}