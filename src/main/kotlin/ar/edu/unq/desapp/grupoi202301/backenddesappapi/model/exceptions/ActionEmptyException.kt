package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions

class ActionEmptyException : RuntimeException() {

    override val message: String?
        get() = "The action should be 'MAKE', 'CONFIRM' or 'CANCEL'. It cannot be null."

    companion object {

        private val serialVersionUID = 1L
    }
}