package com.lwl.generator.plugins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;

import java.util.Iterator;
import java.util.List;

public class PaginationPlugin extends PluginAdapter {
	public PaginationPlugin() {
	}

	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		this.addPageUtil(topLevelClass, introspectedTable, "pageUtil");
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	private void printElement(Element element, int deep) {
		if(element instanceof XmlElement) {
			XmlElement element2 = (XmlElement)element;
			++deep;
			Iterator i$ = element2.getElements().iterator();

			while(i$.hasNext()) {
				Element c = (Element)i$.next();
				this.printElement(c, deep);
			}

			--deep;
		}

	}

	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement isNotNullElement = new XmlElement("if");
		isNotNullElement.addAttribute(new Attribute("test", "pageUtil != null"));
		isNotNullElement.addElement(new TextElement("limit ${pageUtil.start} , ${pageUtil.pageSize}"));
		element.addElement(isNotNullElement);
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		return this.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	private void addPageUtil(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.lwl.api.provider.common.utils.PageUtil"));
		CommentGenerator commentGenerator = this.context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(new FullyQualifiedJavaType("com.lwl.api.provider.common.utils.PageUtil"));
		field.setName(name);
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(new FullyQualifiedJavaType("com.lwl.api.provider.common.utils.PageUtil"), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("com.lwl.api.provider.common.utils.PageUtil"));
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	public boolean validate(List<String> warnings) {
		return true;
	}

	public static void generate() {
		String config = PaginationPlugin.class.getClassLoader().getResource("generatorConfig.xml").getFile();
		String[] arg = new String[]{"-configfile", config, "-overwrite"};
		ShellRunner.main(arg);
	}

	public static void main(String[] args) {
		generate();
	}
}
