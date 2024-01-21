package com.example.demowebapp;

public class User {
    private String nome;
    private String provincia;
    private String saluto;

    public User(String _nome, String _provincia)
    {
        nome = _nome;
        provincia = _provincia;
        saluto = "Ciao " + nome + ", com'è il tempo in Lombardia?";
    }

    public String getNome() {
        return nome;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getSaluto() {
        return saluto;
    }
}
