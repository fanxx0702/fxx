package com.fxx.code;

import com.fxx.parser.CodeVisitor;
import com.fxx.parser.RuleTestLexer;
import com.fxx.parser.RuleTestParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.security.acl.AclNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Code {

    private static List<Map<String, String>> source;

    public static void init() {
        source = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("port", "7");
        map1.put("address", "120");
        source.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("port", "7");
        map2.put("address", "12");

        source.add(map2);
    }

    public static void main(String[] args) {
        // 初始化数据源
        init();
        // 制定过滤的策略
        ANTLRInputStream inputStream = new ANTLRInputStream("( port = 7 or address = 12 ) and address = 120 ");
        RuleTestLexer ruleTestLexer = new RuleTestLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(ruleTestLexer);
        RuleTestParser ruleTestParser = new RuleTestParser(tokenStream);
        ParseTree tree = ruleTestParser.line();

        // 访问解析后的策略，输出匹配的结果
        CodeVisitor visitor = new CodeVisitor(source);
        Map<Map<String, String>, Boolean> result = (Map<Map<String, String>, Boolean>)visitor.visit(tree);
        System.out.println(result.toString());

    }
}
