import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.innercircle.Main;
import org.innercircle.entity.ItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = Main.class)
public class OnboardingTest01 {


    @Autowired
    ApplicationContext context;

    @Test
    public void runJpaTest() {
        EntityManagerFactory emf = context.getBean("entityManagerFactory", EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx =  em.getTransaction();

        try {
            tx.begin();
            //
            ItemType itemType = new ItemType();
            em.persist(itemType);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            Assertions.fail();
        } finally {
            em.close();
            emf.close();
        }
    }
}
