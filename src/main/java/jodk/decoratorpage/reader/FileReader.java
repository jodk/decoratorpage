package jodk.decoratorpage.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdekun on 15-5-26-下午5:21.
 */
public class FileReader {
    /**
     * 查找这个路径下的所有文件
     * @param path
     * @return
     */
    public static List<File> files(String path){
        File file = new File(path);
        return files(file);
    }
    public static List<File> files(File file){
        List<File> files = new ArrayList<File>();
        if (file.exists()){
            if(file.isDirectory()){
                File[] dirs = file.listFiles();
                for(File df : dirs){
                    files.addAll(files(df));
                }
            }else {
                files.add(file);
            }
        }
        return  files;
    }

    public static void main(String[] args) {
        List<File> fs = files(FileReader.class.getResource("/").getPath());
        for(File f : fs){
            System.out.println(f.getAbsolutePath());
        }
    }
}
