package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions

class ActionEmptyException : RuntimeException() {

    override val message: String?
        get() = "The action cannot be null."

    companion object {

        private val serialVersionUID = 1L
    }
}