package self.liang.elasticsearch.self;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 一个索引应该实现的功能模板
 */
public interface Index {

    /**
     * 使用数据库构建索引
     */
    public void build(DataSource dataSource);


    /**
     * 从文件加载倒排索引
     */
    public void read(String filename) throws IOException;


    /**
     * 内存索引存入文件
     */
    public void flush(String filename) throws IOException;

}
