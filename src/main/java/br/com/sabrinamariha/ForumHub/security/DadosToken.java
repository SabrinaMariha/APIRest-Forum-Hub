package br.com.sabrinamariha.ForumHub.security;

public record DadosToken(String token) {
    public DadosToken(String tokenJWT, String nome) {
        this(tokenJWT);
    }
}
