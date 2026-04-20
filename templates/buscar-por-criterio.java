package br.com.lbi.projeto;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.session.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;

/**
 * Template: buscar 1 ou N registros por filtro.
 *
 * Regras aplicadas:
 *  - findOne / find SEM "WHERE" no início e com Object[] (cheatsheet 03)
 *  - Campos de valor envoltos em ISNULL (cheatsheet 99)
 *  - JapeSession em try/finally (cheatsheet 07)
 */
public class BuscarPorCriterio {

    public static DynamicVO buscarParceiroPorCGC(String cgc) throws Exception {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            JapeWrapper dao = JapeFactory.dao("Parceiro");

            return dao.findOne("CGC_CPF = ?", new Object[]{cgc});
        } finally {
            JapeSession.close(hnd);
        }
    }

    public static Collection<DynamicVO> itensDisponiveis(BigDecimal codprod) throws Exception {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            JapeWrapper dao = JapeFactory.dao("Estoque"); // NOME_DAO — validar no DDL

            // Campos de valor (ESTOQUE, RESERVADO) protegidos com ISNULL (SQL Server)
            return dao.find(
                "CODPROD = ? AND ISNULL(ESTOQUE, 0) - ISNULL(RESERVADO, 0) > 0",
                new Object[]{codprod}
            );
        } finally {
            JapeSession.close(hnd);
        }
    }
}
