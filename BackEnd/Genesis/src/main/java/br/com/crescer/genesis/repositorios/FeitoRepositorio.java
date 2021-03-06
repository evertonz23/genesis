/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.crescer.genesis.repositorios;

import br.com.crescer.genesis.entidades.Feito;
import br.com.crescer.genesis.entidades.Permissao;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author rafa
 */
public interface FeitoRepositorio extends CrudRepository<Feito, Long> {
    
    public Feito findOneById(long Id);
    public Feito findTop1ByNomeContainingIgnoreCase(String nome);
    public List<Feito> findAllByIdPermissao(Permissao p);
    public Iterable<Feito> findByNomeIgnoreCase(String nome);
    
}
