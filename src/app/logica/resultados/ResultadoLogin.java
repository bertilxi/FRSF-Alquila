package app.logica.resultados;

import app.logica.resultados.ResultadoLogin.ErrorResultadoLogin;

;

public class ResultadoLogin extends Resultado<ErrorResultadoLogin> {

    public ResultadoLogin(ErrorResultadoLogin... errores) {
        super(errores);
    }

    public enum ErrorResultadoLogin {
        Datos_Incorrectos
    }
}
