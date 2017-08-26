package module;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 * 以实体方式对应docx文档中的变量
 */
public class Project {

    private String name;

    private List<Developer> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Developer> getList() {
        return list;
    }

    public void setList(List<Developer> list) {
        this.list = list;
    }

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }
}
