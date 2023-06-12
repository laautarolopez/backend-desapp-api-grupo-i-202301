package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.*
import jakarta.validation.constraints.Email

@Entity(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 characters long.")
    var name: String? = null

    @Column(nullable = false)
    @Size(min = 3, max = 30, message = "The last name must be between 3 and 30 characters long.")
    var lastName: String? = null

    @Column(nullable = false)
    @NotBlank(message = "The email address cannot be blank.")
    @Email(message = "A valid email address must be used.")
    var email: String? = null

    @Column(nullable = false)
    @Size(min = 10, max = 30, message = "The address must be between 10 and 30 characters long.")
    var address: String? = null

    @Column(nullable = false)
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    @Pattern(
        regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[-+_!@#\$%^&*.,? ])[a-zA-Z0-9-+_!@#\$%^&*.,? ]{0,}$",
        message = "The password must contain at least 1 lower case, 1 upper case and 1 special character."
    )
    var password: String? = null

    @Column(nullable = false)
    @Size(min = 22, max = 22, message = "The CVU must have 22 digits.")
    @Pattern(regexp = "[0-9]+", message = "The CVU must only have digits.")
    var cvuMercadoPago: String? = null

    @Column(nullable = false)
    @Size(min = 8, max = 8, message = "The wallet address must have 8 digits.")
    @Pattern(regexp = "[0-9]+", message = "The wallet address must only have digits.")
    var walletAddress: String? = null

    @Column(nullable = false)
    @Min(value = 0, message = "The number must be equal to or greater than 0.")
    private var points: Int = 0

    fun addPoints(amount: Int) {
        points += amount
    }

    fun subtractPoints(amount: Int) {
        if(points - amount <= 0) {
            points = 0
        } else {
            points -= amount
        }
    }

    @JsonIgnore
    fun getReputation(): String {
        if (operations == 0) {
            return "Without operations"
        } else {
            return (points / operations).toString()
        }
    }

    @Column(nullable = false)
    @Min(value = 0, message = "The number must be equal to or greater than 0.")
    var operations: Int = 0

    fun addOperation() {
        operations += 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (address != other.address) return false
        if (password != other.password) return false
        if (cvuMercadoPago != other.cvuMercadoPago) return false
        if (walletAddress != other.walletAddress) return false
        if (points != other.points) return false
        return operations == other.operations
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (cvuMercadoPago?.hashCode() ?: 0)
        result = 31 * result + (walletAddress?.hashCode() ?: 0)
        result = 31 * result + points
        result = 31 * result + operations
        return result
    }


}