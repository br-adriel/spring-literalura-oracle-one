package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        Number id,
        @JsonAlias("title") String titulo,
        @JsonAlias("subjects") List<String> assuntos,
        @JsonAlias("authors") List<PersonData> autores,
        @JsonAlias("translators") List<PersonData> tradutores,
        @JsonAlias("languages") List<String> idiomas
) {
}
