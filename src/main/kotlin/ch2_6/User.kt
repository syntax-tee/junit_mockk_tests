package ch2_6

class User(val id: Int, val name: String) {

    var reputation: Int = 0
        private set

    fun changeReputation(amount: Int) {
        reputation+=amount
    }


    fun canEditPost():Boolean {
        return reputation > 2000
    }

    fun canComment():Boolean {
        return reputation > 50
    }

    fun canVote():Boolean {
        return reputation > 15
    }

}