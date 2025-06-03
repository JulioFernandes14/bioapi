package br.com.bioapi.dto;

public class HorasPorDiaDto {
    private String diaDaSemana;
    private String qtdHoras;

    public HorasPorDiaDto() {
    }

    public HorasPorDiaDto(String diaDaSemana, String qtdHoras) {
        this.diaDaSemana = diaDaSemana;
        this.qtdHoras = qtdHoras;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public String getQtdHoras() {
        return qtdHoras;
    }

    public void setQtdHoras(String qtdHoras) {
        this.qtdHoras = qtdHoras;
    }
} 