package self.liang.leetcode.base.unionfind;

/**
 * 并查集，解决两点的链接问题。
 */
public class UnionFind1 {
    private int[] id;
    private int count;

    UnionFind1(int n) {
        this.count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    int find(int p) {
        if (p < 0 || p >= count) return -1;
        return id[p];
    }

    boolean isConnected(int p,int q){
        return find(p) == find(q);
    }

    void unionElements(int p,int q){
        int pID = find(p);
        int qID = find(q);

        if(pID == qID)return;

        for(int i = 0;i<count;i++){
            if(id[i] == pID)id[i] = qID;
        }
    }


}
