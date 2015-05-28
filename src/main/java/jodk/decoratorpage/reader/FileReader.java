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
    public static String getAbsolutePath(String relativePath,File file){
        String[] relativeAry = relativePath.split("\\.\\./");
        String path = file.getAbsolutePath();
        File f = file;
        for(int i=0;i<relativeAry.length;i++){
            f = f.getParentFile();
            path =f.getAbsolutePath();
            if(i==relativeAry.length-1){
                path = path+File.separator+relativeAry[i];
            }
        }
        return path;
    }
}
