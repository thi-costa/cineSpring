package br.com.ada.cineSpring.controller;

import br.com.ada.cineSpring.dao.FilmeDAO;
import br.com.ada.cineSpring.dao.NoticiaDAO;
import br.com.ada.cineSpring.model.Filme;
import br.com.ada.cineSpring.model.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private FilmeDAO filmeDAO;
    @Autowired
    private NoticiaDAO noticiaDAO;

    @GetMapping
    public String indexPage(Model model){
        List<Filme> filmes = filmeDAO.buscarFilmesMaisCurtidos();
        model.addAttribute("filmes", filmes);

        List<Noticia> noticias = noticiaDAO.buscarTodos();
        model.addAttribute("noticias", noticias);

        return "index";
    }
}
