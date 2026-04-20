package br.com.lbi.projeto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.session.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;

/**
 * Template: criar registro.
 *
 * Regras aplicadas:
 *  - Import de FluidCreateVO em br.com.sankhya.jape.wrapper.fluid (cheatsheet 06)
 *  - save() retorna o DynamicVO criado (diferente de update que é void — cheatsheet 05)
 *  - setProperty para alimentar campos antes do save (cheatsheet 01)
 *  - JapeSession em try/finally (cheatsheet 07)
 */
public class CriarRegistro {

    public static DynamicVO criarParceiro(String nome, String cgc) throws Exception {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            JapeWrapper dao = JapeFactory.dao("Parceiro");

            FluidCreateVO novo = dao.prepareToCreate();
            novo.set("NOMEPARC", nome);
            novo.set("CGC_CPF", cgc);
            novo.set("CLIENTE", "S");
            novo.set("ATIVO", "S");
            novo.set("DTCAD", new Timestamp(System.currentTimeMillis()));
            novo.set("AD_RODEMP", BigDecimal.ONE); // auditoria padrão Fabmed

            DynamicVO criado = novo.save();
            return criado;
        } finally {
            JapeSession.close(hnd);
        }
    }
}
