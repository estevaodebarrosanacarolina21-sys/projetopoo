package br.com.ifba.usuario.validar;

// Fornece as regras de validacao dos dados de usuario.
public final class ValidadorUsuario {

    // Impede a criacao de objetos desta classe utilitaria.
    private ValidadorUsuario() {
    }

    // Verifica se o texto contem alguma palavra proibida.
    public static boolean contemPalavraProibida(String texto) {
        // Mantem a lista de palavras proibidas local ao metodo.
        String[] palavrasProibidas = {"admin", "teste", "root", "senha123"};

        // Texto nulo nao possui palavra proibida.
        if (texto == null) {
            return false;
        }

        // Ignora diferencas entre letras maiusculas e minusculas.
        String textoNormalizado = texto.toLowerCase();
        // Retorna imediatamente ao encontrar uma palavra proibida.
        for (String palavraProibida : palavrasProibidas) {
            if (textoNormalizado.contains(palavraProibida)) {
                return true;
            }
        }
        // Nenhuma palavra proibida foi encontrada.
        return false;
    }
}