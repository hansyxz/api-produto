package com.hansel.produto.exception;

import java.util.List;
import java.util.Map;

public class ErroValidacaoResponse {
    private int status;
    private Map<String, String> erros;

    public ErroValidacaoResponse(int status, Map<String, String> erros) {
        this.status = status;
        this.erros = erros;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, String> getErros() {
        return erros;
    }
}
