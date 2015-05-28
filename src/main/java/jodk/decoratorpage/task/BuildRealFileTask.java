package jodk.decoratorpage.task;

import jodk.decoratorpage.context.DecoratoredFile;
import jodk.decoratorpage.context.TemplateContext;

import java.util.concurrent.Callable;

/**
 * Created by zhangdekun on 15-5-27-下午5:58.
 */
public class BuildRealFileTask implements Callable<String> {

    private final DecoratoredFile decoratoredFile;

    public BuildRealFileTask(DecoratoredFile decoratoredFile) {
        this.decoratoredFile = decoratoredFile;
    }

    @Override
    public String call() throws Exception {
        if (decoratoredFile.DECORATORED) {
            decoratoredFile.toDestFile();
            return decoratoredFile.getDesPath();
        } else {
            return decoratoredFile.getPath();
        }
    }

}
