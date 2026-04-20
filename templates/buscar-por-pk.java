package br.com.lbi.projeto;

import java.math.BigDecimal;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.session.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;

/**
 * Template: buscar 1 registro por PK.
 *
 * Regras aplicadas:
 *  - JapeSession aberto em try/finally (cheatsheet 07)
 *  - PK pode ser simples (BigDecimal) ou composta (Object[]) — findByPK aceita ambos
 *  - Uso de asString/asBigDecimal (cheatsheet 02)
 */
public class BuscarPorPK {

    public static DynamicVO buscarParceiro(BigDecimal codparc) throws Exception {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();

            JapeWrapper dao = JapeFactory.dao("Parceiro"); // NOME_DAO — validar no DDL do cliente

            DynamicVO parc = dao.findByPK(codparc);
            if (parc == null) {
                return null;
            }

            String nome = parc.asString("NOMEPARC");
            BigDecimal cgc = parc.asBigDecimal("CGC_CPF");
            // ... usar os campos

            return parc;
        } finally {
            JapeSession.close(hnd);
        }
    }
}
