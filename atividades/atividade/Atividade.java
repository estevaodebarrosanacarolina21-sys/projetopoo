package atividade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Atividade {
    public enum Perfil {
        CLIENTE,
        LOJISTA,
        PROFISSIONAL_AUTONOMO,
        PARCEIRO_INSTITUCIONAL,
        ADMINISTRADOR
    }

    public record Usuario(String nome, Perfil perfil) {
    }

    private final List<Usuario> usuarios = new ArrayList<>();

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }

    public static void main(String[] args) {
        Atividade atividade = new Atividade();

        atividade.cadastrarUsuario(new Usuario("Ana", Perfil.CLIENTE));
        atividade.cadastrarUsuario(new Usuario("Bruno", Perfil.LOJISTA));
        atividade.cadastrarUsuario(new Usuario("Carla", Perfil.PROFISSIONAL_AUTONOMO));
        atividade.cadastrarUsuario(new Usuario("Diego", Perfil.PARCEIRO_INSTITUCIONAL));
        atividade.cadastrarUsuario(new Usuario("Eva", Perfil.ADMINISTRADOR));

        System.out.println("Pacote atividade criado com sucesso!");
        System.out.println("Usuários cadastrados:");

        for (Usuario usuario : atividade.listarUsuarios()) {
            System.out.printf("- %s -> %s%n", usuario.nome(), usuario.perfil());
        }
    }
}
