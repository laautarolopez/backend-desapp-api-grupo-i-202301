package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.Pattern

@Entity
class User() {

    @Id
    private var id: Long? = null
    @OneToOne
    private var name: Name? = null
    @OneToOne
    private var lastName: LastName? = null
    @OneToOne
    private var email: Email? = null
    @OneToOne
    var adress: Adress? = null
    @OneToOne
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*().,<>{}[\\]<>?_=+\\-|;:\\'\\\"\\/]])(?!.*\\s).{8,20}$")
    var password: Password? = null
    @OneToOne
    var cvuMercadoPago: CVUMercadoPago? = null
    @OneToOne
    var walletAdress: WalletAdress? = null

    constructor(name: String, lastName: String, email: String, adress: String, password: String, cvu: String, walletAdress: String):this() {
        this.name = Name(name)
        this.lastName = LastName(lastName)
        this.email = Email(email)
        this.adress = Adress(adress)
        this.password = Password(password)
        this.cvuMercadoPago = CVUMercadoPago(cvu)
        this.walletAdress = WalletAdress(walletAdress)
    }

    fun name(): String = this.name!!.name()

    fun changeName(newName: String) {
        this.name!!.changeName(newName)
    }

    fun lastName(): String = this.lastName!!.lastName()

    fun changeLastName(newLastName: String) {
        this.lastName!!.changeLastName(newLastName)
    }

    fun email(): String = this.email!!.email()

    fun changeEmail(newEmail: String) {
        // TODO se debe verificar que tenga formato de email
        this.email!!.changeEmail(newEmail)
    }

    fun adress(): String = this.adress!!.adress()

    fun changeAdress(newAdress: String) {
        this.adress!!.changeAdress(newAdress)
    }

    fun password(): String = this.password!!.password()

    fun changePassword(newPassword: String) {
        // TODO al menos 1 minuscula, 1 mayuscula, 1 caracter especial y minimo 6
        this.password!!.changePassword(newPassword)
    }

    fun cvuMercadoPago(): String = this.cvuMercadoPago!!.cvuMercadoPago()

    fun changeCVUMercadoPago(newCVU: String) {
        this.cvuMercadoPago!!.changeCVUMercadoPago(newCVU)
    }

    fun walletAdress(): String = this.walletAdress!!.walletAdress()

    fun changeWalletAdress(newWalletAdress: String) {
        this.walletAdress!!.changeWalletAdress(newWalletAdress)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (adress != other.adress) return false
        if (password != other.password) return false
        if (cvuMercadoPago != other.cvuMercadoPago) return false
        if (walletAdress != other.walletAdress) return false

        return true
    }
}