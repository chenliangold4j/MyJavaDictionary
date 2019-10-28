package self.liang.mybatis.example.base;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 之后需要定义全局配置文件的typeHandler
 *  <typeHandler handler="self.liang.mybatis.example.base.MyEnumEmpStatusTypeHandler" javaType="self.liang.mybatis.example.base.EnumEmps"/>
 */
public class MyEnumEmpStatusTypeHandler implements TypeHandler<EnumEmps> {

    /**
     * 定义当前数据如何保存
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */

    @Override
    public void setParameter(PreparedStatement ps, int i, EnumEmps parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter.getCode().toString());
    }

    @Override
    public EnumEmps getResult(ResultSet rs, String columnName) throws SQLException {
        //根据拿到的状态码，返回枚举对象
        int code =  rs.getInt(columnName);
        return EnumEmps.getByCode(code);
    }

    @Override
    public EnumEmps getResult(ResultSet rs, int columnIndex) throws SQLException {

        int code =  rs.getInt(columnIndex);
        return EnumEmps.getByCode(code);
    }

    @Override
    public EnumEmps getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code =  cs.getInt(columnIndex);
        return EnumEmps.getByCode(code);
    }
}
