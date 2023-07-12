package com.br.ibm.projetofinal.handler;

import org.springframework.http.HttpStatus;

public class ExceptionDTO {

    private HttpStatus statusCode;
    private String mensagem;

    public ExceptionDTO(HttpStatus statusCode, String mensagem) {
        this.statusCode = statusCode;
        this.mensagem = mensagem;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }



}
