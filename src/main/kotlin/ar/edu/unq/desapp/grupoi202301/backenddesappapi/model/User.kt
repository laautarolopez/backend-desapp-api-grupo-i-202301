package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model
import jakarta.persistence.*
import jakarta.validation.constraints.Size

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
    var email: String? = null
    @Size(min = 10, message = "{validation.direction.size.too_short}")
    @Size(max = 30, message = "{validation.direction.size.too_long}")
    var direction: String? = null
    @Size(min = 10, message = "{validation.password.size.too_short}")
    var password: String? = null
    var CVUPaymentMarket: Long? = null

    constructor(name: String, lastName: String, email: String, direction: String, password: String):this() {
        this.name = name
        this.lastName = lastName
        this.email = email
        this.direction = direction
        this.password = password
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (direction != other.direction) return false
        if (password != other.password) return false
        if (CVUPaymentMarket != other.CVUPaymentMarket) return false

        return true
    }
}