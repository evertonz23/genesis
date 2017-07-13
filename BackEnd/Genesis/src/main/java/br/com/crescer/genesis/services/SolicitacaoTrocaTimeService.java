/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.crescer.genesis.services;

import br.com.crescer.genesis.entidades.SolicitacaoTrocatime;
import br.com.crescer.genesis.repositorios.SolicitacaoTrocaTimeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alana'
 */
@Service
public class SolicitacaoTrocaTimeService {
    @Autowired
    SolicitacaoTrocaTimeRepositorio repositorio;
    
    public SolicitacaoTrocatime criarSolicitacao(SolicitacaoTrocatime solicitacao){
        return repositorio.save(solicitacao);
    }
}
