package br.com.ada.cineSpring.controller;

import br.com.ada.cineSpring.dao.AtorDAO;
import br.com.ada.cineSpring.dao.NoticiaDAO;
import br.com.ada.cineSpring.dao.FilmeDAO;
import br.com.ada.cineSpring.model.Ator;
import br.com.ada.cineSpring.model.Filme;
import br.com.ada.cineSpring.model.FilmeAtoresForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/filme")
public class FilmeController {
    private static final Logger logger = LoggerFactory.getLogger(NoticiaDAO.class);
    @Autowired
    private FilmeDAO filmeDAO;
    @Autowired
    private AtorDAO atorDAO;

    @GetMapping
    public String indexPage(Model model, @RequestParam(required = false, defaultValue = "") String genero){
        List<String> generos = filmeDAO.generosExistentes();
        List<Filme> filmes = filmeDAO.buscarTodos(genero);

        model.addAttribute("filmes", filmes);
        model.addAttribute("generos", generos);

        return "filmes";
    }

    @GetMapping("/novo")
    public String novo(Model model){
        System.out.println("GET '/filme/novo'");
        Filme filme = new Filme();
        List<Integer> atoresIds = new ArrayList<>();
        FilmeAtoresForm form = new FilmeAtoresForm();

        form.setFilme(filme);
        form.setAtoresIds(atoresIds);
        model.addAttribute("form", form);
        model.addAttribute("todos_atores", atorDAO.buscarTodos());
        return "filme_novo";
    }

    @PostMapping("/novo")
    public String adicionar(@ModelAttribute("form") FilmeAtoresForm form) {
        Filme filme = form.getFilme();
        List<Integer> atoresIds = form.getAtoresIds();
        filme.setAtores(atorDAO.buscarAtoresPorId(atoresIds));
        filmeDAO.adicionar(filme);
        return "redirect:/filme";

    }

    @GetMapping("/{id}")
    public String visualizarFilme(@PathVariable int id, Model model){
        Filme filme = filmeDAO.buscarPorId(id);

        model.addAttribute("filme", filme);

        return "filme";
    }

    @GetMapping("/favoritar/{id}")
    public String favoritarFilme(@PathVariable int id){
        filmeDAO.favoritarFilme(id);
        return "redirect:/";
    }

    @GetMapping("/desfavoritar/{id}")
    public String desfavoritarFilme(@PathVariable int id){
        filmeDAO.desfavoritarFilme(id);
        return "redirect:/";
    }
    @GetMapping("/like/{id}")
    public String darLikeFilme(@PathVariable int id){
        filmeDAO.likeFilme(id);
        return "redirect:/";
    }

    @GetMapping("/dislike/{id}")
    public String darDislikeFilme (@PathVariable int id){
        filmeDAO.dislikeFilme(id);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model){
        Filme filme = filmeDAO.buscarPorId(id);
        FilmeAtoresForm form = new FilmeAtoresForm();
        form.setFilme(filme);
        form.setAtoresIds(filme.getAtores().stream().map(Ator::getId).collect(Collectors.toList()));
        model.addAttribute("form", form);
        model.addAttribute("todos_atores", atorDAO.buscarTodos());
        return "filme_editar";
    }

    @PostMapping("/editar")
    public String editar(@ModelAttribute("form") FilmeAtoresForm form){
        Filme filme = form.getFilme();
        List<Integer> atoresIds = form.getAtoresIds();
        filme.setAtores(atorDAO.buscarAtoresPorId(atoresIds));

        filmeDAO.atualizar(filme);
        return "redirect:/";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable int id) {
        try{
            filmeDAO.remover(id);
            return "redirect:/";
        } catch (Exception error){
            System.out.println(error.getMessage());
            return "redirect:/";
        }

    }

    @GetMapping("/favoritos")
    public String filmesFavoritos(Model model){
        List<Filme> filmes = filmeDAO.buscarFilmesFavoritos();
        model.addAttribute("filmes", filmes);

        return "filmes_favoritos";
    }

}
