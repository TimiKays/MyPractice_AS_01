package com.tangqi.service;

import android.util.Xml;

import com.tangqi.domain.News;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析XML的业务方法
 *
 * @author TangBaoBao
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class XmlParserUtils {

    private static ArrayList<News> sNewsList;
    private static News sNews;

    public static List<News> converse(InputStream is) throws Exception {

        //[1]获取解析器的实例
        XmlPullParser parser = Xml.newPullParser();
        //[2]设置解析器的参数
        parser.setInput(is, "utf-8");
        //[3]获取事件类型
        int type = parser.getEventType();
        //[4]判断事件类型，做出相关行为。
        //[4.1]是否文档结尾，不是就一直读下去,是就结束读取
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                //[4.2]如果是文档开头，就创建list对象
                case XmlPullParser.START_DOCUMENT:
                    sNewsList = new ArrayList<News>();
                    break;
                //[4.3]如果是开始标签，则继续判断是哪种开始标签
                case XmlPullParser.START_TAG:
                    //[4.4]如果是item，就创建对象
                    if ("item".equals(parser.getName())) {
                        sNews = new News();
                        //[4.5]如果是参数的标签，则为对象设置参数
                    } else if ("title".equals(parser.getName())) {
                        sNews.setTitle(parser.nextText());
                    } else if ("image".equals(parser.getName())) {
                        sNews.setImage(parser.nextText());
                    } else if ("link".equals(parser.getName())) {
                        sNews.setLink(parser.nextText());
                    } else if ("author".equals(parser.getName())) {
                        sNews.setAuthor(parser.nextText());
                    } else if ("pubDate".equals(parser.getName())) {
                        sNews.setPubDate(parser.nextText());
                    } else if ("description".equals(parser.getName())) {
                        sNews.setDescription(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    //[4.6]如果结束标签是item，就把对象放到集合中
                    if ("item".equals(parser.getName())) {
                        sNewsList.add(sNews);
                    }
            }
            //表示读取下一个标签
            type = parser.next();
        }

        //返回list对象
        return sNewsList;
    }
}
