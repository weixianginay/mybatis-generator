package com.lwl.generator.plugins;

import java.util.Iterator;
import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.ibatis2.Ibatis2FormattingUtilities;

public class LikeAFPlugin extends PluginAdapter {
    public LikeAFPlugin() {
    }

    public boolean validate(List<String> warnings) {
        return true;
    }

    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        InnerClass criteria = null;
        Iterator var4 = topLevelClass.getInnerClasses().iterator();

        while(var4.hasNext()) {
            InnerClass introspectedColumn1 = (InnerClass)var4.next();
            if("GeneratedCriteria".equals(introspectedColumn1.getType().getShortName())) {
                criteria = introspectedColumn1;
                break;
            }
        }

        if(criteria == null) {
            return true;
        } else {
            var4 = introspectedTable.getNonBLOBColumns().iterator();

            while(var4.hasNext()) {
                IntrospectedColumn introspectedColumn11 = (IntrospectedColumn)var4.next();
                if(introspectedColumn11.isJdbcCharacterColumn() && introspectedColumn11.isStringColumn()) {
                    Method method = new Method();
                    method.setVisibility(JavaVisibility.PUBLIC);
                    method.addParameter(new Parameter(introspectedColumn11.getFullyQualifiedJavaType(), "value"));
                    StringBuilder sb = new StringBuilder();
                    sb.append(introspectedColumn11.getJavaProperty());
                    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                    sb.insert(0, "and");
                    sb.append("Like");
                    method.setName(sb.toString() + "AF");
                    method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
                    sb.setLength(0);
                    method.addBodyLine("if(value == null) {");
                    method.addBodyLine("addCriterion(\""+introspectedColumn11.getActualColumnName()+" is null\");");
                    method.addBodyLine("return (Criteria) this;");
                    method.addBodyLine("}");
                    method.addBodyLine("if(\"\".equals(value.trim())) {");
                    method.addBodyLine("value = \"5B6FF2E7CA2F4497A28A38DEBECEF760\";");
                    method.addBodyLine("}");
                    method.addBodyLine("else {");
                    method.addBodyLine("value = \"%\" + value.replace(\"\\\\\", \"\\\\\\\\\").replace(\"_\", \"\\\\_\").replace(\"%\", \"\\\\%\") + \"%\";");
                    method.addBodyLine("}");
                    sb.append("addCriterion(\"");
                    sb.append(Ibatis2FormattingUtilities.getAliasedActualColumnName(introspectedColumn11));
                    sb.append(" like\", value, \"");
                    sb.append(introspectedColumn11.getJavaProperty());
                    sb.append("\");");
                    method.addBodyLine(sb.toString());
                    method.addBodyLine("return (Criteria) this;");
                    criteria.addMethod(method);
                }
            }

            return true;
        }
    }
}