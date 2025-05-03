package br.com.bioapi.model;

public enum Status {
	
    SERVICO("em_servico"),
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