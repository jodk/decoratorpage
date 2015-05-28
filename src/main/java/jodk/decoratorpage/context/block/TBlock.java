package jodk.decoratorpage.context.block;

/**
 * Created by zhangdekun on 15-5-27-下午1:45.
 */

/**
 * 模板内部的内容

 */
public class TBlock extends Block{
    public TBlock(String tag) {
        super(tag);
    }

    @Override
    public TYPE getType() {
        return TYPE.TEMPLATE;
    }
}
