package br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UtilAvisa {

    public static final String DIRETORIO_PDF = "pdfs";
    public static final String DIRETORIO_TXT = "txts";

    public static File criarDiretorio(String diretorio) {

        try {
            File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), diretorio);

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

    public static String lerArquivoTexto(String filename) throws Exception
    {
        try
        {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try
            {
                StringBuffer texto = new StringBuffer();
                // lê a primeira linha
                String linha = bufferedReader.readLine();
                if (linha != null)
                    texto.append(linha);
                // a variável "linha" recebe o valor "null" quando o processo
                // de repetição atingir o final do arquivo texto
                while (linha != null)
                {
                    // lê da segunda até a última linha
                    linha = bufferedReader.readLine();
                    if (linha != null)
                        texto.append(linha);
                }
                return texto.toString().trim();
            }
            finally
            {
                bufferedReader.close();
                fileReader.close();
            }
        }
        catch (IOException e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static boolean arquivoExiste(String nomeArquivo) {

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), nomeArquivo);

            boolean existe = file.exists() && file.length()>0.00;

            return existe;

        } catch (Exception ex) {
            System.out.println("Erro ao procurar arquivo " + ex.getMessage());
            return false;
        }
    }

    public static String obterDiretorioPdfs() {
       return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/"+DIRETORIO_PDF+"/";
    }

    public static String obterDiretorioTxts() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/"+DIRETORIO_TXT+"/";
    }
}
