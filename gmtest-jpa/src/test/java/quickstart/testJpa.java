package quickstart;

import demoJPA.dao.CategoryDao;
import demoJPA.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 单强 2018年5月14日18:39:28
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class testJpa {

    int id=5;

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void testSave(){
        Category category=new Category();
        category.setId(id);
        category.setName("test");
        categoryDao.save(category);
    }

    @Test
    public void testUpdateName(){
        String name="单强11";
        categoryDao.updateName(id,name);
    }

    @Test
    public void testFindById(){
        Category category=categoryDao.findOne(id);
        System.out.println(category);
    }

    @Test
    public void count(){
        long count=categoryDao.count();
        System.out.println(count);
    }

    @Test
    public void testFindAll(){
        Iterable<Category> categories=categoryDao.findAll();
        for(Category category:categories){
            System.out.println(category);
        }
    }

    @Test
    public void delete(){
        categoryDao.delete(id);
    }

}
