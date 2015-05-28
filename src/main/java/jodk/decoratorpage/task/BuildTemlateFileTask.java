package jodk.decoratorpage.task;

import jodk.decoratorpage.context.Template;
import jodk.decoratorpage.context.TemplateFile;
import jodk.decoratorpage.context.block.Block;
import jodk.decoratorpage.reader.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.util.concurrent.Callable;

/**
 * Created by zhangdekun on 15-5-27-下午4:52.
 */
public class BuildTemlateFileTask implements Callable<TemplateFile> {
    private final String path;
    private TemplateFile templateFile = new TemplateFile();

    public BuildTemlateFileTask(String path) {
        this.path = path;
    }

    @Override
    public TemplateFile call() throws Exception {
        templateFile.setPath(path);
        File file = FileReader.files(path).get(0);
        java.io.FileReader reader = new java.io.FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        Block block = null;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            block = Template.circleBlock(block, line);
            if (block.isComplete()) {
                templateFile.getBlocks().add(block);
                block = null;
            }
        }
        return templateFile;
    }
}
