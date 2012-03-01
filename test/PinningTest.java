import java.util.List;

import org.junit.*;

import play.test.*;
import services.pinning.Pinning;

public class PinningTest extends UnitTest {

    @Test
    public void fetchImagesTest() {
	String url = "http://localhost";
	
	List<String> listImageUrls = Pinning.fetchImageLinks(url); 

	for (String imgLink : listImageUrls) {
	    System.out.println(imgLink);
	}
	assert(true);
    }

}