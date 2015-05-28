package jodk.decoratorpage.context;

import jodk.decoratorpage.reader.FileReader;
import jodk.decoratorpage.task.BuildDecoratoredFileTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by zhangdekun on 15-5-27-上午9:44.
 */
public class TemplateContext {
    private String rootPath;
    private boolean isCache = true;
    private ForkJoinPool forkJoinPool = new ForkJoinPool();
    private List<ForkJoinTask<DecoratoredFile>> decoratoredFiles;
    private Map<String,ForkJoinTask<TemplateFile>> templateFiles;

    /**
     * 是否需要做缓存
     * @param isCache
     */
    public void cache(boolean isCache){
        this.isCache = isCache;
    }

    public ForkJoinPool getForkJoinPool() {
        return forkJoinPool;
    }

    public List<ForkJoinTask<DecoratoredFile>> getDecoratoredFiles() {
        return decoratoredFiles;
    }

    public Map<String,ForkJoinTask<TemplateFile>> getTemplateFiles() {
        return templateFiles;
    }

    public TemplateContext(String path) {
        this.rootPath = path;
        this.decoratoredFiles = new ArrayList<ForkJoinTask<DecoratoredFile>>();
        this.templateFiles = new ConcurrentHashMap<String, ForkJoinTask<TemplateFile>>();
    }
    public void init() throws Exception{
        List<File> files = FileReader.files(rootPath);
        for(File file : files){
           decoratoredFiles.add(forkJoinPool.submit(new BuildDecoratoredFileTask(file, this)));
        }
        forkJoinPool.shutdown();
    }
    public void build(){

    }
    public static void main(String[] args) throws Exception{
        TemplateContext context= new TemplateContext("/home/supertool/Workspace/eclipse/unp/src/main/webapp/WEB-INF");
        context.init();
        /**
        List<ForkJoinTask<DecoratoredFile>> list = context.getDecoratoredFiles();
        for(ForkJoinTask<DecoratoredFile> file:list){
            System.out.println(file.join().getPath());
        }

        System.out.println("file count : "+list.size());
         */
    }
}

