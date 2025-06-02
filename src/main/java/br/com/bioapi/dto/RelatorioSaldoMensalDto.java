package br.com.bioapi.dto;

public class RelatorioSaldoMensalDto {
    private int quantidadeSaldoNegativo;
    private int quantidadeSaldoZerado;
    private int quantidadeSaldoPositivo;
    private int totalFuncionarios;
    
    public RelatorioSaldoMensalDto() {
    }
    
    public RelatorioSaldoMensalDto(int quantidadeSaldoNegativo, int quantidadeSaldoZerado, 
            int quantidadeSaldoPositivo, int totalFuncionarios) {
        this.quantidadeSaldoNegativo = quantidadeSaldoNegativo;
        this.quantidadeSaldoZerado = quantidadeSaldoZerado;
        this.quantidadeSaldoPositivo = quantidadeSaldoPositivo;
        this.totalFuncionarios = totalFuncionarios;
    }

    public int getQuantidadeSaldoNegativo() {
        return quantidadeSaldoNegativo;
    }

    public void setQuantidadeSaldoNegativo(int quantidadeSaldoNegativo) {
        this.quantidadeSaldoNegativo = quantidadeSaldoNegativo;
    }

    public int getQuantidadeSaldoZerado() {
        return quantidadeSaldoZerado;
    }

    public void setQuantidadeSaldoZerado(int quantidadeSaldoZerado) {
        this.quantidadeSaldoZerado = quantidadeSaldoZerado;
    }

    public int getQuantidadeSaldoPositivo() {
        return quantidadeSaldoPositivo;
    }

    public void setQuantidadeSaldoPositivo(int quantidadeSaldoPositivo) {
        this.quantidadeSaldoPositivo = quantidadeSaldoPositivo;
    }

    public int getTotalFuncionarios() {
        return totalFuncionarios;
    }

    public void setTotalFuncionarios(int totalFuncionarios) {
        this.totalFuncionarios = totalFuncionarios;
    }

    @Override
    public String toString() {
        return "RelatorioSaldoMensalDto [quantidadeSaldoNegativo=" + quantidadeSaldoNegativo 
                + ", quantidadeSaldoZerado=" + quantidadeSaldoZerado 
                + ", quantidadeSaldoPositivo=" + quantidadeSaldoPositivo 
                + ", totalFuncionarios=" + totalFuncionarios + "]";
    }
} 