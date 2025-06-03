package br.com.bioapi.dto;

public class PontosPorSemanaDto {
    private String semana;
    private Long quantidadePontos;

    public PontosPorSemanaDto() {
    }

    public PontosPorSemanaDto(String semana, Long quantidadePontos) {
        this.semana = semana;
        this.quantidadePontos = quantidadePontos;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public Long getQuantidadePontos() {
        return quantidadePontos;
    }

    public void setQuantidadePontos(Long quantidadePontos) {
        this.quantidadePontos = quantidadePontos;
    }
} 