import org.fullstack.ReferenceApplication;
import org.fullstack.service.RpcService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = ReferenceApplication.class)
public class RpcTest {

    @Autowired
    private RpcService rpcService;

    @Test
    public void test2() {
        List<String> info = rpcService.getInfo(3);
        info.forEach(System.out::println);

        System.out.println(rpcService.echo("hello"));
        rpcService.test();
    }
}
