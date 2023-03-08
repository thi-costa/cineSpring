package br.com.ada.cineSpring.controller;

import br.com.ada.cineSpring.dao.AtorDAO;
import br.com.ada.cineSpring.dao.FilmeDAO;
import br.com.ada.cineSpring.dao.NoticiaDAO;
import br.com.ada.cineSpring.model.Ator;
import br.com.ada.cineSpring.model.Filme;
import br.com.ada.cineSpring.model.Noticia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ator")
public class AtorController {
    private static final Logger logger = LoggerFactory.getLogger(AtorDAO.class);
    @Autowired
    private AtorDAO atorDAO;

    @GetMapping
    public String indexPage(Model model){
        List<Ator> atores = atorDAO.buscarTodos();
        model.addAttribute("atores", atores);

        return "atores";
    }

    @GetMapping("/novo")
    public String novo(Model model){
        model.addAttribute("ator", new Ator());
        return "ator_novo";
    }

    @PostMapping("/novo")
    public String adicionar(Ator ator){
        atorDAO.adicionar(ator);
        return "redirect:/ator";
    }
}
