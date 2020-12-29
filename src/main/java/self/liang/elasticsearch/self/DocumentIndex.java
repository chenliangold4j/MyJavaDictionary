package self.liang.elasticsearch.self;

import java.util.HashMap;

//首先建立文档索引，也就是正排索引
public class DocumentIndex {

    public int docid;// 文档编号
    //词到频率的映射，频率存在的长度是1的整数数组中
    HashMap<String, int[]> frequencyList;
    int words; //文档长度，也就是这个文档包含多少个词
}
