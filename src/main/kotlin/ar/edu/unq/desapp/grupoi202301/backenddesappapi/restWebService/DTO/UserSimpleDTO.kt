package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User

class UserSimpleDTO(
    val id: Long?,
    val name:String?,
    val lastName:String?,
) {

    fun toModel(): User {
        val user = User()
        user.id = this.id
        user.name = this.name
        user.lastName = this.lastName
        return user
    }

    companion object {
        fun fromModel(user: User) =
            UserSimpleDTO(
                id = user.id,
                name = user.name,
                lastName = user.lastName
            )
    }

    constructor() : this(null, null, null)
}