package ch3

import ch3_5.User
import ch3_5.Question
import ch3_5.QuestionException
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
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, "", "questions")
            }
        }

        @Test
        fun `throw exception if body is empty`() {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, "title", "")
            }
        }

        @Test
        fun `throw exception if body and title is empty`() {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, "", "")
            }
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

}