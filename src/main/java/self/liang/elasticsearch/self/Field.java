package self.liang.elasticsearch.self;

//为了灵活的定义文档结构，专门定义一个field类
public class Field {
    //列名
    public final String name;

    //列值
    public String fieldsData;

    public Field(String name) {
        this.name = name;
    }

}
