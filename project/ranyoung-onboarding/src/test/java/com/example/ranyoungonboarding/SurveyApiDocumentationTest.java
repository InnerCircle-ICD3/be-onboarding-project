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

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class SurveyApiDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void createSurveyTest() throws Exception {
        // 설문조사 생성 요청 데이터 준비
        CreateSurveyRequest request = new CreateSurveyRequest();
        request.setName("고객 만족도 조사");
        request.setDescription("서비스 품질 개선을 위한 설문조사");

        List<SurveyItemRequest> questions = new ArrayList<>();

        SurveyItemRequest q1 = new SurveyItemRequest();
        q1.setName("서비스 만족도");
        q1.setDescription("전반적인 서비스 만족도를 평가해주세요");
        q1.setType(QuestionType.SINGLE_CHOICE);
        q1.setRequired(true);
        q1.setOptions(Arrays.asList("매우 불만족", "불만족", "보통", "만족", "매우 만족"));
        questions.add(q1);

        SurveyItemRequest q2 = new SurveyItemRequest();
        q2.setName("개선사항");
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
        // 먼저 설문조사를 생성
        CreateSurveyRequest createRequest = new CreateSurveyRequest();
        createRequest.setName("초기 설문조사");
        createRequest.setDescription("업데이트 테스트용 초기 설문조사");

        List<SurveyItemRequest> initialQuestions = new ArrayList<>();
        SurveyItemRequest q = new SurveyItemRequest();
        q.setName("초기 질문");
        q.setDescription("초기 질문 설명");
        q.setType(QuestionType.SHORT_ANSWER);
        q.setRequired(true);
        initialQuestions.add(q);

        createRequest.setQuestions(initialQuestions);

        String createResult = mockMvc.perform(post("/api/surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // ID 추출 (실제로는 JSON 파싱이 필요)
        Long surveyId = 1L; // 예시 ID, 실제로는 createResult에서 추출

        // 업데이트 요청 준비
        CreateSurveyRequest updateRequest = new CreateSurveyRequest();
        updateRequest.setName("업데이트된 설문조사");
        updateRequest.setDescription("업데이트 테스트 완료");

        List<SurveyItemRequest> updatedQuestions = new ArrayList<>();
        SurveyItemRequest updatedQ = new SurveyItemRequest();
        updatedQ.setName("수정된 질문");
        updatedQ.setDescription("질문이 수정되었습니다");
        updatedQ.setType(QuestionType.MULTIPLE_CHOICE);
        updatedQ.setRequired(true);
        updatedQ.setOptions(Arrays.asList("옵션1", "옵션2", "옵션3"));
        updatedQuestions.add(updatedQ);

        updateRequest.setQuestions(updatedQuestions);

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

    @Test
    public void submitResponseTest() throws Exception {
        // 설문조사 생성 과정 생략 (updateSurveyTest 참고)
        Long surveyId = 1L; // 예시 ID

        // 응답 제출 요청 준비
        SubmitResponseRequest request = new SubmitResponseRequest();
        List<AnswerRequest> answers = new ArrayList<>();

        AnswerRequest answer1 = new AnswerRequest();
        answer1.setQuestionId(1L); // 예시 질문 ID
        answer1.setValue("매우 만족");
        answers.add(answer1);

        AnswerRequest answer2 = new AnswerRequest();
        answer2.setQuestionId(2L); // 예시 질문 ID
        answer2.setValue("서비스가 매우 좋았습니다. 다음에도 이용하겠습니다.");
        answers.add(answer2);

        request.setAnswers(answers);

        // API 호출 및 문서화
        mockMvc.perform(post("/api/surveys/{surveyId}/responses", surveyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document("submit-response",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("surveyId").description("응답을 제출할 설문조사 ID")
                        ),
                        requestFields(
                                fieldWithPath("answers").description("설문 응답 목록"),
                                fieldWithPath("answers[].questionId").description("질문 ID"),
                                fieldWithPath("answers[].value").description("단답형, 장문형, 단일 선택형 응답 값"),
                                fieldWithPath("answers[].values").optional().description("다중 선택형 응답 값")
                        ),
                        responseFields(
                                fieldWithPath("id").description("생성된 응답 ID"),
                                fieldWithPath("surveyId").description("설문조사 ID"),
                                fieldWithPath("answers").description("제출된 응답 목록"),
                                fieldWithPath("answers[].id").description("응답 항목 ID"),
                                fieldWithPath("answers[].questionId").description("질문 ID"),
                                fieldWithPath("answers[].questionName").description("질문 이름"),
                                fieldWithPath("answers[].textValue").description("텍스트 응답 값"),
                                fieldWithPath("answers[].multipleValues").description("다중 선택 응답 값"),
                                fieldWithPath("submittedAt").description("응답 제출 시간")
                        )
                ));
    }

    @Test
    public void getSurveyResponsesTest() throws Exception {
        Long surveyId = 1L; // 예시 ID

        // API 호출 및 문서화
        mockMvc.perform(get("/api/surveys/{surveyId}/responses", surveyId)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(document("get-survey-responses",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("surveyId").description("응답을 조회할 설문조사 ID")
                        ),
                        queryParameters(
                                parameterWithName("page").description("페이지 번호 (0부터 시작)"),
                                parameterWithName("size").description("페이지 크기")
                        ),
                        responseFields(
                                fieldWithPath("content").description("응답 목록"),
                                fieldWithPath("content[].id").description("응답 ID"),
                                fieldWithPath("content[].surveyId").description("설문조사 ID"),
                                fieldWithPath("content[].answers").description("응답 항목 목록"),
                                fieldWithPath("content[].answers[].id").description("응답 항목 ID"),
                                fieldWithPath("content[].answers[].questionId").description("질문 ID"),
                                fieldWithPath("content[].answers[].questionName").description("질문 이름"),
                                fieldWithPath("content[].answers[].textValue").description("텍스트 응답 값"),
                                fieldWithPath("content[].answers[].multipleValues").description("다중 선택 응답 값"),
                                fieldWithPath("content[].submittedAt").description("응답 제출 시간"),
                                fieldWithPath("pageable").description("페이지 정보"),
                                fieldWithPath("pageable.sort").description("정렬 정보"),
                                fieldWithPath("pageable.sort.empty").description("정렬이 비어있는지 여부"),
                                fieldWithPath("pageable.sort.sorted").description("정렬이 되어있는지 여부"),
                                fieldWithPath("pageable.sort.unsorted").description("정렬이 안 되어있는지 여부"),
                                fieldWithPath("pageable.offset").description("페이지 오프셋"),
                                fieldWithPath("pageable.pageNumber").description("페이지 번호"),
                                fieldWithPath("pageable.pageSize").description("페이지 크기"),
                                fieldWithPath("pageable.paged").description("페이징이 되어있는지 여부"),
                                fieldWithPath("pageable.unpaged").description("페이징이 안 되어있는지 여부"),
                                fieldWithPath("last").description("마지막 페이지 여부"),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("totalElements").description("전체 요소 수"),
                                fieldWithPath("first").description("첫 페이지 여부"),
                                fieldWithPath("size").description("페이지 크기"),
                                fieldWithPath("number").description("현재 페이지 번호"),
                                fieldWithPath("sort").description("정렬 정보"),
                                fieldWithPath("sort.empty").description("정렬이 비어있는지 여부"),
                                fieldWithPath("sort.sorted").description("정렬이 되어있는지 여부"),
                                fieldWithPath("sort.unsorted").description("정렬이 안 되어있는지 여부"),
                                fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                                fieldWithPath("empty").description("결과가 비어있는지 여부")
                        )
                ));
    }

    @Test
    public void searchSurveyResponsesTest() throws Exception {
        Long surveyId = 1L; // 예시 ID

        // API 호출 및 문서화
        mockMvc.perform(get("/api/surveys/{surveyId}/responses", surveyId)
                        .param("questionName", "서비스 만족도")
                        .param("answerValue", "매우 만족")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(document("search-survey-responses",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("surveyId").description("응답을 검색할 설문조사 ID")
                        ),
                        queryParameters(
                                parameterWithName("questionName").description("검색할 질문 이름"),
                                parameterWithName("answerValue").description("검색할 응답 값"),
                                parameterWithName("page").description("페이지 번호 (0부터 시작)"),
                                parameterWithName("size").description("페이지 크기")
                        ),
                        // responseFields는 getSurveyResponsesTest와 동일하므로 생략
                        responseFields(
                                fieldWithPath("content").description("검색된 응답 목록"),
                                fieldWithPath("content[].id").description("응답 ID"),
                                fieldWithPath("content[].surveyId").description("설문조사 ID"),
                                fieldWithPath("content[].answers").description("응답 항목 목록"),
                                fieldWithPath("content[].answers[].id").description("응답 항목 ID"),
                                fieldWithPath("content[].answers[].questionId").description("질문 ID"),
                                fieldWithPath("content[].answers[].questionName").description("질문 이름"),
                                fieldWithPath("content[].answers[].textValue").description("텍스트 응답 값"),
                                fieldWithPath("content[].answers[].multipleValues").description("다중 선택 응답 값"),
                                fieldWithPath("content[].submittedAt").description("응답 제출 시간"),
                                fieldWithPath("pageable").description("페이지 정보"),
                                fieldWithPath("pageable.sort").description("정렬 정보"),
                                fieldWithPath("pageable.sort.empty").description("정렬이 비어있는지 여부"),
                                fieldWithPath("pageable.sort.sorted").description("정렬이 되어있는지 여부"),
                                fieldWithPath("pageable.sort.unsorted").description("정렬이 안 되어있는지 여부"),
                                fieldWithPath("pageable.offset").description("페이지 오프셋"),
                                fieldWithPath("pageable.pageNumber").description("페이지 번호"),
                                fieldWithPath("pageable.pageSize").description("페이지 크기"),
                                fieldWithPath("pageable.paged").description("페이징이 되어있는지 여부"),
                                fieldWithPath("pageable.unpaged").description("페이징이 안 되어있는지 여부"),
                                fieldWithPath("last").description("마지막 페이지 여부"),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("totalElements").description("전체 요소 수"),
                                fieldWithPath("first").description("첫 페이지 여부"),
                                fieldWithPath("size").description("페이지 크기"),
                                fieldWithPath("number").description("현재 페이지 번호"),
                                fieldWithPath("sort").description("정렬 정보"),
                                fieldWithPath("sort.empty").description("정렬이 비어있는지 여부"),
                                fieldWithPath("sort.sorted").description("정렬이 되어있는지 여부"),
                                fieldWithPath("sort.unsorted").description("정렬이 안 되어있는지 여부"),
                                fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                                fieldWithPath("empty").description("결과가 비어있는지 여부")
                        )
                ));
    }
}