package self.liang.dynamic.proxy.example;

public class UserMananger  implements  IUserManager{

    @Override
    public void addUser(Integer id, String name) {
        System.out.println("add"+id+":"+name);
    }

    @Override
    public void delUser(Integer id) {
        System.out.println("del"+id);
    }
}
