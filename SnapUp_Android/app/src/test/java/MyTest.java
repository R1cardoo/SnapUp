import com.example.snapup_android.pojo.User;
import com.example.snapup_android.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void TestUser(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl userService = (UserServiceImpl) ctx.getBean("userServiceImpl");

        System.out.println(userService.isRegistered("rex1"));
        userService.registerUser("rex1", "110102199909223017", 'M', "王睿浦",
                "15201657270", "rexwoodrp@gmail.com", "123456", "wrp");
        System.out.println(userService.isRegistered("rex1"));
        System.out.println(userService.checkPassword("rex1", "123456"));
        System.out.println(userService.getUserInstance("rex1", "123456").getIdentity());
        System.out.println(userService.getPwd("rex1"));

        System.out.println(userService.updateUserInfo(new User("rex1", "110102199909223017", 'M', "王睿浦", "18301327275", "rexwoodrp@gmail.com", "123456", "wrp")));
    }
}
