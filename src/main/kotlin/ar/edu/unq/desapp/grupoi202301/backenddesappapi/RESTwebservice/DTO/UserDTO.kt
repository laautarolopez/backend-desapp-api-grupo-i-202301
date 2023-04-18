package ar.edu.unq.desapp.grupoi202301.backenddesappapi.RESTwebservice.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User

class UserDTO(
    val id: Long?,
    val name:String?,
    val lastName:String?,
    val email:String?,
    val address:String?,
    val password:String?,
    val cvuMercadoPago:String?,
    val walletAddress:String?,
    ) {
    
    fun toModel(): User {
        val user = User()
        user.id = this.id
        user.name = this.name
        user.lastName = this.lastName
        user.email = this.email
        user.address = this.address
        user.password = this.password
        user.cvuMercadoPago = this.cvuMercadoPago
        user.walletAddress = this.walletAddress
        return user
    }

    companion object {
        fun fromModel(user: User) =
            UserDTO(
                id = user.id,
                name = user.name,
                lastName = user.lastName,
                email = user.email,
                address = user.address,
                password = user.password,
                cvuMercadoPago = user.cvuMercadoPago,
                walletAddress = user.walletAddress
            )
    }
}