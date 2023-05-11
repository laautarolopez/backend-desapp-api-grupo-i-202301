package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions

class OperationEmptyException : RuntimeException() {

    override val message: String?
        get() = "The operation cannot be null."

    companion object {

        private val serialVersionUID = 1L
    }
}