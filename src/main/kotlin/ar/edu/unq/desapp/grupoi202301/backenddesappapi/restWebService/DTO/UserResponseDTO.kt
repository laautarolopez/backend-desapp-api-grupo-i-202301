package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User

class UserResponseDTO(
    val id: Long?,
    val name:String?,
    val lastName:String?,
    val operations: Int?,
    val reputation: Int?
    ) {

    fun toModel(): User {
        val user = User()
        user.id = this.id
        user.name = this.name
        user.lastName = this.lastName
        user.operations = this.operations
        user.reputation = this.reputation
        return user
    }

    companion object {
        fun fromModel(user: User) =
            UserResponseDTO(
                id = user.id,
                name = user.name,
                lastName = user.lastName,
                operations = user.operations,
                reputation = user.reputation
            )
    }
}