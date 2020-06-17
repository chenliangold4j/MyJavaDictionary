package self.liang.execel.exx;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author Jiaju Zhuang
 **/
@Data
@ToString
public class DemoData {

    @ExcelProperty(value = { "地区"})
    private String area;

    @ExcelProperty(value = { "url"})
    private String url;

    @ExcelProperty(value = { "详细"},index = 1)
    private String detail;
}
