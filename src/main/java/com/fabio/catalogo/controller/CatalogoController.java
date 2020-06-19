package com.fabio.catalogo.controller;

import com.fabio.catalogo.service.CatalogoService;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.fabio.catalogo.model.Musica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CatalogoController {

    @Autowired
    CatalogoService catalogoService;

    @RequestMapping(value = "/musicas", method = RequestMethod.GET)
    public ModelAndView getMusicas() {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findAll();
        mv.addObject("musicas", musicas);
        return mv;
    }

    @RequestMapping(value = "/musicas/{id}", method = RequestMethod.GET)
    public ModelAndView getMusicaDetalhes(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaDetalhes");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value = "/addMusica", method = RequestMethod.GET)
    public String getMusicaForm() {
        return "musicaForm";
    }

    @RequestMapping(value = "/addMusica", method = RequestMethod.POST)
    public String salvarMusicas(@Valid Musica musica, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {

            attributes.addFlashAttribute("mensagem", "Campos obrigatórios não preenchidos!");
            return "redirect:/addMusica";
        }

        musica.setData(LocalDate.now());
        catalogoService.save(musica);
        return "redirect:/musicas";
    }


    @RequestMapping(value = "/removerMusica/{id}", method = RequestMethod.GET)
    public String removerMusica(@PathVariable("id") long id) {
        catalogoService.delete(id);

        return "redirect:/musicas";
    }

    @RequestMapping(value = "/editarMusica/{id}", method = RequestMethod.GET)
    public ModelAndView getEditarMusica(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaEdit");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value = "/editarMusica/{id}", method = RequestMethod.POST)
    public String atualizarMusica(@Valid Musica musica, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {

            attributes.addFlashAttribute("mensagem", "Campos obrigatórios não preenchidos!");
            return "redirect:/addMusica";
        }

        musica.setData(LocalDate.now());
        catalogoService.update(musica);
        return "redirect:/musicas";
    }

}
