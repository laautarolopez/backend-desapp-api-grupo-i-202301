package ar.edu.unq.desapp.grupoi202301.backenddesappapi.service.imp.exception

class UserNonExistent : RuntimeException() {
    override val message: String?
        get() = "User non-existent."
}