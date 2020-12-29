package self.liang.elasticsearch.self;

public class Page {

    byte[] data;

    public Page(byte[] data) {
        this.data = data;
    }

    /**
     * 返回数据的字节数组
     */

    public byte[] getpage() {
        return data;
    }

    /**
     * 用给定的字节数组设置页面
     */
    public void setpage(byte[] array) {
        data = array;
    }

    public byte[] getData() {
        return this.data;
    }

}
