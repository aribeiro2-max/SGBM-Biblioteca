import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static final Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> registarLivro();
                case 2 -> biblioteca.listarLivros();
                case 3 -> pesquisarLivro();
                case 4 -> registarUtilizador();
                case 5 -> efectuarEmprestimo();
                case 6 -> efectuarDevolucao();
                case 7 -> biblioteca.mostrarEstatisticas();
                case 8 -> biblioteca.listarEmprestimos();
                case 0 -> System.out.println("Programa encerrado. Obrigado!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    static void mostrarMenu() {
        System.out.println("\n==============================================");
        System.out.println(" SISTEMA DE GESTÃO DA BIBLIOTECA MUNICIPAL");
        System.out.println("==============================================");
        System.out.println("1. Registar livro");
        System.out.println("2. Listar catálogo");
        System.out.println("3. Pesquisar livro");
        System.out.println("4. Registar utilizador");
        System.out.println("5. Efectuar empréstimo");
        System.out.println("6. Registar devolução");
        System.out.println("7. Ver estatísticas");
        System.out.println("8. Listar histórico de empréstimos");
        System.out.println("0. Sair");
    }

    static void registarLivro() {
        String id = lerTexto("ID do livro: ");
        String titulo = lerTexto("Título: ");
        String autor = lerTexto("Autor: ");
        int ano = lerInteiro("Ano de publicação: ");
        int quantidade = lerInteiro("Quantidade: ");

        if (ano < 0 || quantidade < 0) {
            System.out.println("Ano ou quantidade inválidos.");
            return;
        }

        if (biblioteca.registarLivro(new Livro(id, titulo, autor, ano, quantidade))) {
            System.out.println("Livro registado com sucesso.");
        } else {
            System.out.println("Erro: ID do livro já existe ou limite atingido.");
        }
    }

    static void pesquisarLivro() {
        String termo = lerTexto("Digite o título ou autor: ");
        biblioteca.pesquisarLivro(termo);
    }

    static void registarUtilizador() {
        String id = lerTexto("ID do utilizador: ");
        String nome = lerTexto("Nome completo: ");
        String contacto = lerTexto("Contacto: ");

        if (biblioteca.registarUtilizador(new Utilizador(id, nome, contacto))) {
            System.out.println("Utilizador registado com sucesso.");
        } else {
            System.out.println("Erro: ID já existe ou limite atingido.");
        }
    }

    static void efectuarEmprestimo() {
        String idLivro = lerTexto("ID do livro: ");
        String idUtilizador = lerTexto("ID do utilizador: ");

        Emprestimo emprestimo = biblioteca.efectuarEmprestimo(idLivro, idUtilizador);
        if (emprestimo != null) {
            System.out.println("Empréstimo efectuado com sucesso.");
            System.out.println(emprestimo);
        } else {
            System.out.println("Não foi possível efectuar o empréstimo.");
            System.out.println("Verifique se o livro/utilizador existem e se há exemplares disponíveis.");
        }
    }

    static void efectuarDevolucao() {
        String idEmprestimo = lerTexto("ID do empréstimo: ");

        if (biblioteca.registarDevolucao(idEmprestimo)) {
            System.out.println("Devolução registada com sucesso.");
        } else {
            System.out.println("Empréstimo não encontrado ou já devolvido.");
        }
    }

    static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduza um número inteiro válido.");
            }
        }
    }
}

class Livro {
    String id, titulo, autor;
    int ano, quantidadeDisponivel, totalEmprestimos;

    Livro(String id, String titulo, String autor, int ano, int quantidadeDisponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.totalEmprestimos = 0;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Título: %s | Autor: %s | Ano: %d | Disponíveis: %d | Empréstimos: %d",
                id, titulo, autor, ano, quantidadeDisponivel, totalEmprestimos);
    }
}

class Utilizador {
    String id, nome, contacto;

    Utilizador(String id, String nome, String contacto) {
        this.id = id;
        this.nome = nome;
        this.contacto = contacto;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Nome: %s | Contacto: %s", id, nome, contacto);
    }
}

class Emprestimo {
    String id, idLivro, idUtilizador, data;
    boolean devolvido;

    Emprestimo(String id, String idLivro, String idUtilizador) {
        this.id = id;
        this.idLivro = idLivro;
        this.idUtilizador = idUtilizador;
        this.data = LocalDate.now().toString();
        this.devolvido = false;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Livro: %s | Utilizador: %s | Data: %s | Estado: %s",
                id, idLivro, idUtilizador, data, devolvido ? "Devolvido" : "Emprestado");
    }
}

class Biblioteca {
    static final int LIMITE = 100;
    Livro[] livros = new Livro[LIMITE];
    Utilizador[] utilizadores = new Utilizador[LIMITE];
    Emprestimo[] emprestimos = new Emprestimo[LIMITE];

    int totalLivros = 0, totalUtilizadores = 0, totalEmprestimos = 0, sequenciaEmprestimo = 1;

    boolean registarLivro(Livro livro) {
        if (totalLivros >= LIMITE || procurarLivro(livro.id) != null) return false;
        livros[totalLivros++] = livro;
        return true;
    }

    boolean registarUtilizador(Utilizador utilizador) {
        if (totalUtilizadores >= LIMITE || procurarUtilizador(utilizador.id) != null) return false;
        utilizadores[totalUtilizadores++] = utilizador;
        return true;
    }

    Livro procurarLivro(String id) {
        for (int i = 0; i < totalLivros; i++)
            if (livros[i].id.equalsIgnoreCase(id)) return livros[i];
        return null;
    }

    Utilizador procurarUtilizador(String id) {
        for (int i = 0; i < totalUtilizadores; i++)
            if (utilizadores[i].id.equalsIgnoreCase(id)) return utilizadores[i];
        return null;
    }

    Emprestimo procurarEmprestimo(String id) {
        for (int i = 0; i < totalEmprestimos; i++)
            if (emprestimos[i].id.equalsIgnoreCase(id)) return emprestimos[i];
        return null;
    }

    void listarLivros() {
        if (totalLivros == 0) {
            System.out.println("Nenhum livro registado.");
            return;
        }
        System.out.println("\n========== CATÁLOGO ==========");
        for (int i = 0; i < totalLivros; i++) System.out.println(livros[i]);
    }

    void pesquisarLivro(String termo) {
        boolean encontrado = false;
        for (int i = 0; i < totalLivros; i++) {
            Livro l = livros[i];
            if (l.titulo.toLowerCase().contains(termo.toLowerCase())
                    || l.autor.toLowerCase().contains(termo.toLowerCase())) {
                System.out.println(l);
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("Nenhum livro encontrado.");
    }

    Emprestimo efectuarEmprestimo(String idLivro, String idUtilizador) {
        Livro livro = procurarLivro(idLivro);
        Utilizador utilizador = procurarUtilizador(idUtilizador);

        if (livro == null || utilizador == null || livro.quantidadeDisponivel <= 0
                || totalEmprestimos >= LIMITE) return null;

        livro.quantidadeDisponivel--;
        livro.totalEmprestimos++;

        Emprestimo e = new Emprestimo(String.format("E%03d", sequenciaEmprestimo++), livro.id, utilizador.id);
        emprestimos[totalEmprestimos++] = e;
        return e;
    }

    boolean registarDevolucao(String idEmprestimo) {
        Emprestimo e = procurarEmprestimo(idEmprestimo);
        if (e == null || e.devolvido) return false;

        Livro livro = procurarLivro(e.idLivro);
        if (livro == null) return false;

        livro.quantidadeDisponivel++;
        e.devolvido = true;
        return true;
    }

    void listarEmprestimos() {
        if (totalEmprestimos == 0) {
            System.out.println("Nenhum empréstimo registado.");
            return;
        }
        System.out.println("\n====== HISTÓRICO DE EMPRÉSTIMOS ======");
        for (int i = 0; i < totalEmprestimos; i++) System.out.println(emprestimos[i]);
    }

    void mostrarEstatisticas() {
        int livrosDisponiveis = 0;
        Livro maisEmprestado = null;

        for (int i = 0; i < totalLivros; i++) {
            livrosDisponiveis += livros[i].quantidadeDisponivel;
            if (maisEmprestado == null || livros[i].totalEmprestimos > maisEmprestado.totalEmprestimos)
                maisEmprestado = livros[i];
        }

        System.out.println("\n========== ESTATÍSTICAS ==========");
        System.out.println("Total de títulos registados: " + totalLivros);
        System.out.println("Total de utilizadores: " + totalUtilizadores);
        System.out.println("Total de empréstimos: " + totalEmprestimos);
        System.out.println("Total de exemplares disponíveis: " + livrosDisponiveis);

        if (maisEmprestado != null && maisEmprestado.totalEmprestimos > 0) {
            System.out.println("Livro mais emprestado: " + maisEmprestado.titulo);
            System.out.println("Número de empréstimos: " + maisEmprestado.totalEmprestimos);
        } else {
            System.out.println("Ainda não existem empréstimos para apresentar o livro mais requisitado.");
        }
    }
}
