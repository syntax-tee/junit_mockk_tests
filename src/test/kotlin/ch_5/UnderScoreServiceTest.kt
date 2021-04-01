package ch_5

import ch3_5.*
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.amshove.kluent.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UnderScoreServiceTest {



    @Nested
    class UnderScoreTest{
        val mockQuestionRepository = mockk<IQuestionRepository>(relaxUnitFun = true)
        val mockUserRepository = mockk<IUserRepository>(relaxUnitFun = true)

        val questionId = 20
        val voteId = 10

        val service = UnderflowService(mockQuestionRepository, mockUserRepository)

        @Test
        fun `should be able to initialize`() {
            service.shouldNotBeNull()
        }

        @Test
        fun `should be able to vote up question`() {
            val user = User(1, "Alice")
            val question = Question(20, user, "title", "question")
            user.changeReputation(3000)

            question.voteUp()
            question.voteUp()

            every { mockQuestionRepository.findQuestion(questionId) } returns question
            every { mockUserRepository.findUser(voteId) } returns user
            every { mockUserRepository.findUser(question.userId) } returns user
            every { mockQuestionRepository.update(question) } just Runs
            every { mockUserRepository.update(user) } just Runs


            val votes = service.voteUpQuestion(20, 10)
            votes.shouldBe(3)
        }

        @Test
        fun `should throw an exception id the question id is invalid`(){
            val user = User(1, "Alice")
            val question = Question(2, user, "title", "question")

            every { mockQuestionRepository.findQuestion(questionId) } throws Exception()

            invoking { service.voteUpQuestion(questionId,voteId) } `should throw` ServiceException::class
        }

    }


    @Nested
    class UnderScoreTestAnnotation{

        @RelaxedMockK
        lateinit var mockQuestionRepository :IQuestionRepository

        @RelaxedMockK
        lateinit var mockUserRepository :IUserRepository


        init {
            MockKAnnotations.init(this)
        }
        val questionId = 20
        val voteId = 10

        val service = UnderflowService(mockQuestionRepository, mockUserRepository)

        @Test
        fun `should be able to initialize`() {
            service.shouldNotBeNull()
        }

        @Test
        fun `should be able to vote up question`() {
            val user = User(1, "Alice")
            val question = Question(20, user, "title", "question")
            user.changeReputation(3000)

            question.voteUp()
            question.voteUp()

            every { mockQuestionRepository.findQuestion(questionId) } returns question
            every { mockUserRepository.findUser(voteId) } returns user
            every { mockUserRepository.findUser(question.userId) } returns user
            every { mockQuestionRepository.update(question) } just Runs
            every { mockUserRepository.update(user) } just Runs


            val votes = service.voteUpQuestion(20, 10)
            votes.shouldBe(3)
        }

        @Test
        fun `should throw an exception id the question id is invalid`(){
            val user = User(1, "Alice")
            val question = Question(2, user, "title", "question")

            every { mockQuestionRepository.findQuestion(questionId) } throws Exception()

            invoking { service.voteUpQuestion(questionId,voteId) } `should throw` ServiceException::class
        }

    }



}