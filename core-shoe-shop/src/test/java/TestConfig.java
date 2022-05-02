import com.example.demo.core.DatabaseAdapter;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Configuration
@Profile("test")
@ComponentScan({"com.example.demo.facade", "com.example.demo.core"})
public class TestConfig {
    @Bean
    @Primary
    public DatabaseAdapter databaseAdapter() {
        return Mockito.mock(DatabaseAdapter.class);
    }
}
