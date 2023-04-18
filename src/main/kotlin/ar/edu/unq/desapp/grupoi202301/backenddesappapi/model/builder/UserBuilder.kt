package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User

class UserBuilder {
    private var name: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var address: String? = null
    private var password: String? = null
    private var cvuMercadoPago: String? = null
    private var walletAddress: String? = null

    fun build(): User {
        var user = User()
        user.name = this.name
        user.lastName = this.lastName
        user.email = this.email
        user.address = this.address
        user.password = this.password
        user.cvuMercadoPago = this.cvuMercadoPago
        user.walletAddress = this.walletAddress
        return user
    }

    fun withName(name: String?): UserBuilder {
        this.name = name
        return this
    }

    fun withLastName(lastname: String?): UserBuilder {
        this.lastName = lastname
        return this
    }

    fun withEmail(email: String?): UserBuilder {
        this.email = email
        return this
    }

    fun withAddress(address: String?): UserBuilder {
        this.address = address
        return this
    }

    fun withPassword(password: String?): UserBuilder {
        this.password = password
        return this
    }


    fun withCVU(cvu: String?): UserBuilder {
        this.cvuMercadoPago = cvu
        return this
    }

    fun withWalletAddress(walletAddress: String?): UserBuilder {
        this.walletAddress = walletAddress
        return this
    }
}