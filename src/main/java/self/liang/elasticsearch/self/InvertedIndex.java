package self.liang.elasticsearch.self;

import java.util.*;

/**
 * 简单的倒排索引
 */
public class InvertedIndex {

    Map<String, List<Tuple>> index = new HashMap<>();

    //索引文档
    public void indexDoc(String docName, ArrayList<String> words) {
        int pos = 0;
        for (String word : words) {
            pos++;
            List<Tuple> idx = index.get(word);
            if (idx == null) {
                idx = new LinkedList<>();
                index.put(word, idx);
            }
            idx.add(new Tuple(docName, pos));
            System.out.println("indexed " + docName + " : " + pos + " words");
        }
    }

    //serach
    public void search(List<String> words) {
        for (String word : words) {
            Set<String> answer = new HashSet<>();
            List<Tuple> idx = index.get(word);
            if (idx != null) {
                for (Tuple t : idx) { //找到了一些文档
                    answer.add(t.docName);
                }
            }
            System.out.println(word);
            for (String f : answer) {
                System.out.println(" " + f);
            }
            System.out.println("");
        }
    }

    private class Tuple {  //< docName,position> metadata
        private String docName;//文档名
        private int position; //位置

        public Tuple(String docName, int position) {
            this.docName = docName;
            this.position = position;
        }
    }


}


