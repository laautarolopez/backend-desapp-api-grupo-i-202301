package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User

class UserCreateDTO(
        var name: String? = null,
        var lastName: String? = null,
        var email: String? = null,
        var address: String? = null,
        var password: String? = null,
        var cvuMercadoPago: String? = null,
        var walletAddress: String? = null
    ) {

    fun toModel(): User {
        val user = User()
        user.name = this.name
        user.lastName = this.lastName
        user.email = this.email
        user.address = this.address
        user.password = this.password
        user.cvuMercadoPago = this.cvuMercadoPago
        user.walletAddress = this.walletAddress
        return user
    }
}
