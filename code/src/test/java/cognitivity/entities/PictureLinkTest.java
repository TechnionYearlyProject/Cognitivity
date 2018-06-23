package cognitivity.entities;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by peers on 23/06/2018.
 */
public class PictureLinkTest {

    @Test
    public void TestAll() {
        PictureLink link1 = new PictureLink();
        PictureLink link2 = new PictureLink("link","name");
        link1.setLink("lnk");
        link1.setName("nm");
        assertEquals("Problem with setting/getting links",link1.getLink(),"lnk");
        assertEquals("Problem with setting/getting name",link1.getName(),"nm");
    }
}