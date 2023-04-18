package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.Pattern

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    var id: Long? = null
    @OneToOne
    @JsonProperty
    private var name: Name? = null
    @OneToOne
    @JsonProperty
    private var lastName: LastName? = null
    @OneToOne
    @JsonProperty
    private var email: Email? = null
    @OneToOne
    @JsonProperty
    var adress: Adress? = null
    @OneToOne
    @JsonProperty
    var password: Password? = null
    @OneToOne
    @JsonProperty
    var cvuMercadoPago: CVUMercadoPago? = null
    @OneToOne
    @JsonProperty
    var walletAdress: WalletAdress? = null

    constructor() {
        this.name = Name()
        this.lastName = LastName()
        this.email = Email()
        this.adress = Adress()
        this.password = Password()
        this.cvuMercadoPago = CVUMercadoPago()
        this.walletAdress = WalletAdress()
    }

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

    fun changeName(newName: String) = this.name!!.changeName(newName)

    fun lastName(): String = this.lastName!!.lastName()

    fun changeLastName(newLastName: String) = this.lastName!!.changeLastName(newLastName)

    fun email(): String = this.email!!.email()

    fun changeEmail(newEmail: String) = this.email!!.changeEmail(newEmail)

    fun adress(): String = this.adress!!.adress()

    fun changeAdress(newAdress: String) = this.adress!!.changeAdress(newAdress)

    fun password(): String = this.password!!.password()

    fun changePassword(newPassword: String) = this.password!!.changePassword(newPassword)

    fun cvuMercadoPago(): String = this.cvuMercadoPago!!.cvuMercadoPago()

    fun changeCVUMercadoPago(newCVU: String) = this.cvuMercadoPago!!.changeCVUMercadoPago(newCVU)

    fun walletAdress(): String = this.walletAdress!!.walletAdress()

    fun changeWalletAdress(newWalletAdress: String) = this.walletAdress!!.changeWalletAdress(newWalletAdress)

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

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (adress?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (cvuMercadoPago?.hashCode() ?: 0)
        result = 31 * result + (walletAdress?.hashCode() ?: 0)
        return result
    }
}