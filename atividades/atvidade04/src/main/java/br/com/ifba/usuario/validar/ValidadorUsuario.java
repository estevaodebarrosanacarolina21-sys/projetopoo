package br.com.ifba.usuario.validar;

public final class ValidadorUsuario {

    private ValidadorUsuario() {
    }

    public static boolean contemPalavraProibida(String texto) {
        String[] palavrasProibidas = {"admin", "teste", "root", "senha123"};

        if (texto == null) {
            return false;
        }

        String textoNormalizado = texto.toLowerCase();
        for (String palavraProibida : palavrasProibidas) {
            if (textoNormalizado.contains(palavraProibida)) {
                return true;
            }
        }
        return false;
    }
}