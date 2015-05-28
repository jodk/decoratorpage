package jodk.decoratorpage.context;

import jodk.decoratorpage.context.block.Block;
import jodk.decoratorpage.context.block.BlockFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangdekun on 15-5-27-上午9:45.
 */
public final class Template {
    /**
     * 约束：
     * 1、模板标签(起始与结束)各占用一行
     * 2、模板标签不可嵌套
     * 3、标签如：{%t-b:body%}{%end%},{%t-e:main.html%}
     * 4、继承模板必须在第一行(非空行)
     */
    /**
     * 模板文件占用资源一般不会太多
     * circle life is global
     * key:path,value:TemplateFile
     */
    private final static Map<String, TemplateFile> templateMap = new ConcurrentHashMap<String, TemplateFile>();

    /**
     * 模板中使用的所有标签
     */
    private final static Map<String,String> tagMap = new ConcurrentHashMap<String, String>();

    public static Map<String, String> getTagMap() {
        return tagMap;
    }

    public static Map<String, TemplateFile> getTemplateMap() {
        return templateMap;
    }

    public static boolean isTend(String line) {
        return TReg.endExist(line);
    }

    /**
     * 返回模板的路径
     *
     * @param content
     * @return
     */
    public static String isTExtend(String content) {
        return TReg.extend(content);
    }

    public static boolean isTBlock(String content) {
        return TReg.blockExist(content);
    }

    public static boolean isContent(String content) {
        return !(isTend(content) || isTExtend(content) != null || isTBlock(content));
    }

    public static Block circleBlock(Block block, String line) {
        if (block == null) {
            block = BlockFactory.get(line);
        } else if (isTend(line)) {
            block.setComplete(true);
        } else {
            if (isContent(line))
                block.append(line+"\n");
        }
        return block;
    }

    /**
     * 模板正则
     */
    public static class TReg {
        public static final String T_END = "\\s*\\{%end%\\}\\s*";
        public static final String T_EXTEND = "\\s*\\{%t-e:.+%\\}\\s*";
        public static final String T_BLOCK = "\\s*\\{%t-b:.+%\\}\\s*";
        private static Pattern endPattern = Pattern.compile(T_END);
        private static Pattern extendPattern = Pattern.compile(T_EXTEND);
        private static Pattern blockPattern = Pattern.compile(T_BLOCK);

        private static boolean exist(Pattern pattern, String content) {
            return pattern.matcher(content).matches();
        }

        private static String find(Pattern pattern, String content) {
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                return matcher.group().replaceAll("\\{%", "").replaceAll("%\\}", "").replaceAll("t-e:", "").replaceAll("t-b:", "").trim();
            }
            return null;
        }

        public static boolean endExist(String content) {
            return exist(endPattern, content);
        }

        public static boolean extendExist(String content) {
            return exist(extendPattern, content);
        }

        public static boolean blockExist(String content) {
            return exist(blockPattern, content);
        }

        public static String extend(String content) {
            return find(extendPattern, content);
        }

        public static String block(String content) throws RuntimeException{
            return  find(blockPattern, content);
        }
    }
}

