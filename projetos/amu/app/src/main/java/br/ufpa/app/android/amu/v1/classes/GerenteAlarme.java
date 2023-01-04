package br.ufpa.app.android.amu.v1.classes;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.ufpa.app.android.amu.v1.BuildConfig;
import br.ufpa.app.android.amu.v1.dto.AlarmeDTO;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MapaHorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.dto.UtilizacaoDTO;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.DataUtil;

public class GerenteAlarme {

    private final AppCompatActivity atividade;
    private final List<MedicamentoDTO> medicamentos;
    private final List<UtilizacaoDTO> utilizacoes;
    private final List<AlarmeDTO> alarmes;
    private List<MapaHorarioDTO> vMapa = new ArrayList<>();

    public GerenteAlarme(AppCompatActivity atividade, List<MedicamentoDTO> medicamentos, List<UtilizacaoDTO> utilizacoes, List<AlarmeDTO> alarmes) {
        this.atividade = atividade;
        this.medicamentos = medicamentos;
        this.utilizacoes = utilizacoes;
        this.alarmes = alarmes;
    }

    public void verificar(TextView txvQtdeCadastrados, TextView txvQtdeNaoAdministrado) throws InterruptedException {


        int cadastradosHorarioAtivo = 0;
        int naoAdministrados = 0;
        List<AlarmeDTO> vAlarmesTodos = new ArrayList<>();

        for (MedicamentoDTO medicamentoDTO : medicamentos) {

            HorarioDTO horarioDTO = obterHorario(medicamentoDTO);

            if (horarioDTO == null) continue;

            cadastradosHorarioAtivo++;

            List<AlarmeDTO> vAlarmes = verificarCriarAlarme(App.listaUtilizacoes, horarioDTO, medicamentoDTO);

            for (AlarmeDTO alarmeDTO : vAlarmes) {
                GerenteServicos gerenteServicos = new GerenteServicos(atividade);
                App.alarmeDTO = alarmeDTO;
                gerenteServicos.incluirAlarme(alarmeDTO);
            }

            vAlarmesTodos.addAll(vAlarmes);

            int horariosPrescritos = contarDosesPrescritas();
            if (horariosPrescritos > utilizacoes.size()) naoAdministrados++;
        }

        for (AlarmeDTO alarmeDTO : vAlarmesTodos) {
            enviarNotificacao(alarmeDTO.getIdMedicamento(), alarmeDTO.getTitulo(), alarmeDTO.getDescricao());
        }

        for (AlarmeDTO alarmeDTO : vAlarmesTodos) {
            App.integracaoUsuario.dispararAlarme(alarmeDTO.getDescricao());
            //ThreadUtil.esperar(ThreadUtil.CINCO_SEGUNDOS);
        }

        txvQtdeCadastrados.setText(String.valueOf(cadastradosHorarioAtivo));
        txvQtdeNaoAdministrado.setText(String.valueOf(naoAdministrados));
    }

    private List<AlarmeDTO> verificarCriarAlarme(List<UtilizacaoDTO> utilizacoes, HorarioDTO
            horarioDTO, MedicamentoDTO medicamentoDTO) {
        List<AlarmeDTO> vAlarmes = new ArrayList<>();

        if (utilizacoes.size() == horarioDTO.getNrDoses())
            return vAlarmes;
        //

        try {
            this.vMapa = criarMapaHorario(DataUtil.convertDateToString(DataUtil.getDataAtual()), horarioDTO.getHorarioInicial(), Integer.parseInt(horarioDTO.getIntervalo().substring(0, 2).trim()), horarioDTO.getNrDoses(), utilizacoes);

            if (utilizacoes.size() <= horarioDTO.getNrDoses())
                verificarCriarAlarmeDose(vAlarmes, vMapa, medicamentoDTO);
            if (utilizacoes.size() > horarioDTO.getNrDoses()) {
                MapaHorarioDTO ultimoHorarioDTO = obterHoraUltimaDose(vMapa);

                verificarCriarAlarmeSuperDose(vAlarmes, utilizacoes, horarioDTO, ultimoHorarioDTO, medicamentoDTO);
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.i("verificarCriarAlarme", e.getMessage());
            e.printStackTrace();
        }
        return vAlarmes;
    }

    private void verificarCriarAlarmeDose(List<AlarmeDTO> vAlarmes, List<MapaHorarioDTO> vMapa, MedicamentoDTO medicamentoDTO) {
        for (MapaHorarioDTO mapaHorarioDTO : vMapa) {
            if (mapaHorarioDTO.getHoraPrescrita() != null && mapaHorarioDTO.getHoraAdministrada() == null) {
                int minutos = DataUtil.getDiferencaEmMinutos(DataUtil.getDataAtual(), mapaHorarioDTO.getHoraPrescrita());

                if (gerarAlarme(medicamentoDTO.getIdMedicamento(), AlarmeDTO.TIPO_ALARME_PROXIMA_DOSE, mapaHorarioDTO,-5,0))
                {
                    vAlarmes.add(new AlarmeDTO("0", medicamentoDTO, AlarmeDTO.TIPO_ALARME_PROXIMA_DOSE, minutos));
                }

                if (gerarAlarme(medicamentoDTO.getIdMedicamento(), AlarmeDTO.TIPO_ALARME_HORA_DOSE, mapaHorarioDTO,0,0))
                {
                    vAlarmes.add(new AlarmeDTO("0", medicamentoDTO, AlarmeDTO.TIPO_ALARME_HORA_DOSE, minutos));
                }

                if (gerarAlarme(medicamentoDTO.getIdMedicamento(),AlarmeDTO.TIPO_ALARME_DOSE_ATRASADA, mapaHorarioDTO,1,5))
                {
                    vAlarmes.add(new AlarmeDTO("0", medicamentoDTO, AlarmeDTO.TIPO_ALARME_DOSE_ATRASADA, minutos));
                }
            }
        }
    }

    private void verificarCriarAlarmeSuperDose(List<AlarmeDTO> vAlarmes, List<UtilizacaoDTO> utilizacoes, HorarioDTO horarioDTO, MapaHorarioDTO mapaHorarioDTO, MedicamentoDTO medicamentoDTO)
    {
        if (gerarAlarme(medicamentoDTO.getIdMedicamento(), AlarmeDTO.TIPO_ALARME_PROXIMA_DOSE, mapaHorarioDTO, 1,5))
        {
            vAlarmes.add(new AlarmeDTO("0", medicamentoDTO, AlarmeDTO.TIPO_ALARME_DOSE_EXCEDIDA, horarioDTO.getNrDoses(),utilizacoes.size()));
        }
    }

    private HorarioDTO obterHorario(MedicamentoDTO medicamentoDTO) {
        for (HorarioDTO horarioDTO : App.listaHorarios) {
            if (!horarioDTO.getIdMedicamento().equals(medicamentoDTO.getIdMedicamento()))
                continue;

            if (horarioDTO.getAtivo().equals("NÃO")) continue;
            if (new java.util.Date().before(DataUtil.convertStringToDate(horarioDTO.getDataInicial())))
                continue;

            return horarioDTO;
        }

        return null;
    }

    private List<MapaHorarioDTO> criarMapaHorario(String dataInicial, String horaInicial,
                                                  int intervalo, int nrDoses, List<UtilizacaoDTO> utilizacoes) throws Exception {
        List<MapaHorarioDTO> vMapa = new ArrayList<MapaHorarioDTO>();
        Date horaPrescrita = DataUtil.convertStringToDate(dataInicial + " " + horaInicial, "dd/MM/yyyy HH:mm");
        Date horaAdministrada = null;
        for (int i = 0; i < nrDoses; i++) {
            MapaHorarioDTO dto = new MapaHorarioDTO(i + 1, horaPrescrita, horaAdministrada);
            horaPrescrita = DataUtil.somaHoras(horaPrescrita, intervalo);
            vMapa.add(dto);
        }
        ordenarUtilizacoes(utilizacoes);
        int i = 0;
        for (UtilizacaoDTO utilizacaoDTO : utilizacoes) {
            if (i <= vMapa.size() - 1)
                vMapa.get(i).setHoraAdministrada(utilizacaoDTO.getDataUtilizacao());
            else {
                vMapa.add(new MapaHorarioDTO(i + 1, null, utilizacaoDTO.getDataUtilizacao()));
            }
            i++;
        }
        return vMapa;
    }

    private void ordenarUtilizacoes(List<UtilizacaoDTO> utilizacoes) throws Exception {
        for (UtilizacaoDTO utilizacaoDTO : utilizacoes) {
            utilizacaoDTO.setDataUtilizacao(DataUtil.convertStringToDate(utilizacaoDTO.getDataHora(), "dd/MM/yyyy HH:mm"));
        }
        Collections.sort(utilizacoes, new Comparator<UtilizacaoDTO>() {
            @Override
            public int compare(UtilizacaoDTO o1, UtilizacaoDTO o2) {
                // TODO Auto-generated method stub
                return o1.getDataUtilizacao().compareTo(o2.getDataUtilizacao());
            }
        });
    }

    private void enviarNotificacao(String idMedicamento, String titulo, String corpo) throws InterruptedException {
        new TransacaoEnviarNotificacao(atividade,idMedicamento, titulo,corpo).executar();
    }

    //26/12/2022 19:55
    //26/12/2022 20:00
    //26/12/2022 20:00

    private boolean gerarAlarme(String idMedicameto, int tipoAlarme, MapaHorarioDTO
            mapaHorarioDTO, int minutosInicio, int minutosFim) {
        Date inicio = DataUtil.somaMinutos(mapaHorarioDTO.getHoraPrescrita(), minutosInicio);
        Date fim = DataUtil.somaMinutos(mapaHorarioDTO.getHoraPrescrita(), minutosFim);

        if (DataUtil.getDataAtual().before(inicio)) return false;
        if (DataUtil.getDataAtual().after(fim)) return false;

        for (AlarmeDTO alarmeDTO : this.alarmes) {
            if (!alarmeDTO.getIdMedicamento().equals(idMedicameto)) continue;
            if (alarmeDTO.getTipoAlarme() != tipoAlarme) continue;

            if (DataUtil.convertStringToDateTime(alarmeDTO.getDataHora()).before(inicio))
                continue;
            if (DataUtil.convertStringToDateTime(alarmeDTO.getDataHora()).after(fim)) continue;

            return false;
        }

        return true;
    }

    private MapaHorarioDTO obterHoraUltimaDose(List<MapaHorarioDTO> vMapa) {
        MapaHorarioDTO mapaHorarioUltimoDTO = null;

        for (MapaHorarioDTO mapaHorarioDTO : vMapa) {
            if (mapaHorarioDTO.getHoraPrescrita() != null && mapaHorarioDTO.getHoraAdministrada() == null) {
                mapaHorarioUltimoDTO = mapaHorarioDTO;
            }
        }

        return mapaHorarioUltimoDTO;
    }

    private int contarDosesPrescritas() {
        int contador = 0;
        for (MapaHorarioDTO mapaHorarioDTO : this.vMapa) {
            if (mapaHorarioDTO.getHoraPrescrita() != null && mapaHorarioDTO.getHoraAdministrada() == null) {
                contador++;
            }
        }

        return contador;
    }
}
