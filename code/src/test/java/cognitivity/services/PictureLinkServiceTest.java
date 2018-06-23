package cognitivity.services;

import cognitivity.dao.PictureLinkDAO;
import cognitivity.entities.PictureLink;
import cognitivity.web.app.config.HibernateBeanConfiguration;
import config.TestContextBeanConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

/**
 * Created by peers on 23/06/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContextBeanConfiguration.class, HibernateBeanConfiguration.class})
public class PictureLinkServiceTest {

    @Autowired
    private PictureLinkDAO dao;

    @Before
    public void setup() {

        Mockito.reset(dao);

    }

    @Test
    public void fullTest()throws Exception{
        PictureLinkService service = new PictureLinkService(dao);
        PictureLink link = new PictureLink("link","name");

        doReturn(1L).when(dao).add(link);
        try {

            service.createPictureLink(link);
        }catch (Exception e){
            assertTrue("Problem with adding a link",false);
        }

        doReturn(link).when(dao).get(1L);
        PictureLink linkRes = service.findPictureLinkById(1L);
        assertEquals("Problem with finding link by id",link,linkRes);

        doReturn(1L).when(dao).update(link);
        service.updatePictureLink(link);

        doNothing().when(dao).deleteLinkByName("name");
        service.deletePictureLink("name");


        doReturn(null).when(dao).getAllLinks();

        doThrow(new org.hibernate.HibernateException("")).when(dao).add(any());
        try{
            service.createPictureLink(new PictureLink());
            assertTrue("Problem with handling with exception at create",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).update(any());
        try {
            service.updatePictureLink(new PictureLink());
            assertTrue("Problem with handling with exception at update",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).deleteLinkByName(any());
        try {
            service.deletePictureLink("");
            assertTrue("Problem with handling with exception at delete",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).get(7L);
        try {
            service.findPictureLinkById(7);
            assertTrue("Problem with handling with exception at finding link by id",false);
        }catch (Exception e){}

        doThrow(new org.hibernate.HibernateException("")).when(dao).getAllLinks();
        try {
            service.getAllPictureLinks();
            assertTrue("Problem with handling with exception at getting all picture links",false);
        }catch (Exception e){}




    }
}