package test;

import com.example.demo.facade.ShoeShopFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.NotSupportedException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class ShoeFacadeTest {

    @Autowired
    private ShoeShopFacade shoeShopFacade;

    @Test
    void unsupportedVersion() {
        assertThrows(NotSupportedException.class, () -> shoeShopFacade.get(1));
    }

}
