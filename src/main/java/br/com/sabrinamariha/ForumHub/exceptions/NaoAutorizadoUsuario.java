package br.com.sabrinamariha.ForumHub.exceptions;

public class NaoAutorizadoUsuario extends RuntimeException {
    public NaoAutorizadoUsuario(String s) {
        super(s);
    }
}
