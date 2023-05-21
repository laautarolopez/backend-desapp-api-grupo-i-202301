package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.exception

class CryptoNonExistent : RuntimeException() {
    override val message: String?
        get() = "Crypto non-existent."
}