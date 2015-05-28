package jodk.decoratorpage.context;

import jodk.decoratorpage.context.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdekun on 15-5-27-下午4:48.
 */
public class BlockFile {
    private String path;
    private List<Block> blocks =new ArrayList<Block>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
    public Block getBlockByTag(String tag){
        for (Block b: blocks){
            if(tag.equals(b.getTag())){
                return b;
            }
        }
        return null;
    }
}
