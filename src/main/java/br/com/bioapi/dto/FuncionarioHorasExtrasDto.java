package br.com.bioapi.dto;

public class FuncionarioHorasExtrasDto {
    private Long funcionarioId;
    private String nomeFuncionario;
    private String horasExtras;

    public FuncionarioHorasExtrasDto() {
    }

    public FuncionarioHorasExtrasDto(Long funcionarioId, String nomeFuncionario, String horasExtras) {
        this.funcionarioId = funcionarioId;
        this.nomeFuncionario = nomeFuncionario;
        this.horasExtras = horasExtras;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(String horasExtras) {
        this.horasExtras = horasExtras;
    }
} 