package br.com.alura.literalura.cli;

import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.BookApiService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class MainCli {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final BookApiService bookApiService;
    private final Scanner sc = new Scanner(System.in);
    private final List<String> possibleQueryLanguages;

    public MainCli(
            BookApiService bookApiService,
            LivroRepository livroRepository,
            AutorRepository autorRepository
    ) {
        this.bookApiService = bookApiService;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;

        possibleQueryLanguages = new ArrayList<>();
        possibleQueryLanguages.add("pt");
        possibleQueryLanguages.add("en");
        possibleQueryLanguages.add("es");
        possibleQueryLanguages.add("fr");
    }

    public void start() {
        System.out.println("LITERALURA =======================================");
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("1 - Buscar livro por título");
            System.out.println("2 - Listar livros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos em um determinado ano");
            System.out.println("5 - Listar livros em um determinado idioma");
            System.out.println("6 - Listar livros mais baixados");
            System.out.println("7 - Estatísticas");
            System.out.println("0 - Sair\n");
            System.out.print("Escolha a opção desejada: ");
            opcao = nextInt();

            switch (opcao) {
                case 1:
                    searchByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listAliveAuthorsOnYear();
                    break;
                case 5:
                    listBooksOnLanguage();
                    break;
                case 6:
                    mostDownloaded();
                    break;
                case 7:
                    statistics();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n[!] - OPÇÃO INVÁLIDA\n");
            }
        }
    }

    private void searchByTitle() {
        System.out.println("\nBUSCA POR TÍTULO =================================");
        System.out.print("Digite o título do livro: ");
        var titulo = sc.nextLine();

        System.out.println("\nPesquisando...\n");
        var bookData = bookApiService.search(titulo).stream().findFirst();

        if (bookData.isEmpty()) {
            System.out.println("[i] - Nenhum livro encontrado\n");
        } else {
            var livro = Livro.fromBookData(bookData.get());
            autorRepository.save(livro.getAutor());
            livroRepository.save(livro);
            System.out.println(livro + "\n");
        }
    }

    private void listRegisteredBooks() {
        System.out.println("\nLISTAGEM DE LIVROS ===============================");
        var livros = livroRepository.findAll();
        if (livros.isEmpty())
            System.out.println("[i] - Nenhum livro encontrado\n");
        livros.forEach(l -> System.out.println(l + "\n"));
    }

    private void listRegisteredAuthors() {
        System.out.println("\nLISTAGEM DE AUTORES ==============================");
        var autores = autorRepository.findAll();
        autores.forEach(System.out::println);
        if (autores.isEmpty())
            System.out.println("[i] - Nenhum autor encontrado\n");

    }

    private void listAliveAuthorsOnYear() {
        System.out.println("\nBUSCAR AUTORES VIVOS EM DETERMINADO ANO ==========");
        System.out.print("Digite o ano desejado: ");
        var year = nextInt();

        System.out.println("\nPesquisando...\n");
        var autores = autorRepository
                .findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(year, year);

        if (autores.isEmpty())
            System.out.println("[i] - Nenhum autor encontrado\n");
        autores.forEach(System.out::println);
        System.out.println(" ");
    }

    private void listBooksOnLanguage() {
        System.out.println("\nLISTAR LIVROS DE UM IDIOMA ESPECÍFICO ============");

        boolean validLanguage = false;
        String idioma = "";
        while (!validLanguage) {
            System.out.println("pt => Português");
            System.out.println("en => Inglês");
            System.out.println("es => Espanhol");
            System.out.println("fr => Francês");
            System.out.print("Escolha o idioma: ");

            var inputUsuario = sc.nextLine();
            validLanguage = possibleQueryLanguages.stream()
                    .anyMatch(l -> l.equalsIgnoreCase(inputUsuario));

            if (!validLanguage) System.out.println("\n[!] - Idioma inválido\n");
            idioma = inputUsuario;
        }
        var livros = livroRepository.findByIdioma(idioma);
        var quantidade = livroRepository.countByIdioma(idioma);

        System.out.println("\nLISTANDO " + quantidade + " LIVRO"
                           + (quantidade != 1 ? "S" : ""));
        livros.forEach(l -> System.out.println(l + "\n"));
        if (livros.isEmpty())
            System.out.println("[i] - Nenhum livro encontrado\n");
    }

    private void mostDownloaded() {
        System.out.println("\nCarregando...");
        var maisBaixados = livroRepository.findTop10ByOrderByDownloadsDesc();

        System.out.println("\nTOP 10 LIVROS MAIS BAIXADOS ======================");
        maisBaixados.forEach(l -> System.out.println(l + "\n"));
        if (maisBaixados.isEmpty())
            System.out.println("[i] - Nenhum livro encontrado\n");
    }

    private void statistics() {
        System.out.println("\nCarregando...");
        var maisBaixados = livroRepository.findTop10ByOrderByDownloadsDesc();
        var livros = livroRepository.findAll();
        var estatisticas = livros.stream()
                .collect(Collectors.summarizingDouble(Livro::getDownloads));

        System.out.println("\nESTATÍSTICAS =====================================");
        System.out.println("Total de livros salvos no banco de dados: " + livros.size());

        if (maisBaixados.isEmpty()) System.out.println("Livro mais baixado: -");
        else {
            System.out.println(
                    "Livro mais baixado: "
                    + maisBaixados.get(0).getTitulo() + " - "
                    + maisBaixados.get(0).getAutor()
            );
        }

        System.out.println("Número mínimo de downloads: " + (int) estatisticas.getMin());
        System.out.println("Número médio de downloads: " + estatisticas.getAverage());
        System.out.println("Número máximo de downloads: " + (int) estatisticas.getMax());
        System.out.println(" ");
    }

    private int nextInt() {
        int input;
        try {
            input = sc.nextInt();
        } catch (Exception ignore) {
            sc.nextLine();
            input = -1;
        }
        return input;
    }
}
