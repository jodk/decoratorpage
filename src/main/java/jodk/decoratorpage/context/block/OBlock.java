package jodk.decoratorpage.context.block;

/**
 * Created by zhangdekun on 15-5-27-下午1:45.
 */

/**
 * 原始内容
 */
public class OBlock extends Block{
    public OBlock(String tag) {
        super(tag);
    }

    @Override
    public TYPE getType() {
        return TYPE.ORIGIN;

    }
}
