import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import org.innercircle.Main;
import org.innercircle.entity.*;
import org.innercircle.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest(classes = Main.class)
public class OnboardingTest01 {
    // org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: could not initialize proxy - no Session


    @Autowired
    ApplicationContext context;

    @Autowired
    SurveyService surveyService;

    @Autowired
    SurveyItemService surveyItemService;

    @Autowired
    ItemOptionService itemOptionService;

    @Autowired
    SuveryAnswerService suveryAnswerService;

    @Autowired
    AnswerItemService answerItemService;

    @PersistenceContext
    EntityManager entityManager;


    private static Survey surveyTest = new Survey();
    private static List<SurveyItem> surveyItemTestList = new ArrayList<>();

    private static List<ItemOption> itemOptionTestList = new ArrayList<>();

//    @BeforeAll
    @BeforeEach
    public void setup() {
        //
        for(int i=0; i<5; i++) {
            SurveyItem surveyItemTest = new SurveyItem();
            surveyItemTest.setTitle("title"+i);
            surveyItemTest.setDesc("desc"+i);
            surveyItemTest.setIsRequiredYN("Y");
            OnboardingTest01.surveyItemTestList.add(surveyItemTest);
        }
        //
        for(int i=0; i<3; i++) {
            ItemOption itemOptionTest = new ItemOption();
            itemOptionTest.setContent("option"+i);
            OnboardingTest01.itemOptionTestList.add(itemOptionTest);
        }
        surveyTest.setTitle("hello");
        surveyTest.setDesc("world");
        surveyTest.loadSurveyItemList(surveyItemTestList);
    }

    @Test
    public void runJpaTest() {
        EntityManagerFactory emf = context.getBean("entityManagerFactory", EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx =  em.getTransaction();

        try {
            tx.begin();
            //
            Survey survey = new Survey();
            em.persist(survey);
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



//    요청 값에는 [설문조사 이름], [설문조사 설명], [설문 받을 항목]이 포함됩니다.
//    [설문 받을 항목]은 [항목 이름], [항목 설명], [항목 입력 형태], [항목 필수 여부]의 구성으로 이루어져있습니다.
//    [항목 입력 형태]는 [단답형], [장문형], [단일 선택 리스트], [다중 선택 리스트]의 구성으로 이루어져있습니다.
//    [단일 선택 리스트], [다중 선택 리스트]의 경우 선택 할 수 있는 후보를 요청 값에 포함하여야 합니다.
//    [설문 받을 항목]은 1개 ~ 10개까지 포함 할 수 있습니다.
    @Test
    public void createAndReadSurveyTest() {
        //
        String surveyTitle = "survey test title";
        String surveyDesc = "survey test desc";
        Survey survey1 = new Survey();
        survey1.setTitle(surveyTitle);
        survey1.setDesc(surveyDesc);
        survey1.loadSurveyItemList(OnboardingTest01.surveyItemTestList);
        int optionIdx = 2;
        OnboardingTest01.surveyItemTestList.get(optionIdx).loadItemOptionList(OnboardingTest01.itemOptionTestList);


        //
        Long seq = surveyService.saveSurvey(survey1);
        //

        System.out.println("hello world 11111111111111111111111");
        entityManager.flush();
        entityManager.clear();
        System.out.println("hello world 222222222222222222222222");

        //
        Survey survey2 = surveyService.findSurvey(seq);
        Assertions.assertEquals(surveyTitle, survey2.getTitle());
        Assertions.assertEquals(surveyDesc, survey2.getDesc());
        Assertions.assertEquals(OnboardingTest01.surveyItemTestList.size(), survey2.getSurveyItemList().size());
        Assertions.assertEquals(OnboardingTest01.surveyItemTestList.get(optionIdx).getItemOptionList().size(), survey2.getSurveyItemList().get(optionIdx).getItemOptionList().size());

    }

    @Test
    @Commit
    public void createAndReadAnswerTest() {
        //

        List<AnswerItem> answerItemList = new ArrayList<>();
        List<SurveyItem> surveyItemList = surveyTest.getSurveyItemList();
        for (int i = 0; i < surveyItemList.size(); i++) {
            SurveyItem surveyItem = surveyItemList.get(i);
            AnswerItem answerItem = new AnswerItem();
            answerItem.loadSurveyItem(surveyItem);
            answerItem.setAnswVal("answer"+i);
            answerItemList.add(answerItem);
        }
        //
        int itemIdx = 3;
        SurveyItem surveyItem = surveyItemTestList.get(itemIdx);
        surveyItem.loadItemOptionList(itemOptionTestList);
        //
        AnswerItem answerItem = answerItemList.get(itemIdx);
        answerItem.loadSurveyItem(surveyItem);
        answerItem.loadItemOption(itemOptionTestList.get(2));
        //
        SurveyAnswer surveyAnswer1 = new SurveyAnswer();
        surveyAnswer1.loadSurvey(surveyTest);
        surveyAnswer1.loadAnswerItemList(answerItemList);

        //
        Long seqSurvey = surveyService.saveSurvey(surveyTest);
        Long seqAnswer = suveryAnswerService.saveSurveyAnswer(surveyAnswer1);
        Long seqOption = itemOptionTestList.get(2).getSeq();
        //
        System.out.println("hello world 11111111111111111111111");
        entityManager.flush();
        entityManager.clear();
        System.out.println("hello world 222222222222222222222222");

        //
        SurveyAnswer surveyAnswer2 = suveryAnswerService.findOne(seqAnswer);
        Assertions.assertEquals(seqSurvey, surveyAnswer2.getSurvey().getSeq());
        Assertions.assertEquals(surveyAnswer1.getAnswerItemList().size(), surveyAnswer2.getAnswerItemList().size());
        Assertions.assertEquals(seqOption, surveyAnswer2.getAnswerItemList().get(itemIdx).getItemOption().getSeq());
    }


}
