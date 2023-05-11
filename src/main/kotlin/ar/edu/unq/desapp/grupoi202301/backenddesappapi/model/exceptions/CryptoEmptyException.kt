package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions

class CryptoEmptyException : RuntimeException() {

    override val message: String?
        get() = "The crypto cannot be null."

    companion object {

        private val serialVersionUID = 1L
    }
}