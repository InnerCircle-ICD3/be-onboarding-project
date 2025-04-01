package com.example.ranyoungonboarding;


import com.example.ranyoungonboarding.api.CreateSurveyRequest;
import com.example.ranyoungonboarding.api.SurveyItemRequest;
import com.example.ranyoungonboarding.api.SubmitResponseRequest;
import com.example.ranyoungonboarding.api.AnswerRequest;
import com.example.ranyoungonboarding.domain.QuestionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, SpringExtension.class})
@SpringBootApplication
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class SurveyApiDocumentationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }
    @Test
    public void createSurveyTest() throws Exception {
        CreateSurveyRequest request = new CreateSurveyRequest();
        request.setName("고객 만족도 조사");
        request.setDescription("서비스 품질 관리를 위한 설문 조사");

        List<SurveyItemRequest> questions = new ArrayList<>();

        SurveyItemRequest q1 = new SurveyItemRequest();
        q1.setName("서비스 만족도");
        q1.setDescription("전반적인 서비스 만족도를 평가해주세요");
        q1.setType(QuestionType.SINGLE_CHOICE);
        q1.setRequired(true);
        q1.setOptions(Arrays.asList("매우 불만족","불만족","보통","만족","매우 만족"));
        questions.add(q1);


        SurveyItemRequest q2 = new SurveyItemRequest();
        q2.setName("개선 사항");
        q2.setDescription("서비스 개선을 위한 제안사항을 자유롭게 작성해주세요");
        q2.setType(QuestionType.LONG_ANSWER);
        q2.setRequired(false);
        questions.add(q2);

        request.setQuestions(questions);

        // API 호출 및 문서화
        mockMvc.perform(post("/api/surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document("create-survey",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("설문조사 이름"),
                                fieldWithPath("description").description("설문조사 설명"),
                                fieldWithPath("questions").description("설문 항목 목록"),
                                fieldWithPath("questions[].name").description("항목 이름"),
                                fieldWithPath("questions[].description").description("항목 설명"),
                                fieldWithPath("questions[].type").description("항목 입력 형태 (SHORT_ANSWER, LONG_ANSWER, SINGLE_CHOICE, MULTIPLE_CHOICE)"),
                                fieldWithPath("questions[].required").description("필수 항목 여부"),
                                fieldWithPath("questions[].options").description("선택 항목 (단일 또는 다중 선택 리스트인 경우)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("생성된 설문조사 ID"),
                                fieldWithPath("name").description("설문조사 이름"),
                                fieldWithPath("description").description("설문조사 설명"),
                                fieldWithPath("questions").description("설문 항목 목록"),
                                fieldWithPath("questions[].id").description("항목 ID"),
                                fieldWithPath("questions[].name").description("항목 이름"),
                                fieldWithPath("questions[].description").description("항목 설명"),
                                fieldWithPath("questions[].type").description("항목 입력 형태"),
                                fieldWithPath("questions[].required").description("필수 항목 여부"),
                                fieldWithPath("questions[].options").description("선택 항목 목록"),
                                fieldWithPath("questions[].position").description("항목의 순서"),
                                fieldWithPath("createdAt").description("생성 시간"),
                                fieldWithPath("updatedAt").description("수정 시간")
                        )
                ));
    }

    @Test
    public void updateSurveyTest() throws Exception {
        //먼저 설문조사를 생성
        CreateSurveyRequest createRequest = new CreateSurveyRequest();
        createRequest.setName("초기 설문 조사");
        createRequest.setDescription("업데이트 테스트용 초기 설문 조사");

        List<SurveyItemRequest> initialQuestions = new ArrayList<>();
        SurveyItemRequest q1 = new SurveyItemRequest();
        q1.setName("초기 질문");
        q1.setDescription("초기 질문 생성");
        q1.setType(QuestionType.SHORT_ANSWER);
        q1.setRequired(true);
        initialQuestions.add(q1);

        createRequest.setQuestions(initialQuestions);


        String createResult = mockMvc.perform(put("/api/surveys")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createRequest)))
                        .andExpect(status().isCreated())
                        .andReturn().getResponse().getContentAsString();

        //ID 추출(실제로는 JSON파싱이 필요)
        Long surveyId = 1L;  //ex id, 실제로는 createResult에서 추출

        //업데이트 요청 준비
        CreateSurveyRequest updateRequest = new CreateSurveyRequest();
        updateRequest.setName("업데이트 설문 조사");
        updateRequest.setDescription("업데이트 테스트 완료");


       List<SurveyItemRequest> updateQuestions = new ArrayList<>();
       SurveyItemRequest updatedQ = new SurveyItemRequest();
       updatedQ.setName("수정된 질문");
       updatedQ.setDescription("질문이 수정되었습니다.");
       updatedQ.setType(QuestionType.MULTIPLE_CHOICE);
       updatedQ.setRequired(true);
       updatedQ.setOptions(Arrays.asList("옵션1","옵션2","옵션3"));
       updateQuestions.add(updatedQ);

       updateRequest.setQuestions(updateQuestions);

        // API 호출 및 문서화
        mockMvc.perform(put("/api/surveys/{surveyId}", surveyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andDo(document("update-survey",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("surveyId").description("수정할 설문조사 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").description("수정할 설문조사 이름"),
                                fieldWithPath("description").description("수정할 설문조사 설명"),
                                fieldWithPath("questions").description("수정할 설문 항목 목록"),
                                fieldWithPath("questions[].name").description("항목 이름"),
                                fieldWithPath("questions[].description").description("항목 설명"),
                                fieldWithPath("questions[].type").description("항목 입력 형태"),
                                fieldWithPath("questions[].required").description("필수 항목 여부"),
                                fieldWithPath("questions[].options").description("선택 항목 (단일 또는 다중 선택 리스트인 경우)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("설문조사 ID"),
                                fieldWithPath("name").description("수정된 설문조사 이름"),
                                fieldWithPath("description").description("수정된 설문조사 설명"),
                                fieldWithPath("questions").description("수정된 설문 항목 목록"),
                                fieldWithPath("questions[].id").description("항목 ID"),
                                fieldWithPath("questions[].name").description("항목 이름"),
                                fieldWithPath("questions[].description").description("항목 설명"),
                                fieldWithPath("questions[].type").description("항목 입력 형태"),
                                fieldWithPath("questions[].required").description("필수 항목 여부"),
                                fieldWithPath("questions[].options").description("선택 항목 목록"),
                                fieldWithPath("questions[].position").description("항목의 순서"),
                                fieldWithPath("createdAt").description("생성 시간"),
                                fieldWithPath("updatedAt").description("수정 시간")
                        )
                ));
    }
//전송 test 진행중



}
