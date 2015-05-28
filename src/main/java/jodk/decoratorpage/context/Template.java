package jodk.decoratorpage.context;

import jodk.decoratorpage.context.block.Block;
import jodk.decoratorpage.context.block.BlockFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    public static final String T_START = "{%%}";
    public static final String T_END = "{%end%}";
    public static final String T_EXTEND = "t-e:";
    public static final String T_BLOCK = "t-b:";
    /**
     * 模板文件占用资源一般不会太多
     * circle life is global
     * key:path,value:block list
     */
    private final static Map<String, List<TemplateFile>> templateMap = new ConcurrentHashMap<String, List<TemplateFile>>();

    public static boolean isTend(String line){
        //TODO
        return true;
    }
    public static String isTExtend(String content) {
        //TODO
        return null;
    }

    public static boolean isTBlock(String content) {
        //TODO
        return true;
    }
    public static Block circleBlock(Block block,String line){
        if(block==null){
            block = BlockFactory.get(line);
        }else if(isTend(line)){
            block.setComplete(true);
        }else {
            block.append(line);
        }
        return block;
    }
}
