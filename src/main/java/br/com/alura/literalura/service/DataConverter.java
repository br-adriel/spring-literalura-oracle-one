package br.com.alura.literalura.service;

public interface DataConverter {
    <T> T convert(String json, Class<T> tClass);

}
