package jodk.decoratorpage.context.block;

/**
 * Created by zhangdekun on 15-5-27-下午1:52.
 */

/**
 * 模板继承内容
 */
public class EBlock extends Block {
    public EBlock(String tag) {
        super(tag);
    }
    @Override
    public TYPE getType() {
        return TYPE.EXTEND;
    }
}
