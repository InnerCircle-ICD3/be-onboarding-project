//package com.example.ranyoungonboarding;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//
//
//import com.example.ranyoungonboarding.api.*;
//import com.example.ranyoungonboarding.domain.QuestionType;
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.List;
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//        import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//        import static org.springframework.restdocs.request.RequestDocumentation.*;
//        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
//public class setuptest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired private ObjectMapper objectMapper;
//
//    private Long surveyId;
//    private Long questionId;
//
//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext,
//                      RestDocumentationContextProvider restDocumentation) throws Exception {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .build();
//
//        // 1. 설문 생성
//        CreateSurveyRequest surveyRequest = new CreateSurveyRequest();
//        surveyRequest.setName("문서 테스트용 설문");
//        surveyRequest.setDescription("테스트용 설명");
//
//        SurveyItemRequest item = new SurveyItemRequest();
//        item.setName("서비스 만족도");
//        item.setDescription("서비스 만족도를 평가해주세요");
//        item.setType(QuestionType.SINGLE_CHOICE);
//        item.setRequired(true);
//        item.setOptions(List.of("좋음", "보통", "나쁨"));
//
//        surveyRequest.setQuestions(List.of(item));
//
//        String responseJson = mockMvc.perform(post("/api/surveys")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(surveyRequest)))
//                .andExpect(status().isCreated())
//                .andReturn().getResponse().getContentAsString();
//
//        JsonNode root = objectMapper.readTree(responseJson);
//        surveyId = root.get("id").asLong();
//        questionId = root.get("questions").get(0).get("id").asLong();
//
//        // 2. 응답 생성
//        SubmitResponseRequest responseRequest = new SubmitResponseRequest();
//        AnswerRequest answer = new AnswerRequest();
//        answer.setQuestionId(questionId);
//        answer.setValue("좋음");
//        responseRequest.setAnswers(List.of(answer));
//
//        mockMvc.perform(post("/api/surveys/" + surveyId + "/responses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(responseRequest)))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    public void getSurveyResponsesTest() throws Exception {
//        mockMvc.perform(get("/api/surveys/{surveyId}/responses", surveyId)
//                        .param("page", "0")
//                        .param("size", "10"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("get-survey-responses",
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("surveyId").description("응답을 조회할 설문조사 ID")
//                        ),
//                        queryParameters(
//                                parameterWithName("page").description("페이지 번호"),
//                                parameterWithName("size").description("페이지 크기")
//                        ),
//                        responseFields(
//                                fieldWithPath("content[].id").description("응답 ID"),
//                                fieldWithPath("content[].surveyId").description("설문조사 ID"),
//                                fieldWithPath("content[].answers[].id").description("응답 항목 ID"),
//                                fieldWithPath("content[].answers[].questionId").description("질문 ID"),
//                                fieldWithPath("content[].answers[].questionName").description("질문 이름"),
//                                fieldWithPath("content[].answers[].textValue").description("응답 값"),
//                                fieldWithPath("content[].answers[].multipleValues").description("다중 응답"),
//                                fieldWithPath("content[].submittedAt").description("제출 시간"),
//                                // 📌 pageable 전체 하위 필드 무시
//                                fieldWithPath("pageable.pageNumber").ignored(),
//                                fieldWithPath("pageable.pageSize").ignored(),
//                                fieldWithPath("pageable.sort.empty").ignored(),
//                                fieldWithPath("pageable.sort.sorted").ignored(),
//                                fieldWithPath("pageable.sort.unsorted").ignored(),
//                                fieldWithPath("pageable.offset").ignored(),
//                                fieldWithPath("pageable.paged").ignored(),
//                                fieldWithPath("pageable.unpaged").ignored(),
//
//                                // 📌 루트에 있는 sort 무시
//                                fieldWithPath("sort.empty").ignored(),
//                                fieldWithPath("sort.sorted").ignored(),
//                                fieldWithPath("sort.unsorted").ignored(),
//
//                                // 📌 기타 페이지 정보 무시
//                                fieldWithPath("last").ignored(),
//                                fieldWithPath("totalPages").ignored(),
//                                fieldWithPath("totalElements").ignored(),
//                                fieldWithPath("first").ignored(),
//                                fieldWithPath("size").ignored(),
//                                fieldWithPath("number").ignored(),
//                                fieldWithPath("numberOfElements").ignored(),
//                                fieldWithPath("empty").ignored()
//
//                                )
//                ));
//    }
//
//    // 다른 테스트들도 필요 시 여기에 추가 가능
//}
