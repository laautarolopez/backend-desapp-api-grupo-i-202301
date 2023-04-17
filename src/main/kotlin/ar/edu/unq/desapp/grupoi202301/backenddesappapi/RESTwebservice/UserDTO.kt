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
    
    fun toModel(): User {
        val user = User()
        user.id = this.id
        user.changeName(this.name!!)
        user.changeLastName(this.lastName!!)
        user.changeEmail(this.email!!)
        user.changeAdress(this.adress!!)
        user.changePassword(this.password!!)
        user.changeCVUMercadoPago(this.cvuMercadoPago!!)
        user.changeWalletAdress(this.walletAdress!!)
        return user
    }

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
                walletAdress = user.walletAdress()
            )
    }
}