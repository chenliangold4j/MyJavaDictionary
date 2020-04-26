package self.liang.mongo.code;


import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class BaseConnect {
    public static void main(String[] args) {
        //创建连接
//        MongoCredential credential = MongoCredential.createCredential("root", "test", "123456".toCharArray());
//        MongoClient mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), Arrays.asList(credential));
        MongoClient mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017));
        System.out.println("Connect to database successfully");
        MongoDatabase database = mongoClient.getDatabase("test");//获取数据库
        MongoCollection<Document> collection = database.getCollection("user");//集合名

        FindIterable<Document> users =  collection.find();
        for(Document document : users){
            System.out.println(document);
        }
    }
}
