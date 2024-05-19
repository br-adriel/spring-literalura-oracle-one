package br.com.alura.literalura.cli;

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
    }

    private void listRegisteredBooks() {
    }

    private void listRegisteredAuthors() {
    }

    private void listAliveAuthorsOnYear() {
    }

    private void listBooksOnLanguage() {
    }

    private int nextInt() {
        var input = sc.nextInt();
        sc.nextLine();
        return input;
    }
}
