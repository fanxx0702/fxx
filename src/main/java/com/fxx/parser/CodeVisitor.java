package com.fxx.parser;

import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fanxiaoxu
 * @date 2020-02-20
 */
public class CodeVisitor extends RuleTestBaseVisitor{

    private List<Map<String,String>> source;
    public CodeVisitor (List<Map<String,String>> source) {
        this.source = source;
    }

    /**
     * and 操作
     * @param ctx
     * @return
     */
    @Override
    public Map<Map<String, String>, Boolean> visitAndExpr(RuleTestParser.AndExprContext ctx) {
        Map<Map<String, String>, Boolean> result = new HashMap<>();
        RuleTestParser.ExprContext left = ctx.expr(0);
        RuleTestParser.ExprContext right = ctx.expr(1);
        Map<Map<String, String>, Boolean> leftResult = (Map<Map<String, String>, Boolean>)visit(left);
        Map<Map<String, String>, Boolean> rightResult = (Map<Map<String, String>, Boolean>)visit(right);
        leftResult.keySet().forEach( leftE -> {
            result.put(leftE, leftResult.get(leftE).booleanValue() && rightResult.get(leftE).booleanValue());
        });
        return result;
    }

    /**
     * or 操作
     * @param ctx
     * @return
     */
    @Override
    public Map<Map<String, String>, Boolean> visitOrExpr(RuleTestParser.OrExprContext ctx) {
        Map<Map<String, String>, Boolean> result = new HashMap<>();
        RuleTestParser.ExprContext left = ctx.expr(0);
        RuleTestParser.ExprContext right = ctx.expr(1);
        Map<Map<String, String>, Boolean> leftResult = (Map<Map<String, String>, Boolean>)visit(left);
        Map<Map<String, String>, Boolean> rightResult = (Map<Map<String, String>, Boolean>)visit(right);
        leftResult.keySet().forEach( leftE -> {
            result.put(leftE, leftResult.get(leftE).booleanValue() || rightResult.get(leftE).booleanValue());
        });
        return result;
    }

    /**
     * common
     * @param ctx
     * @return
     */
    @Override
    public Map<Map<String, String>, Boolean> visitCommom(RuleTestParser.CommomContext ctx) {
        Map<Map<String, String>, Boolean> result = new HashMap<>();
        String col = ctx.COL().getSymbol().getText();
        String value = ctx.VALUE().getSymbol().getText();
        source.forEach(e -> {
            if (e.containsKey(col) && e.get(col).equals(value)) {
                result.put(e, Boolean.TRUE);
            }else {
                result.put(e, Boolean.FALSE);
            }
        });
        return result;
    }

    @Override
    public Map<Map<String, String>, Boolean> visitParenExpr(RuleTestParser.ParenExprContext ctx) {
        return (Map<Map<String, String>, Boolean>)visit(ctx.expr());
    }
}
