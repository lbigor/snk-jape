package br.com.lbi.projeto;

import java.math.BigDecimal;

import br.com.sankhya.jape.session.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;

/**
 * Template: atualizar registro por PK.
 *
 * Regras aplicadas:
 *  - prepareToUpdateByPK recebe Object[] (cheatsheet 04), mesmo para PK simples
 *  - FluidUpdateVO em br.com.sankhya.jape.wrapper.fluid (cheatsheet 06)
 *  - update() retorna void — reler via findByPK se precisar do VO (cheatsheet 05)
 *  - JapeSession em try/finally (cheatsheet 07)
 */
public class AtualizarRegistro {

    public static void renomearParceiro(BigDecimal codparc, String novoNome) throws Exception {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            JapeWrapper dao = JapeFactory.dao("Parceiro");

            FluidUpdateVO upd = dao.prepareToUpdateByPK(new Object[]{codparc});
            upd.set("NOMEPARC", novoNome);
            upd.update(); // void

            // Se precisa do VO atualizado:
            DynamicVO atualizado = dao.findByPK(codparc);
        } finally {
            JapeSession.close(hnd);
        }
    }

    /**
     * Atualização em PK composta (ex.: TGFITE — NUNOTA + SEQUENCIA).
     */
    public static void atualizarItem(BigDecimal nunota, BigDecimal sequencia, BigDecimal novaQtd) throws Exception {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            JapeWrapper dao = JapeFactory.dao("ItemNota");

            dao.prepareToUpdateByPK(new Object[]{nunota, sequencia})
               .set("QTDNEG", novaQtd)
               .update();
        } finally {
            JapeSession.close(hnd);
        }
    }
}
