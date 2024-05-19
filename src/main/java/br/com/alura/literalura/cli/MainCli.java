package br.com.alura.literalura.cli;

import br.com.alura.literalura.model.BookData;
import br.com.alura.literalura.model.PersonData;
import br.com.alura.literalura.service.BookApiService;

import java.util.Scanner;

public class MainCli {
    private final BookApiService bookApiService;
    private final Scanner sc = new Scanner(System.in);

    public MainCli(BookApiService bookApiService) {
        this.bookApiService = bookApiService;
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
        var book = bookApiService.search(titulo).stream().findFirst();

        if (book.isEmpty())
            System.out.println("[i] - Nenhum livro encontrado\n");
        else showBookListingDetails(book.get());
    }

    private void listRegisteredBooks() {
        System.out.println("\nLISTAGEM DE LIVROS ===============================");
        var books = bookApiService.search("");
        books.forEach(this::showBookListingDetails);
    }

    private void listRegisteredAuthors() {
    }

    private void listAliveAuthorsOnYear() {
    }

    private void listBooksOnLanguage() {
    }

    private void showBookListingDetails(BookData livro) {
        System.out.println("Titulo: " + livro.titulo());

        System.out.print("Autor(a): ");
        livro.autores().stream().findFirst().ifPresentOrElse(
                a -> System.out.println(a.nome()),
                () -> System.out.println("-")
        );

        System.out.print("Idioma: ");
        livro.idiomas().stream().findFirst().ifPresentOrElse(
                System.out::println,
                () -> System.out.println("-")
        );

        System.out.println("Número de downloads: " + livro.downloads() + "\n");
    }

    private void showAuthorDetails(PersonData autor) {
        System.out.print(autor.nome());
        if (autor.anoNascimento() != null && autor.anoMorte() != null){
            System.out.println(" (" + autor.anoNascimento() + "-" + autor.anoMorte() + ")");
        } else if (autor.anoNascimento() != null) {
            System.out.println(" (" + autor.anoNascimento() + "-)");
        } else if (autor.anoMorte() != null) {
            System.out.println(" (-" + autor.anoMorte() + ")");
        } else {
            System.out.print("\n");
        }
    }

    private int nextInt() {
        var input = sc.nextInt();
        sc.nextLine();
        return input;
    }
}
