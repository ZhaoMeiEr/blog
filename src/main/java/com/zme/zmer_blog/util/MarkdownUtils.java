package com.zme.zmer_blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * @authon timber
 * @description Markdown文本转换成Html格式工具类
 * @date 2020/2/17 上午12:59
 */
public class MarkdownUtils {

    /**
     * @Author: timber 
     * @Description: markdown格式文本转换成Html格式
     * @Date: 2020/2/17 上午1:20
     * @param markdown
     * @Return  
     */
    public static String markdownToHtml(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * @Author: timber 
     * @Description: 增加扩展(标题描点，表格生成) markdown格式文本转换成Html格式
     * @Date: 2020/2/17 上午1:21
     * @param markdown
     * @Return  
     */
    public static String markdownToHtmlExtensions(String markdown){
        // h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        // 转换table的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(tableExtension).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomAttributeProvider();
                    }
                }).build();
        return renderer.render(document);
    }

    /**
     * @Author: timber 
     * @Description:  处理标签的属性
     * @Date: 2020/2/17 上午1:22
     */
    static class CustomAttributeProvider implements AttributeProvider{
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            // 改变a标签的属性为_blank
            if(node instanceof Link){
                attributes.put("target", "_blank");
            }
            // 给table添加class
            if(node instanceof TableBlock){
                attributes.put("class", "ui celled table");
            }
        }
    }

    public static void main(String[] args) {
        String table = "| hello | hi   | 哈哈哈   |\n" +
                "| ----- | ---- | ----- |\n" +
                "| 斯维尔多  | 士大夫  | f啊    |\n" +
                "| 阿什顿发  | 非固定杆 | 撒阿什顿发 |\n" +
                "\n";
        String a = "[imCoding 爱编程](http://www.lirenmi.cn)";
        System.out.println(markdownToHtmlExtensions(a));
    }
}
