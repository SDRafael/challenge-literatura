package br.com.alura.literatura.models;

import java.util.List;

public record DataLivro(String title,
                        List<DataAutor> authors,
                        Integer download_count) {
}
