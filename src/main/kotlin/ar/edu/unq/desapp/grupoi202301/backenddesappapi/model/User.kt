package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import java.lang.RuntimeException

@Entity
class User() {

    // "https://objetivoligar.com/wp-content/uploads/2017/03/blank-profile-picture-973460_1280-580x580.jpg"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(nullable = false, length = 500)
    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 30, message = "{validation.name.size.too_long}")
    var name: String? = null
    @Size(min = 3, message = "{validation.lastName.size.too_short}")
    @Size(max = 200, message = "{validation.lastName.size.too_long}")
    var lastName: String? = null
    @Column(unique = true)
    @Email
    var email: String? = null
    @Size(min = 10, message = "{validation.direction.size.too_short}")
    @Size(max = 30, message = "{validation.direction.size.too_long}")
    var adress: String? = null
    @Size(min = 10, message = "{validation.password.size.too_short}")
    var password: String? = null
    var cvuMercadoPago: String? = null
    // Se debe validar que sean 8 digitos
    var walletAdress: String? = null

    constructor(name: String, lastName: String, email: String, adress: String, password: String, cvu: String, walletAdress: String):this() {
        changeName(name)
        changeLastName(lastName)
        changeEmail(email)
        changeAdress(adress)
        changePassword(password)
        changeCVUMercadoPago(cvu)
        changeWalletAdress(walletAdress)
    }

    fun changeName(name: String) {
        if(name.length < 3 || name.length > 30) {
            throw RuntimeException("El nombre debe tener entre 3 y 30 caracteres.")
        }
        this.name = name
    }

    fun changeLastName(lastName: String) {
        if(lastName.length < 3 || lastName.length > 30) {
            throw RuntimeException("El apellido debe tener entre 3 y 30 caracteres.")
        }
        this.lastName = lastName
    }

    fun changeEmail(email: String) {
        // TODO se debe verificar que tenga formato de email

        this.email = email
    }

    fun changeAdress(adress: String) {
        if(adress.length < 10 || adress.length > 30) {
            throw RuntimeException("La dirección debe tener entre 10 y 30 caracteres.")
        }
        this.adress = adress
    }

    fun changePassword(password: String) {
        // TODO al menos 1 minuscula, 1 mayuscula, 1 carac especial y min 6

        this.password = password
    }

    fun changeCVUMercadoPago(cvu: String) {
        // TODO se debe validar que sean digitos
        if(cvu.length != 22) {
            throw RuntimeException("El CVU debe tener 22 dígitos.")
        }

        this.cvuMercadoPago = cvu
    }

    fun changeWalletAdress(walletAdress: String) {
        // TODO se debe validar que sean digitos
        if(walletAdress.length != 8) {
            throw RuntimeException("La dirección de la billetera debe tener 8 dígitos.")
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
}