package br.ufpa.app.android.amu.v1.util;

import android.content.Intent;

import br.ufpa.app.android.amu.v1.activity.DetalheMedicamentoActivity;
import br.ufpa.app.android.amu.v1.activity.PrincipalActivity;
import br.ufpa.app.android.amu.v1.integracao.classes.ComandosVoz;

public class Constantes {
    public static final int ACAO_REGISTRAR_MEDICAMENTO = 1;
    public static final int ACAO_OBTER_LISTA_MEDICAMENTO_POR_USUARIO = 2;
    public static final int ACAO_OBTER_MEDICAMENTO_POR_USUARIO_PRODUTO = 3;
    public static final int ACAO_REGISTRAR_HORARIO = 4;
    public static final int ACAO_ALTERAR_MEDICAMENTO = 5;
    public static final int ACAO_ALTERAR_HORARIO = 6;
    public static final int ACAO_REGISTRAR_ESTOQUE = 7;
    public static final int ACAO_ALTERAR_ESTOQUE = 8;
    public static final int ACAO_REGISTRAR_UTILIZACAO = 9;
    public static final int ACAO_ALTERAR_UTILIZACAO = 10;
    public static final int ACAO_OBTER_ULTIMO_ESTOQUE_POR_USUARIO_PRODUTO = 11;
    public static final int ACAO_OBTER_LISTA_HORARIO_USUARIO_MEDICAMENTO = 12;
    public static final int ACAO_OBTER_LISTA_UTILIZACAO_POR_USUARIO_MEDICAMENTO = 13;
    public static final int ACAO_OBTER_LISTA_ESTOQUE_POR_USUARIO_MEDICAMENTO = 14;
    public static final int ACAO_APRESENTAR_TELA_PRINCIPAL = 15;
    public static final int ACAO_APRESENTAR_TELA_BOAS_VINDAS = 16;
    public static final int ACAO_REGISTRAR_COMANDO = 17;
    public static final int ACAO_REGISTRAR_VARIACAO_COMANDO = 18;
    public static final int ACAO_ALTERAR_COMANDO = 19;
    public static final int ACAO_ALTERAR_VARIACAO_COMANDO = 20;
    public static final int ACAO_OBTER_LISTA_COMANDO_POR_USUARIO = 21;
    public static final int ACAO_OBTER_LISTA_VARIACAO_POR_USUARIO_COMANDO = 22;

    public static final int ACAO_VOZ_LISTA_MEDICAMENTOS = 23;
    public static final int ACAO_VOZ_DESCREVA_MEDICAMENTO = 24;
    public static final int ACAO_VOZ_DESCREVA_HORARIO = 25;
    public static final int ACAO_VOZ_TELA_ANTERIOR = 26;
    public static final int ACAO_CHAMAR_COMANDO_VOZ = 27;
    public static final int ACAO_VOZ_DOSE_REALIZADA = 28;
    public static final int ACAO_ERRO_AO_ATUALIZAR_SALDO_ESTOQUE = 29;
    public static final int ACAO_RECEBER_TEXTO_BULA = 30;
    public static final int ACAO_FECHAR_TELA = 31;
    public static final int ACAO_ERRO_SEM_SALDO_ESTOQUE = 32;
    public static final int ACAO_ATUALIZAR_SALDO_ESTOQUE = 33;
    public static final int ACAO_UTLIZACAO_REMEDIO_CONCLUIDA = 34;
    public static final int ACAO_VOZ_ESTOQUE_ATUAL = 35;
    public static final int ACAO_VOZ_ENTRADA_ESTOQUE = 36;
    public static final int ACAO_VOZ_SAIDA_ESTOQUE = 37;
    public static final int ACAO_AVISAR_SALDO_ATUALIZADO = 38;
    public static final int ACAO_VOZ_ALTERNAR_PERFIL = 39;
    public static final int ACAO_VOZ_FECHAR_APP = 40;
    public static final int ACAO_OBTER_LISTA_HORARIO_ATIVO = 41;
    public static final int ACAO_OBTER_LISTA_UTILIZACAO_HOJE = 42;
    public static final int ACAO_REGISTRO_ALARME_CONCLUIDO = 43;
    public static final int ACAO_ALTERAR_ALARME_CONCLUIDO = 44;
    public static final int ACAO_OBTER_LISTA_ALARME_HOJE = 45;
    public static final int ACAO_VOZ_COMANDOS_TELA_PRINCIPAL = 46;
    public static final int ACAO_VOZ_COMANDOS_DETALHE_MEDICAMENTO = 47;

    public static String[] intervalos = new String[] {
            "6 horas", "8 horas", "12 horas", "24 horas",
            "1 horas", "2 horas", "3 horas", "4 horas", "5 horas", "6 horas",
            "7 horas", "8 horas", "9 horas", "10 horas", "11 horas", "12 horas",
            "13 horas", "14 horas", "15 horas", "16 horas", "17 horas", "18 horas",
            "19 horas", "20 horas", "21 horas", "22 horas", "23 horas", "24 horas"};

}
