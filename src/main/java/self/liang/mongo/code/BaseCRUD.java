package self.liang.mongo.code;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class BaseCRUD {

    public static void main(String[] args) {
        MongoCollection<Document> collection = MongoDBUtil.getConnect().getCollection("user");


        //1创建文档对象。
        Document document = new Document("name","张三")
                .append("sex", "男")
                .append("age", 18);

        //插入文档
        collection.insertOne(document);
        //插入文档 多个
        List<Document> list = new ArrayList<>();
        for(int i = 1; i <= 3; i++) {
            document = new Document("name", "张三"+i)
                    .append("sex", "男")
                    .append("age", 18);
            list.add(document);
        }
        //插入多个文档
        collection.insertMany(list);

//        //删除
        Bson filter = Filters.eq("age",18);
//        //删除与筛选器匹配的单个文档
//        collection.deleteOne(filter);
//        //删除与筛选器匹配的所有文档
//        collection.deleteMany(filter);


        filter = Filters.eq("name", "张三");
        //指定修改的更新文档
        document = new Document("$set", new Document("age", 100));
        //修改单个文档
        collection.updateOne(filter, document);

        //修改多个文档
        collection.updateMany(filter, document);

        //查询
        FindIterable findIterable = collection.find();
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

        //指定查询过滤器
         filter = Filters.eq("name", "张三");
        //指定查询过滤器查询
        findIterable = collection.find(filter);
        cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }



    }

}
