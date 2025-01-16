package br.com.alura.literatura.menu;

import br.com.alura.literatura.models.Autor;
import br.com.alura.literatura.models.DataGutendex;
import br.com.alura.literatura.models.Livro;
import br.com.alura.literatura.repositorio.AutorRepository;
import br.com.alura.literatura.repositorio.LivroRepository;
import br.com.alura.literatura.service.BuscaLivro;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Menu {

    private static final String MAIN_URL = "https://gutendex.com/books/?search=";
    private BuscaLivro buscalivroApi = new BuscaLivro();
    private Scanner scan =  new Scanner(System.in);
    private String opcao = "";

    @Autowired
    private LivroRepository livroRepositorio;

    @Autowired
    private AutorRepository autorRepositorio;

    public Menu(LivroRepository repositorio) {
        this.livroRepositorio = repositorio;
    }
    public void exibeMenu() {



        while(!opcao.equals("0")) {
            System.out.println("""
                   1 - Buscar livro por nome
                   2 - Listar livros registrados
                   3 - Listar autores registrados
                   
                   0 - Sair
                
                """);
            opcao = scan.nextLine();
            switch (opcao) {
                case "0" -> System.out.println("Saindo");

                case "1" -> {

                    resultadoApi();
                }
                case "2" -> {
                    listarLivros();
                }
                case "3" -> {
                    listarAutores();
                }
                default -> System.out.println("nnnn");
            }
        }
    }

    private void listarAutores() {
        List<Autor> autor = autorRepositorio.findAll();
        autor.stream().forEach(a -> {
            System.out.println("O autor "+a.getNome()+" viveu entre "+a.getAnoNascimento()+" e "+a.getAnoMorte());
        });
    }


    private void listarLivros() {
        List<Livro> livro = livroRepositorio.findAll();
        livro.forEach(l -> System.out.println(l.getTitulo()+" "+l.getAutores()));
    }

    private DataGutendex getDataApi() {
        System.out.println("Digite o nome do livro: ");
        String livro = scan.nextLine();
        var json = buscalivroApi.obterLivro(MAIN_URL+livro.replace(" ","+"));
        DataGutendex data = new Gson().fromJson(json, DataGutendex.class);

        return data;

    }


    private void resultadoApi() {
        DataGutendex data = getDataApi();

        if (!data.results().isEmpty()) {
            Livro livroSave = new Livro(data);

            var primeiroLivro = data.results().get(0);
            primeiroLivro.authors().forEach(a -> {

                Autor autor = autorRepositorio.findByNome(a.name())
                        .orElseGet(() -> {
                            Autor novoAutor = new Autor(a.name(), a.birth_year(), a.death_year());
                            autorRepositorio.save(novoAutor);
                            return novoAutor;

                        });
                livroSave.getAutores().add(autor);


            });
            Optional<Livro> livroExiste = livroRepositorio.findByTitulo(primeiroLivro.title());
            if(livroExiste.isEmpty()) {
                livroRepositorio.save(livroSave);
            } else {
                System.out.println("Livro já cadastrado");
            }



        }else {
            System.out.println("Nenhum livro encontrado");
        }

    }
}
