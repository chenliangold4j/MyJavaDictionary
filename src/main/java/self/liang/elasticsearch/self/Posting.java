package self.liang.elasticsearch.self;

import java.io.Serializable;

// 建立倒排索引用到的
public class Posting implements Serializable {


    public int docid; //文档编号
    public int freq; //这个词在文档中出现了多少次

    public Posting(int docid, int freq) {
        this.docid = docid;
        this.freq = freq;
    }
}
