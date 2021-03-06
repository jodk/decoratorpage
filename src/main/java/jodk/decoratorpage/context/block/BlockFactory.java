package jodk.decoratorpage.context.block;

import jodk.decoratorpage.context.Template;

/**
 * Created by zhangdekun on 15-5-27-下午3:16.
 */
public class BlockFactory {
    /**
     * 根据内容确定创建什么样的block
     * @param content
     * @return
     */
    public static Block get(String content){
        if(Template.isTExtend(content)!=null){
            return new EBlock(Template.TReg.extend(content));
        }else if(Template.isTBlock(content)){
            return new TBlock(Template.TReg.block(content));
        }else {
            Block block = new OBlock(null);
            block.append(content+"\n");
            return block;
        }
    }
}
