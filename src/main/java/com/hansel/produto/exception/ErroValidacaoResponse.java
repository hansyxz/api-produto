package com.hansel.produto.exception;

import java.util.List;

public class ErroValidacaoResponse {
    private int status;
    private List<ErroCampoDTO> erros;

    public ErroValidacaoResponse(int status, List<ErroCampoDTO> erros) {
        this.status = status;
        this.erros = erros;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ErroCampoDTO> getErros() {
        return erros;
    }

    public void setErros(List<ErroCampoDTO> erros) {
        this.erros = erros;
    }
}
