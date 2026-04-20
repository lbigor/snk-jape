package br.com.lbi.projeto;

import java.math.BigDecimal;

import br.com.sankhya.jape.session.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;

/**
 * Template: deletar registro.
 *
 * Regras aplicadas:
 *  - JapeSession em try/finally (cheatsheet 07)
 *  - deleteByPK aceita Object[] — mesma regra do update (cheatsheet 04)
 *  - Preferir deletar via findByPK + deleteEntity quando a entidade tem triggers/eventos
 *    que precisam do contexto do VO.
 */
public class DeletarRegistro {

    public static void deletarParceiro(BigDecimal codparc) throws Exception {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            JapeWrapper dao = JapeFactory.dao("Parceiro");

            dao.deleteByPK(new Object[]{codparc});
        } finally {
            JapeSession.close(hnd);
        }
    }

    /**
     * Deletar filtrando via critério (sem "WHERE", com Object[] — cheatsheet 03).
     * Use com cautela: remove todos os registros que baterem no predicado.
     */
    public static void deletarInativosAntigos(BigDecimal anos) throws Exception {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            JapeWrapper dao = JapeFactory.dao("Parceiro");

            // Carrega, itera e deleta — padrão seguro quando há triggers.
            for (DynamicVO p : dao.find(
                    "ATIVO = 'N' AND DTCAD < DATEADD(YEAR, -?, GETDATE())",
                    new Object[]{anos})) {
                dao.deleteByPK(new Object[]{p.asBigDecimal("CODPARC")});
            }
        } finally {
            JapeSession.close(hnd);
        }
    }
}
