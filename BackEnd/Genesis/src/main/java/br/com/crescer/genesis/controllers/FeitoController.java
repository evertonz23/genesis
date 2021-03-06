package br.com.crescer.genesis.controllers;

import br.com.crescer.genesis.entidades.Colaborador;
import br.com.crescer.genesis.entidades.Feito;
import br.com.crescer.genesis.services.ColaboradorService;
import br.com.crescer.genesis.services.FeitoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rafa
 */
@RestController
@RequestMapping(value = "/feitos")
public class FeitoController {
    
    @Autowired
    FeitoService service;
    
    
    @Autowired 
    ColaboradorService serviceColaborador;
    
    @GetMapping
    @Secured("ROLE_ADMINISTRADOR")
    public List<Feito> listarFeitos(){
        return service.buscarTodosFeitos();
    }
    
    @PostMapping
    @Secured("ROLE_ADMINISTRADOR")
    public Feito cadastraFeito(@RequestBody Feito feito){
       return service.cadastrarFeito(feito);
    }
    
    @PutMapping
    @Secured("ROLE_ADMINISTRADOR")
    public Feito atualizarFeito(@RequestBody Feito feito){
        return service.atualizarFeito(feito);
    }
    
    @RequestMapping(value = "/excluir", method = RequestMethod.POST)
    @Secured("ROLE_ADMINISTRADOR")
    public Feito removerFeito(@RequestBody Feito feito){
        return service.removerFeito(feito);
    }
    
    @GetMapping("/{id}")
    public Feito procurarPorId(@PathVariable Long id){
        return service.buscarFeitoPorId(id);
    }
    
    @GetMapping("/permissao")
    public List<Feito> procurarPorPermissao(@AuthenticationPrincipal User user){
        Colaborador colab = serviceColaborador.buscarPorEmail(user.getUsername());
        return service.buscarPorPermissao(colab.getIdPermissao());
    }
}
