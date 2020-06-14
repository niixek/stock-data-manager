import com.mongodb.MongoClient;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class mongoTest {
    @Test
    //throws Exception
    public void shouldCreateANewMongoClientConnectedToLocalhost() {
        MongoClient mongoClient = null;

        //assertThat(mongoClient, is(notNullValue()));
    }
}
