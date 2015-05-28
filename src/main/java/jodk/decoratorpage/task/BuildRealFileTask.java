package jodk.decoratorpage.task;

import jodk.decoratorpage.context.DecoratoredFile;
import jodk.decoratorpage.context.TemplateContext;

import java.util.concurrent.Callable;

/**
 * Created by zhangdekun on 15-5-27-下午5:58.
 */
public class BuildRealFileTask implements Callable<String> {

    private final DecoratoredFile decoratoredFile;
    private final TemplateContext context;
    public BuildRealFileTask(DecoratoredFile decoratoredFile,TemplateContext context) {
        this.decoratoredFile = decoratoredFile;
        this.context = context;
    }
    @Override
    public String call() throws Exception {
        String templateFilePath = decoratoredFile.getTemplatePath();

        return null;
    }

}
