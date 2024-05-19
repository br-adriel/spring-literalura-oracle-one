package br.com.alura.literalura.service;

import br.com.alura.literalura.model.BookData;
import br.com.alura.literalura.model.BooksApiResponse;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class BookApiService {
    public List<BookData> searchByAuthor(String authorName) {
        var json = ApiConsumer.getData(
                "https://gutendex.com/books/?search="
                + URLEncoder.encode(authorName.trim().toLowerCase(), StandardCharsets.UTF_8)
        );
        return new DataConverter().convert(json, BooksApiResponse.class).livros();
    }
}
