package jodk.decoratorpage.context;

import java.io.File;

/**
 * Created by zhangdekun on 15-5-27-上午9:46.
 */
public class DecoratoredFile extends BlockFile{
    //文件是否需要模板
    public boolean DECORATORED = true;
    private static final String PRE = "dec_";

    private String templatePath;//模板路径

    private String desPath;//根据模板生成最终的文件地址

    private boolean isCache;

    public String getTemplatePath() {
        return templatePath;
    }


    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean isCache) {
        this.isCache = isCache;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getDesPath() {
        return desPath;
    }


    public void setPath(String path) {
        super.setPath(path);
        int split = super.getPath().lastIndexOf(File.separatorChar);
        this.desPath = super.getPath().substring(0, split + 1) + PRE + super.getPath().substring(split + 1);
    }

    public void toDestFile() {
        //TODO
    }
}
