package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import java.lang.RuntimeException

@Entity
class User() {

    // "https://objetivoligar.com/wp-content/uploads/2017/03/blank-profile-picture-973460_1280-580x580.jpg"
    @Id
    private var id: Long? = null
    @OneToOne
    private var name: Name? = null
    @OneToOne
    private var lastName: LastName? = null
    @OneToOne
    private var email: Email? = null
    var adress: String? = null
    var password: String? = null
    var cvuMercadoPago: String? = null
    var walletAdress: String? = null

    constructor(name: String, lastName: String, email: String, adress: String, password: String, cvu: String, walletAdress: String):this() {
        this.name = Name(name)
        this.lastName = LastName(lastName)
        this.email = Email(email)
        changeAdress(adress)
        changePassword(password)
        changeCVUMercadoPago(cvu)
        changeWalletAdress(walletAdress)
    }

    fun name(): String = this.name!!.name()

    fun changeName(name: String) {
        this.name!!.changeName(name)
    }

    fun lastName(): String = this.lastName!!.lastName()

    fun changeLastName(lastName: String) {
        this.lastName!!.changeLastName(lastName)
    }

    fun email(): String = this.email!!.email()

    fun changeEmail(email: String) {
        // TODO se debe verificar que tenga formato de email
        this.email!!.changeEmail(email)
    }

    fun changeAdress(adress: String) {
        if(adress.length < 10 || adress.length > 30) {
            throw RuntimeException("The address must be between 10 and 30 characters long.")
        }
        this.adress = adress
    }

    fun changePassword(password: String) {
        // TODO al menos 1 minuscula, 1 mayuscula, 1 carac especial y min 6
        this.password = password
    }

    fun isNumeric(s: String): Boolean {
        return s.chars().allMatch { Character.isDigit(it) }
    }

    fun changeCVUMercadoPago(cvu: String) {
        if(!isNumeric(cvu)) {
            throw RuntimeException("The CVU must only have digits.")
        }
        else if(cvu.length != 22) {
            throw RuntimeException("The CVU must have 22 digits.")
        }

        this.cvuMercadoPago = cvu
    }

    fun changeWalletAdress(walletAdress: String) {
        if(!isNumeric(walletAdress)) {
            throw RuntimeException("The wallet adress must only have digits.")
        }
        else if(walletAdress.length != 8) {
            throw RuntimeException("The wallet address must be 8 digits long.")
        }
        this.walletAdress = walletAdress
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