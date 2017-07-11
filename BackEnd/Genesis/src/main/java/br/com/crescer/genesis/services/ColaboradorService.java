package br.com.crescer.genesis.services;

import br.com.crescer.genesis.entidades.Colaborador;
import br.com.crescer.genesis.entidades.ColaboradorFeito;
import br.com.crescer.genesis.repositorios.ColaboradorFeitoRepositorio;
import br.com.crescer.genesis.repositorios.ColaboradorRepositorio;
import br.com.crescer.genesis.repositorios.FeitoRepositorio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * @author mirela.adam
 */
@Service
public class ColaboradorService {

    @Autowired
    ColaboradorRepositorio colabRepositorio;

    @Autowired
    EmailService email;

    @Autowired
    CriptografiaService criptografia;
    
    @Autowired
    ColaboradorFeitoRepositorio colabFeitoRepositorio;
    
    @Autowired 
    FeitoRepositorio feitoRepositorio;

    public Iterable<Colaborador> buscarTodos() {
        return colabRepositorio.findAll();
    }

    public Colaborador buscarPorID(Long id) {
        return colabRepositorio.findOneById(id);
    }

    public Iterable<Colaborador> buscarPorNome(String texto) {
        return colabRepositorio.findByNomecompletoContainingIgnoreCase(texto);
    }

    public Colaborador cadastrar(Colaborador colab) throws Exception {        
        final Colaborador colaborador = colabRepositorio.findOneByEmail(colab.getEmail());
        final String url = "http://localhost:9090/colaboradores/novo-acesso/nova-senha/" +  criptografia.encrypt(colab.getEmail());
        final boolean novoColaborador = colaborador == null ? true : !colaborador.getEmail().equals(colab.getEmail());
        final boolean contemSenhaCadastrada = colab.getSenha() == null;

        if (contemSenhaCadastrada && novoColaborador) {
            final String assunto = "cadastrar senha";
            String mensagem = "acesse o link para cadastrar sua senha .:  "+ url + criptografia.encrypt(colab.getEmail());
            
            //String senha = colab.getSenha();
            //String novaSenha = new BCryptPasswordEncoder().encode(senha);
            //colab.setSenha(novaSenha);
                        
            List<Colaborador> listaColaborador = new ArrayList<>();
            listaColaborador.add(colab);           
            
            email.enviarEmail(listaColaborador, assunto, mensagem);
            Colaborador colaboradorCadastrado = colabRepositorio.save(colab);
            
            ColaboradorFeito colaboradorFeito = new ColaboradorFeito();
            colaboradorFeito.setId(0L);
            colaboradorFeito.setIdColaborador(colaboradorCadastrado);
            colaboradorFeito.setIdFeito(feitoRepositorio.findOneById(1L));
            colaboradorFeito.setDatafeito(colaboradorCadastrado.getAdmissao());
            
            colabFeitoRepositorio.save(colaboradorFeito);
            
            return colaboradorCadastrado;
        }

        return null;
    }

    public Colaborador atualizar(Colaborador colab) {
        String senha = colab.getSenha();
        String novaSenha = new BCryptPasswordEncoder().encode(senha);
        colab.setSenha(novaSenha);
        return colabRepositorio.save(colab);
    }
    
    public Colaborador buscarPorEmail(String email){
        return colabRepositorio.findOneByEmail(email);
    }

    public Colaborador buscarPorEmailCriptografado(Map<String,String> emailBuscar) throws Exception {
        try {
            String email = criptografia.decrypt(emailBuscar.get("email"));
            return colabRepositorio.findOneByEmail(email);
        } catch (Exception e) {
            throw new Exception("Erro ao buscar Email");
        }
    }

    public Colaborador cadastrarSenhaNova(HashMap<String, String> map) throws Exception {
        try {
            Colaborador col = buscarPorEmail(map.get("email"));
            col.setSenha(new BCryptPasswordEncoder().encode(map.get("senha")));
            return colabRepositorio.save(col);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
