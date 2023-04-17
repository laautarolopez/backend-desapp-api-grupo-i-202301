package ar.edu.unq.desapp.grupoi202301.backenddesappapi.RESTwebservice

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User

class UserDTO(
    val id: Long?,
    val name:String?,
    val lastName:String?,
    val email:String?,
    val adress:String?,
    val password:String?,
    val cvuMercadoPago:String?,
    val walletAdress:String?,
    ) {

    companion object {
        fun fromModel(user: User) =
            UserDTO(
                id = user.id,
                name = user.name(),
                lastName = user.lastName(),
                email = user.email(),
                adress = user.adress(),
                password = user.password(),
                cvuMercadoPago = user.cvuMercadoPago(),
                walletAdress = user.walletAdress(),
            )
    }

    fun toModel(): User {
        val user = User()
        TODO("Not yet implemented")
        return user
    }
}