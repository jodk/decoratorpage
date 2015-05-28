package jodk.decoratorpage.context.block;

/**
 * Created by zhangdekun on 15-5-27-下午1:45.
 */
public abstract class Block {
    public enum TYPE{
        TEMPLATE,ORIGIN,EXTEND;
    }
    private StringBuffer content = new StringBuffer();
    private String tag;
    private boolean isComplete = false;

    public Block(String tag){
        this.tag = tag;
    }
    public String getTag() {
        return tag;
    }

    public void append(String line){
        content.append(line);
    }
    public StringBuffer getContent() {
        return content;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public abstract  TYPE getType();
}
