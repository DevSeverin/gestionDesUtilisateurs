package com.authentication.connexion.validator;

import com.authentication.connexion.model.Teste;

public class Main {

    public static void main(String[] Args) {
        Teste teste = new Teste();
        teste.setTeste("bonjour");
        System.out.println(teste.getTeste());
    }

}
