package app.datos.clases;

public enum TipoInmuebleStr {
    LOCAL //local-oficina
    , CASA //casa
    , DEPARTAMENTO //departamento
    , TERRENO //terreno
    , QUINTA //quinta,
    , GALPON; //galpón

    @Override
    public String toString() {
        switch (this) {
            case LOCAL:
                return "L";
            case CASA:
                return "C";
            case DEPARTAMENTO:
                return "D";
            case TERRENO:
                return "T";
            case QUINTA:
                return "Q";
            case GALPON:
                return "G";
        }
        return null;
    }
}
