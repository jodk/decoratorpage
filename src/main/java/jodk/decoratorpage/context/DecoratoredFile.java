package jodk.decoratorpage.context;

import jodk.decoratorpage.context.block.Block;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zhangdekun on 15-5-27-上午9:46.
 */
public class DecoratoredFile extends BlockFile {
    //文件是否需要模板
    public boolean DECORATORED = true;
    private static final String PRE = "dec_";

    private String templatePath;//模板路径

    private String desPath;//根据模板生成最终的文件地址

    private boolean isCache;
    private TemplateFile templateFile;


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

    public TemplateFile getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(TemplateFile templateFile) {
        this.templateFile = templateFile;
    }

    public void setPath(String path) {
        super.setPath(path);
        int split = super.getPath().lastIndexOf(File.separatorChar);
        this.desPath = super.getPath().substring(0, split + 1) + PRE + super.getPath().substring(split + 1);
    }

    public void toDestFile() throws IOException {
        if (!DECORATORED) return;
        File destFile = new File(this.desPath);
        if (!destFile.exists())
            destFile.createNewFile();
        FileWriter writer = new FileWriter(destFile,false);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        TemplateFile templateFile = Template.getTemplateMap().get(templatePath);
        for (Block tb : templateFile.getBlocks()) {
            String tag = tb.getTag();
            Block bb = null;
            if (tag == null || (bb = this.getBlockByTag(tag)) == null) {
                bufferedWriter.write(tb.getContent().toString());
            } else {
                bufferedWriter.write(bb.getContent().toString());
            }
        }
        bufferedWriter.close();
        writer.close();
    }
}
