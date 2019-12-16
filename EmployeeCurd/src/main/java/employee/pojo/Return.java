package employee.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工返回通用类
 */
public class Return {

    //    状态码{100:成功;200:失败}
    private Integer code;

    //    提示消息
    private String msg;

    //    返回的数据
    private Map<String, Object> data_return = new HashMap<>();

    public static Return success() {
        Return er = new Return();
        er.setCode(100);
        er.setMsg("处理成功！");
        return er;
    }

    public static Return error() {
        Return er = new Return();
        er.setCode(200);
        er.setMsg("处理失败...");
        return er;
    }

    public Return addData(String key, Object value) {

        this.getData_return().put(key, value);
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData_return() {
        return data_return;
    }

    public void setData_return(Map<String, Object> data_return) {
        this.data_return = data_return;
    }
}
