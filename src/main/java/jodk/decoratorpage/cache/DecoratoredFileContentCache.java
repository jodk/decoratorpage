package jodk.decoratorpage.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangdekun on 15-5-27-下午3:46.
 */
public class DecoratoredFileContentCache {
    /**
     * key:在装饰之前的文件路径
     * value:装饰之后的文件内容
     */
    private static Map<String,String> cacheMap = new HashMap<String, String>();

}
