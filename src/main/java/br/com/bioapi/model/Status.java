package br.com.bioapi.model;

public enum Status {
	
    SERVICO_INICIO("em_servico_inicio"),
    SERVICO_TERMINO("em_servico_termino"),
	PAUSA("em_pausa"),
	AUSENTE("ausente");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    // Getter
    public String getStatus() {
        return status;
    }
}