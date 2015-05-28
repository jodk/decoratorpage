package jodk.decoratorpage;

import jodk.decoratorpage.context.TemplateContext;

import java.io.File;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String root = App.class.getResource("/").getPath();
        String path = root + "resources/views";
        System.out.println(path);
        TemplateContext context = new TemplateContext(path);
        try {
            context.init();
            context.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
