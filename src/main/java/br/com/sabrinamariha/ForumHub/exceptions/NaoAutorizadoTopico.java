package br.com.sabrinamariha.ForumHub.exceptions;

public class NaoAutorizadoTopico extends RuntimeException{
    public NaoAutorizadoTopico(String s) {
        super(s);
    }
}
