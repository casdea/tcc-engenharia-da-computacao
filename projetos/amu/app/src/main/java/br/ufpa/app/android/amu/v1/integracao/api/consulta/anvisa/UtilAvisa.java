package br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import br.ufpa.app.android.amu.v1.BuildConfig;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.util.App;

public class UtilAvisa {

    public static final String DIRETORIO_PDF = "pdfs";
    public static final String DIRETORIO_TXT = "txts";
    public static final String CHAVE_IDENTIFICACAO = "X@@@X0@";
    public static final String CHAVE_PARA_QUE_INDICADO = "X@@@X1@";
    public static final String CHAVE_COMO_FUNCIONA = "X@@@X2@";
    public static final String CHAVE_QUANDO_NAO_USAR = "X@@@X3@";
    public static final String CHAVE_SABER_ANTES_USAR = "X@@@X4@";
    public static final String CHAVE_QUANTO_TEMPO_GUARDAR = "X@@@X5@";
    public static final String CHAVE_COMO_USAR = "X@@@X6@";
    public static final String CHAVE_QUANDO_ESQUECER = "X@@@X7@";
    public static final String CHAVE_MALES_CAUSAM = "X@@@X8@";
    public static final String CHAVE_SUPERDOSAGEM = "X@@@X9@";
    public static final String CHAVE_DIZERES_LEGAIS = "X@@@X10@";
    public static final String CHAVE_HISTORICO_ALTERACAO_BULA1 = "X@@@X11@";
    public static final String CHAVE_HISTORICO_ALTERACAO_BULA2 = "X@@@X11@";

    public static final String CODIGO_IDENTIFICACAO = "0@";
    public static final String CODIGO_PARA_QUE_INDICADO = "1@";
    public static final String CODIGO_COMO_FUNCIONA = "2@";
    public static final String CODIGO_COMO_USAR = "6@";
    public static final String CODIGO_QUANDO_ESQUECER = "7@";

    public static final String PARA_QUE_INDICADO = "1. PARA QUE ESTE MEDICAMENTO É INDICADO?";
    public static final String COMO_FUNCIONA = "2. COMO ESTE MEDICAMENTO FUNCIONA?";
    public static final String QUANDO_NAO_USAR = "3. QUANDO NÃO DEVO USAR ESTE MEDICAMENTO?";
    public static final String SABER_ANTES_USAR = "4. O QUE DEVO SABER ANTES DE USAR ESTE MEDICAMENTO?";
    public static final String QUANTO_TEMPO_GUARDAR = "5. ONDE, COMO E POR QUANTO TEMPO POSSO GUARDAR ESTE MEDICAMENTO?";
    public static final String COMO_USAR = "6. COMO DEVO USAR ESTE MEDICAMENTO?";
    public static final String QUANDO_ESQUECER = "7. O QUE DEVO FAZER QUANDO EU ME ESQUECER DE USAR ESTE MEDICAMENTO?";
    public static final String MALES_CAUSAM = "8. QUAIS OS MALES QUE ESTE MEDICAMENTO PODE ME CAUSAR?";
    public static final String SUPERDOSAGEM = "9. O QUE FAZER SE ALGUÉM USAR UMA QUANTIDADE MAIOR DO QUE A INDICADA DESTE MEDICAMENTO?";
    public static final String DIZERES_LEGAIS = "DIZERES LEGAIS";
    public static final String HISTORICO_ALTERACAO_BULA1 = "Histórico de Alteração da Bula";
    public static final String HISTORICO_ALTERACAO_BULA2 = "Histórico de Alteração para a Bula";

    public static final String CHAVE_IDENTIFICACAO_APRESENTACAO = "X@@@X21@";
    public static final String CHAVE_IDENTIFICACAO_COMPOSICAO = "X@@@X22@";
    public static final String CHAVE_IDENTIFICACAO_VIA_ADM = "X@@@X23@";
    public static final String CHAVE_IDENTIFICACAO_PUBLICO_ALVO = "X@@@X24@";

    public static final String CODIGO_IDENTIFICACAO_APRESENTACAO = "21@";
    public static final String CODIGO_IDENTIFICACAO_COMPOSICAO = "22@";
    public static final String CODIGO_IDENTIFICACAO_VIA_ADM = "23@";

    public static final String IDENTIFICACAO_APRESENTACAO = "APRESENTAÇÕES";
    public static final String IDENTIFICACAO_COMPOSICAO = "COMPOSIÇÃO";
    public static final String IDENTIFICACAO_VIA_ADM = " USO ";
    public static final String IDENTIFICACAO_PUBLICO_ALVO = " USO ";
    public static final String CHAVE_QUEBRA = "X@@@X";

    public static File criarDiretorio(String diretorio) {

        try {
            File path = new File(diretorioRoot(), diretorio);

            if (!path.exists()) {
                if (!path.mkdirs()) {
                    throw new Exception("Diretorio não foi criado " + diretorio);
                } else {
                    //create new folder
                }
            }
            return path;
        } catch (Exception ex) {
            System.out.println("Erro ao criar diretorio " + ex.getMessage());
            return null;
        }
    }

    public static String lerArquivoTexto(String filename) throws Exception {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try {
                StringBuffer texto = new StringBuffer();
                // lê a primeira linha
                String linha = bufferedReader.readLine();
                if (linha != null)
                    texto.append(linha);
                // a variável "linha" recebe o valor "null" quando o processo
                // de repetição atingir o final do arquivo texto
                while (linha != null) {
                    // lê da segunda até a última linha
                    linha = bufferedReader.readLine();
                    if (linha != null)
                        texto.append(linha);
                }
                return texto.toString().trim();
            } finally {
                bufferedReader.close();
                fileReader.close();
            }
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static boolean arquivoExiste(String nomeArquivo) {

        try {
            File file = new File(diretorioRoot(), nomeArquivo);

            return file.exists() && file.length() > 0.00;

        } catch (Exception ex) {
            System.out.println("Erro ao procurar arquivo " + ex.getMessage());
            return false;
        }
    }

    public static String obterDiretorioPdfs() {
        return diretorioRoot() + "/" + DIRETORIO_PDF + "/";
    }

    public static String obterDiretorioTxts() {
        return diretorioRoot() + "/" + DIRETORIO_TXT + "/";
    }

    public static String diretorioRoot() {
        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        if (BuildConfig.DEBUG)
            Log.i("Nome do Arquivo 1", App.context.getExternalFilesDir(Environment.DIRECTORY_DCIM) + "");
        return App.context.getExternalFilesDir(Environment.DIRECTORY_DCIM) + "";
    }

    private static String findParteByChave(String[] partes, String chaveParte) {
        String parte;

        for (int i = 0; i < partes.length; i++) {
            parte = partes[i].trim();

            if (parte.equals("") || parte.length()<=5) continue;

            if (BuildConfig.DEBUG)
                Log.i("Decodificando a parte ",parte);
            try {
                if (parte.startsWith(chaveParte)) {
                    return parte.substring(chaveParte.length());
                }
            } catch (Exception ex) {
                System.out.println("Erro ao procurar arquivo " + ex.getMessage());
            }

        }

        return "";
    }

    public static MedicamentoDTO textoToMedicamento(MedicamentoRetDTO medicamentoRetDTO, String bula) {
        bula = CHAVE_IDENTIFICACAO + bula.trim();
        bula = bula.replaceAll(PARA_QUE_INDICADO, CHAVE_PARA_QUE_INDICADO);
        bula = bula.replaceAll(COMO_FUNCIONA, CHAVE_COMO_FUNCIONA);
        bula = bula.replaceAll(QUANDO_NAO_USAR, CHAVE_QUANDO_NAO_USAR);
        bula = bula.replaceAll(SABER_ANTES_USAR, CHAVE_SABER_ANTES_USAR);
        bula = bula.replaceAll(QUANTO_TEMPO_GUARDAR, CHAVE_QUANTO_TEMPO_GUARDAR);
        bula = bula.replaceAll(COMO_USAR, CHAVE_COMO_USAR);
        bula = bula.replaceAll(QUANDO_ESQUECER, CHAVE_QUANDO_ESQUECER);
        bula = bula.replaceAll(MALES_CAUSAM, CHAVE_MALES_CAUSAM);
        bula = bula.replaceAll(SUPERDOSAGEM, CHAVE_SUPERDOSAGEM);
        bula = bula.replaceAll(DIZERES_LEGAIS, CHAVE_DIZERES_LEGAIS);
        bula = bula.replaceAll(HISTORICO_ALTERACAO_BULA1, CHAVE_HISTORICO_ALTERACAO_BULA1);
        bula = bula.replaceAll(HISTORICO_ALTERACAO_BULA2, CHAVE_HISTORICO_ALTERACAO_BULA2);

        String[] partes = bula.split(CHAVE_QUEBRA);

        for (int i = 0; i < partes.length - 1; i++) {

            System.out.println("Parte " + (i + 1) + " " + partes[i]);
        }

        String identificacao = findParteByChave(partes, CODIGO_IDENTIFICACAO);

        boolean usoInfatil = identificacao.toUpperCase().contains("PEDIATRICO");
        boolean usoAdulto = identificacao.toUpperCase().contains("ADULTO");

        identificacao = CHAVE_IDENTIFICACAO + identificacao.trim();
        identificacao = identificacao.replaceAll(IDENTIFICACAO_APRESENTACAO,CHAVE_IDENTIFICACAO_APRESENTACAO);
        identificacao = identificacao.replaceAll(IDENTIFICACAO_COMPOSICAO,CHAVE_IDENTIFICACAO_COMPOSICAO);
        identificacao = identificacao.replace(IDENTIFICACAO_VIA_ADM,CHAVE_IDENTIFICACAO_VIA_ADM);
        identificacao = identificacao.replace(IDENTIFICACAO_PUBLICO_ALVO,CHAVE_IDENTIFICACAO_PUBLICO_ALVO);


        String[] partesIdentificacao = identificacao.split(CHAVE_QUEBRA);

        MedicamentoDTO medicamentoDTO = new MedicamentoDTO();
        //preencher dados
        medicamentoDTO.setNomeComercial(medicamentoRetDTO.getNomeComercial());
        medicamentoDTO.setFabricante(medicamentoRetDTO.getNomeLaboratorio());
        medicamentoDTO.setIdProdutoAnvisa(medicamentoRetDTO.getIdProduto());
        medicamentoDTO.setDataProdutoAnvisa(medicamentoRetDTO.getDataProduto());
        medicamentoDTO.setPrincipioAtivo(findParteByChave(partesIdentificacao, CODIGO_IDENTIFICACAO));
        medicamentoDTO.setFormaApresentacao(findParteByChave(partesIdentificacao, CODIGO_IDENTIFICACAO_APRESENTACAO));
        medicamentoDTO.setViaAdministracao("USO "+findParteByChave(partesIdentificacao, CODIGO_IDENTIFICACAO_VIA_ADM));
        medicamentoDTO.setPublicoAlvo("USO "+(usoInfatil ? "PEDIATRICO " : "") + (usoAdulto ? " ADULTO " : ""));
        //INTERAÇÕES MEDICAMENTOSAS
        //Interações medicamentosas
        medicamentoDTO.setComposicao(findParteByChave(partesIdentificacao, CODIGO_IDENTIFICACAO_COMPOSICAO));
        medicamentoDTO.setTextoParaQueIndicado(findParteByChave(partes, CODIGO_PARA_QUE_INDICADO));
        medicamentoDTO.setTextoComoFunciona(findParteByChave(partes, CODIGO_COMO_FUNCIONA));
        medicamentoDTO.setTextoComoUsar(findParteByChave(partes, CODIGO_COMO_USAR));
        medicamentoDTO.setTextoSeEsquecerQueFazer(findParteByChave(partes, CODIGO_QUANDO_ESQUECER));
        return medicamentoDTO;
    }

}
