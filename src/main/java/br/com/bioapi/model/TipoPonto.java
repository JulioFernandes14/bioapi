package br.com.bioapi.model;

public enum TipoPonto {
	
    ENTRADA("entrada"),
    SAIDA("saida"),
    PAUSA("pausa"),
    RETORNO("retorno");
	
	private final String tipoPonto;

	private TipoPonto(String tipoPonto) {
		this.tipoPonto = tipoPonto;
	}
	
	public String getTipoPonto() {
		return tipoPonto;
	}
}