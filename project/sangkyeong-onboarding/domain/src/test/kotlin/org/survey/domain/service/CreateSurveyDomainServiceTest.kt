package org.survey.domain.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.survey.domain.dto.SurveyItemData
import org.survey.domain.survey.model.SurveyItem
import org.survey.domain.survey.repository.ItemOptionRepository
import org.survey.domain.survey.repository.SurveyItemRepository
import org.survey.domain.survey.repository.SurveyRepository
import org.survey.domain.survey.service.SurveyDomainService

class CreateSurveyDomainServiceTest : BehaviorSpec({
    val surveyRepository = mockk<SurveyRepository>()
    val surveyItemRepository = mockk<SurveyItemRepository>()
    val itemOptionRepository = mockk<ItemOptionRepository>()
    val surveyDomainService = SurveyDomainService(surveyRepository, surveyItemRepository, itemOptionRepository)

    given("설문조사를 생성할 때") {
        val title = "설문조사 제목테스트1"
        val description = "설문조사 설명테스트1"

        `when`("설문조사 항목이 0개이면") {
            val emptyItems = emptyList<SurveyItemData>()

            then("설문조사 생성에 실패한다.") {
                val exception = shouldThrow<IllegalArgumentException> {
                    surveyDomainService.createSurvey(title, description, emptyItems)
                }
                exception.message shouldBe "설문 항목이 최소 1개 이상 필요하다."
            }
        }

        `when`("설문조사 항목이 10개를 초과하면") {
            val tooManyItems = List(11) { index ->
                SurveyItemData(
                    title = "항목 $index",
                    description = "설명 $index",
                    inputType = "TEXT",
                    isRequired = true,
                    options = null
                )
            }

            then("설문조사 생성에 실패한다.") {
                val exception = shouldThrow<IllegalArgumentException> {
                    surveyDomainService.createSurvey(title, description, tooManyItems)
                }
                exception.message shouldBe "설문 항목은 최대 10개까지 가능하다."
            }
        }

        `when`("단답형 설문 요청이 들어오면") {
            val items = listOf(
                SurveyItemData(
                    title = "설문조사 항목 제목테스트1",
                    description = "설문조사 항목 설명테스트1",
                    inputType = "SHORT_TEXT",
                    isRequired = true,
                    options = null
                )
            )

            every { surveyRepository.save(any()) } returns 1
            every { surveyItemRepository.saveAll(any()) } returns listOf(
                SurveyItem(
                    id = 1,
                    surveyId = 1,
                    title = "설문조사 항목 제목테스트1",
                    description = "설문조사 항목 설명테스트1",
                    inputType = "SHORT_TEXT",
                    isRequired = true
                )
            )
            every { itemOptionRepository.saveAll(any()) } just runs

            then("성공적으로 설문조사가 생성이 된다.") {
                surveyDomainService.createSurvey(title, description, items)
                verify { surveyRepository.save(any()) }
                verify { surveyItemRepository.saveAll(any()) }
            }
        }

        `when`("선택형 설문 요청이 들어오면") {
            val items = listOf(
                SurveyItemData(
                    title = "설문조사 항목 제목테스트1",
                    description = "설문조사 항목 설명테스트1",
                    inputType = "SINGLE_CHOICE",
                    isRequired = true,
                    options = listOf("선택지1", "선택지2", "선택지3")
                )
            )

            every { surveyRepository.save(any()) } returns 1
            every { surveyItemRepository.saveAll(any()) } returns listOf(
                SurveyItem(
                    id = 1,
                    surveyId = 1,
                    title = "설문조사 항목 제목테스트1",
                    description = "설문조사 항목 설명테스트1",
                    inputType = "SINGLE_CHOICE",
                    isRequired = true
                )
            )
            every { itemOptionRepository.saveAll(any()) } just runs

            then("성공적으로 설문조사가 생성이 된다.") {
                surveyDomainService.createSurvey(title, description, items)
                verify { surveyRepository.save(any()) }
                verify { surveyItemRepository.saveAll(any()) }
                verify { itemOptionRepository.saveAll(any()) }
            }
        }

        `when`("단답형 설문이지만 옵션이 존재하면") {
            val invalidShortTextItem = listOf(
                SurveyItemData(
                    title = "단답형 항목",
                    description = "단답형 설명",
                    inputType = "SHORT_TEXT",
                    isRequired = true,
                    options = listOf("옵션1", "옵션2")
                )
            )

            then("설문조사 생성에 실패한다.") {
                val exception = shouldThrow<IllegalArgumentException> {
                    surveyDomainService.createSurvey(title, description, invalidShortTextItem)
                }
                exception.message shouldBe "선택형 질문이 아니면 선택지가 없어야 한다."
            }
        }

        `when`("선택형 설문이지만 옵션이 0개이면") {
            val invalidChoiceItem = listOf(
                SurveyItemData(
                    title = "선택형 항목",
                    description = "선택형 설명",
                    inputType = "SINGLE_CHOICE",
                    isRequired = true,
                    options = emptyList()
                )
            )

            then("설문조사 생성에 실패한다.") {
                val exception = shouldThrow<IllegalArgumentException> {
                    surveyDomainService.createSurvey(title, description, invalidChoiceItem)
                }
                exception.message shouldBe "선택형 질문에는 최소 1개 이상의 선택지가 필요하다."
            }
        }
    }
})
