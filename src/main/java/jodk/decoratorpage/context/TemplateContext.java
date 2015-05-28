package jodk.decoratorpage.context;

import jodk.decoratorpage.reader.FileReader;
import jodk.decoratorpage.task.BuildDecoratoredFileTask;
import jodk.decoratorpage.task.BuildRealFileTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by zhangdekun on 15-5-27-上午9:44.
 */
public class TemplateContext {
    private String rootPath;
    private boolean isCache = true;
    private ForkJoinPool forkJoinPool = new ForkJoinPool();
    private List<ForkJoinTask<DecoratoredFile>> decoratoredFiles;

    /**
     * 是否需要做缓存
     *
     * @param isCache
     */
    public void cache(boolean isCache) {
        this.isCache = isCache;
    }

    public ForkJoinPool getForkJoinPool() {
        return forkJoinPool;
    }

    public List<ForkJoinTask<DecoratoredFile>> getDecoratoredFiles() {
        return decoratoredFiles;
    }


    public TemplateContext(String path) {
        this.rootPath = path;
        this.decoratoredFiles = new ArrayList<ForkJoinTask<DecoratoredFile>>();
    }

    public void init() throws Exception {
        List<File> files = FileReader.files(rootPath);
        for (File file : files) {
            decoratoredFiles.add(forkJoinPool.submit(new BuildDecoratoredFileTask(file, this)));
        }
    }

    public void build() throws Exception {
        List<ForkJoinTask<DecoratoredFile>> decoratoredFiles = getDecoratoredFiles();
        for (ForkJoinTask<DecoratoredFile> ft : decoratoredFiles) {
            forkJoinPool.submit(new BuildRealFileTask(ft.get()));
        }
    }

    public static void main(String[] args) {
        TemplateContext context = new TemplateContext("/home/supertool/IdeaProjects/decoratorpage/src/test/java/views");
        try {
            context.init();
            context.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}

