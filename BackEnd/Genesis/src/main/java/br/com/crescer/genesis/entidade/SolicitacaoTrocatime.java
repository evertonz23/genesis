/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.crescer.genesis.entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafael.barreto
 */
@Entity
@Table(name = "SOLICITACAO_TROCATIME")
@XmlRootElement
public class SolicitacaoTrocatime implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SQ_NAME = "SEQ_SOLICITACAO_TROCATIME";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SQ_NAME)
    @SequenceGenerator(name = SQ_NAME, sequenceName = SQ_NAME, allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "ID_COLABORADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Colaborador idColaborador;
    @JoinColumn(name = "ID_NOVOTIME", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Timecwi idNovotime;

    public SolicitacaoTrocatime() {
    }

    public SolicitacaoTrocatime(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Colaborador getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Colaborador idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Timecwi getIdNovotime() {
        return idNovotime;
    }

    public void setIdNovotime(Timecwi idNovotime) {
        this.idNovotime = idNovotime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitacaoTrocatime)) {
            return false;
        }
        SolicitacaoTrocatime other = (SolicitacaoTrocatime) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.crescer.genesis.entidade.SolicitacaoTrocatime[ id=" + id + " ]";
    }

}
