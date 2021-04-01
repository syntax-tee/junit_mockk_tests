package ch_4

import ch3_5.Answer
import ch3_5.Question
import ch3_5.QuestionException
import ch3_5.User
import org.amshove.kluent.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource

@Suppress("ClassName")
class QuestionTest {


    val user = User(1, "Alice")

    @Nested
    inner class `constructor should` {


        val user = User(1, "Alice")

        @Test
        fun `throw exception if title is empty`() {
            invoking {Question(1, user, "", "questions")  }.shouldThrow(QuestionException::class)
        }

        @Test
        fun `throw exception if body is empty`() {
            invoking {Question(1, user, "", "questions")  } `should throw` QuestionException::class
        }

        @Test
        fun `throw exception if body and title is empty`() {
            invoking {Question(1, user, "", "")}.shouldThrow(QuestionException::class)
        }

        @Test
        fun `not throw exception if the question  is valid`() {
            Assertions.assertDoesNotThrow {
                Question(1, user, "title", "question")
            }
        }


        @ParameterizedTest
        @CsvSource(
                "' ',question",
                "'',question",
                "' ',title",
                "'',title")
        fun `throw an exception if question is invalid`(title: String, body: String) {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, title, body)
            }
        }
    }


    @ParameterizedTest
    @MethodSource("titlesAndQuestions")
    fun `throw an exception if title or question is invalid`(title: String, question: String) {
        Assertions.assertThrows(QuestionException::class.java) {
            Question(1, user, title, question)
        }
    }


    companion object {
        @Suppress("unused")
        @JvmStatic
        fun titlesAndQuestions() = listOf(
                Arguments.of("", "Question"),
                Arguments.of(" ", "Question"),
                Arguments.of("", "title"),
                Arguments.of(" ", "title")
        )
    }

    @Nested
    inner class answers{
    val user = User(1,"Alice")
    val question = Question(1,user,"title","question")

        @Test
        fun `should have an answer`(){
         question.answers.shouldBeEmpty()
        }

        @Test
        fun `should have answer`(){
            val answer = Answer(1,user,"answer")
            question.addAnswer(answer)
            question.answers.shouldNotBeEmpty()
        }

        @Test
        fun `should contain an  answer`(){
            val answer = Answer(1,user,"answer")
            question.addAnswer(answer)
            question.answers.shouldContain(answer)
        }


        @Test
        fun `should contain an  answer if two are added`(){
            val answer1 = Answer(1,user,"answer")
            question.addAnswer(answer1)

            val answer2 = Answer(2,user,"answer")
            question.addAnswer(answer2)

            question.answers.shouldContain(answer1)
        }


        @Test
        fun `should contain an  answer that was not added`(){
            val answer1 = Answer(1,user,"answer")
            question.addAnswer(answer1)

            val answer2 = Answer(2,user,"answer")

            question.answers.shouldNotContain(answer2)
        }
    }

}