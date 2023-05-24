package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions

class OperationEmptyException : RuntimeException() {

    override val message: String?
        get() = "The operation should be 'BUY' or 'SALE'. It cannot be null."

    companion object {

        private val serialVersionUID = 1L
    }
}