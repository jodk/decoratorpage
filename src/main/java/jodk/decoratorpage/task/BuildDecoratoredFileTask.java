package jodk.decoratorpage.task;

import jodk.decoratorpage.context.DecoratoredFile;
import jodk.decoratorpage.context.Template;
import jodk.decoratorpage.context.TemplateContext;
import jodk.decoratorpage.context.block.Block;
import jodk.decoratorpage.reader.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.util.concurrent.Callable;

/**
 * Created by zhangdekun on 15-5-27-上午10:32.
 */
public class BuildDecoratoredFileTask implements Callable<DecoratoredFile> {
    private final File file;
    private final TemplateContext context;
    private DecoratoredFile decoratoredFile = new DecoratoredFile();

    public BuildDecoratoredFileTask(File file,TemplateContext context) {
        this.file = file;
        this.context = context;
    }

    @Override
    public DecoratoredFile call() throws Exception {
        String path = file.getAbsolutePath();
        decoratoredFile.setPath(path);

        String line=null;
        java.io.FileReader reader = new java.io.FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        boolean isExtendLine = true;
        Block block = null;
        while ((line=br.readLine())!=null && decoratoredFile.DECORATORED){
            if(line.trim().isEmpty()){
                continue;
            }
            if(isExtendLine){
                String templateRelativePath ;
                if((templateRelativePath = Template.isTExtend(line))!=null){
                    String absolutePath = FileReader.getAbsolutePath(templateRelativePath,file);
                    decoratoredFile.setTemplatePath(absolutePath);
                    if(!context.getTemplateFiles().containsKey(absolutePath)){
                        context.getTemplateFiles().put(absolutePath,context.getForkJoinPool().submit(new BuildTemlateFileTask(absolutePath)));
                    }
                }else {
                    decoratoredFile.DECORATORED = false;
                }
                isExtendLine = false;
            }
            block = Template.circleBlock(block,line);
            if(block.isComplete()){
                decoratoredFile.getBlocks().add(block);
                block = null;
            }
        }
        br.close();
        reader.close();
        return decoratoredFile;
    }
}
