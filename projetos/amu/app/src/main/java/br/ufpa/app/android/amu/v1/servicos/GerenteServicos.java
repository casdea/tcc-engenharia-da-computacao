package br.ufpa.app.android.amu.v1.servicos;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.concurrent.Callable;

import br.ufpa.app.android.amu.v1.activity.BemVindoActivity;
import br.ufpa.app.android.amu.v1.activity.PrincipalActivity;
import br.ufpa.app.android.amu.v1.classes.ProxyAssincronoServico;
import br.ufpa.app.android.amu.v1.classes.TransacaoVerificarAlarme;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.factoryDao.FactoryDAO;
import br.ufpa.app.android.amu.v1.dao.helper.Base64Custom;
import br.ufpa.app.android.amu.v1.dao.modelo.Alarme;
import br.ufpa.app.android.amu.v1.dao.modelo.Estoque;
import br.ufpa.app.android.amu.v1.dao.modelo.Horario;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;
import br.ufpa.app.android.amu.v1.dao.modelo.Utilizacao;
import br.ufpa.app.android.amu.v1.dto.AlarmeDTO;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.dto.UsuarioDTO;
import br.ufpa.app.android.amu.v1.dto.UtilizacaoDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class GerenteServicos  {

    final DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();
    final AppCompatActivity atividade;
    final GerenteServicosListener gerenteServicosListener;

    public GerenteServicos(AppCompatActivity atividade) {
        this.atividade = atividade;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    public Usuario incluirUsuario(UsuarioDTO usuarioDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        Usuario usuario = new Usuario(usuarioDTO);
        return factoryDAO.getUsuarioDao().create(usuario);
    }

    public Usuario alterarUsuario(UsuarioDTO usuarioDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        Usuario usuario = new Usuario(usuarioDTO);
        return factoryDAO.getUsuarioDao().update(usuario);
    }

    public void verificarUsuarioLogado() {
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        if (autenticacao.getCurrentUser() != null) {
            String emailUsuario = autenticacao.getCurrentUser().getEmail();
            String idUsuario = Base64Custom.codificarBase64(emailUsuario);

            DatabaseReference usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios").child(idUsuario);

            usuariosRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    App.usuario = snapshot.getValue(Usuario.class);

                    if (App.usuario != null) {
                        App.usuario.setIdUsuario(idUsuario);
                        App.tipoPerfil = TipoPerfil.COMUM; //TipoPerfil.valueOf(App.usuario.getTipoPerfil());

                        gerenteServicosListener.executarAcao(Constantes.ACAO_APRESENTAR_TELA_PRINCIPAL,autenticacao);
                    } else {
                        Toast.makeText(App.context,
                                "Usuário não tem mais um cadastrado válido !",
                                Toast.LENGTH_LONG).show();

                        gerenteServicosListener.executarAcao(Constantes.ACAO_APRESENTAR_TELA_BOAS_VINDAS,autenticacao);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void incluirMedicamento(MedicamentoDTO medicamentoDTO, HorarioDTO horarioDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        Medicamento medicamento = new Medicamento(medicamentoDTO);
        Horario horario = new Horario(horarioDTO);
        factoryDAO.getMedicamentosDao().create(medicamento,horario);
    }

    public void obterListaMedicamentosByUsuario(String idUsuario) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        factoryDAO.getMedicamentosDao().findAllByUsuario(idUsuario);
    }

    public void obterTextoBula(MedicamentoRetDTO medicamentoRetDTO)
    {
        App.integracaoBularioEletronico.obterTextoBula(atividade, medicamentoRetDTO);
    }

    public void obterMedicamentoByUsuarioIdProduto(String idUsuario, String idProduto) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        factoryDAO.getMedicamentosDao().findByUsuarioIdProduto(idUsuario, idProduto);
    }

    public void incluirHorario(HorarioDTO horarioDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        Horario horario = new Horario(horarioDTO);
        factoryDAO.getHorarioDao().create(horario);
    }

    public void alterarHorario(HorarioDTO horarioDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        Horario horario = new Horario(horarioDTO);
        factoryDAO.getHorarioDao().update(horario);
    }

    public void obterListaHorariosByUsuarioMedicamento(String idUsuario, String idMedicamento) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        factoryDAO.getHorarioDao().findAllByUsuarioIdMedicamento(idUsuario,idMedicamento);
    }

    public void alterarMedicamento(MedicamentoDTO medicamentoDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        Medicamento medicamento = new Medicamento(medicamentoDTO);
        factoryDAO.getMedicamentosDao().update(medicamento);
    }

    public void incluirUtilizacao(UtilizacaoDTO utilizacaoDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        Utilizacao utilizacao = new Utilizacao(utilizacaoDTO);
        factoryDAO.getUtilizacaoDao().create(utilizacao);
    }

    public void incluirEstoque(EstoqueDTO estoqueDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        Estoque estoque = new Estoque(estoqueDTO);
        factoryDAO.getEstoqueDao().create(estoque);
    }

    public void obterListaUtilizacoesByUsuarioMedicamento(String idUsuario, String idMedicamento) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        factoryDAO.getUtilizacaoDao().findAllByUsuarioIdMedicamento(idUsuario,idMedicamento);
    }

    public void atualizarSaldoEstoque(String idUsuario, EstoqueDTO estoqueDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        factoryDAO.getEstoqueDao().atualizarSaldoEstoque(idUsuario,estoqueDTO);
    }

    public void obterListaEstoquesByUsuarioMedicamento(String idUsuario, String idMedicamento) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        factoryDAO.getEstoqueDao().findAllByUsuarioIdMedicamento(idUsuario,idMedicamento);
    }

    public void sinalizarDoseRealizada(String idUsuario, EstoqueDTO estoqueDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        factoryDAO.getEstoqueDao().sinalizarDoseRealizada(idUsuario,estoqueDTO);
    }

    public void incluirAlarme(AlarmeDTO alarmeDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade);
        Alarme alarme = new Alarme(alarmeDTO);
        factoryDAO.getAlarmeDao().create(alarme);
    }

    public void verificarAlarme(List<MedicamentoDTO> medicamentos, TextView txvQtdeCadastrados, TextView txvQtdeNaoAdministrado) {
        ProxyAssincronoServico proxyAssincronoAlarme = new ProxyAssincronoServico(
                new TransacaoVerificarAlarme(atividade, medicamentos, txvQtdeCadastrados, txvQtdeNaoAdministrado));
        proxyAssincronoAlarme.executar();
    }
}
