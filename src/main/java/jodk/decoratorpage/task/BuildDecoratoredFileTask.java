package jodk.decoratorpage.task;

import jodk.decoratorpage.context.DecoratoredFile;
import jodk.decoratorpage.context.Template;
import jodk.decoratorpage.context.TemplateContext;
import jodk.decoratorpage.context.TemplateFile;
import jodk.decoratorpage.context.block.Block;
import jodk.decoratorpage.reader.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by zhangdekun on 15-5-27-上午10:32.
 */
public class BuildDecoratoredFileTask implements Callable<DecoratoredFile> {
    private final File file;
    private final TemplateContext context;
    private DecoratoredFile decoratoredFile = new DecoratoredFile();

    public BuildDecoratoredFileTask(File file, TemplateContext context) {
        this.file = file;
        this.context = context;
    }

    @Override
    public DecoratoredFile call() throws Exception {
        String path = file.getAbsolutePath();
        decoratoredFile.setPath(path);
        String line;
        java.io.FileReader reader = new java.io.FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        boolean isExtendLine = true;
        Block block = null;
        while ((line = br.readLine()) != null && decoratoredFile.DECORATORED) {
            if (line.trim().isEmpty()) {
                continue;
            }
            if (isExtendLine) {
                String templateRelativePath;
                if ((templateRelativePath = Template.isTExtend(line)) != null) {
                    String absolutePath = FileReader.getAbsolutePath(templateRelativePath, file);
                    decoratoredFile.setTemplatePath(absolutePath);
                    if (!Template.getTemplateMap().containsKey(absolutePath)) {
                        ForkJoinTask<TemplateFile> templateFileForkJoinTask = context.getForkJoinPool().submit(new BuildTemlateFileTask(absolutePath));
                        TemplateFile templateFile = templateFileForkJoinTask.join();
                        decoratoredFile.setTemplateFile(templateFile);
                        Template.getTemplateMap().put(absolutePath, templateFile);
                    }
                } else {
                    decoratoredFile.DECORATORED = false;
                }
                isExtendLine = false;
                continue;
            }
            block = Template.circleBlock(block, line);
            validBlock(block);
            if (block.isComplete()) {
                decoratoredFile.getBlocks().add(block);
                block = null;
            }
        }
        br.close();
        reader.close();
        return decoratoredFile;
    }

    private void validBlock(Block block) {
        if (block == null) return;
        String tag = block.getTag();
        if (tag != null) {
            if (Template.getTagMap().get(tag) == null)
                throw new RuntimeException("can not find this tag :" + block.getTag());
        }
    }
}
