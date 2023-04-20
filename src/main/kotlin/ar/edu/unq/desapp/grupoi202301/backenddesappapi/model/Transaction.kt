package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The crypto cannot be null.")
    var crypto: CryptoName? = null

    @Column(nullable = false)
    @NotNull(message = "The quantity cannot be null.")
    @DecimalMin(value = "0.0", message = "The quantity cannot be negative.")
    var quantity: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The quotation crypto cannot be null.")
    @DecimalMin(value = "0.0", message = "The quotation crypto cannot be negative.")
    var quotationCrypto: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The amount of operation cannot be null.")
    @DecimalMin(value = "0.0", message = "The amount of operation cannot be negative.")
    var amountOperation: Double? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The user cannot be null.")
    var user: User? = null

    @Column(nullable = false)
    @NotNull(message = "The username cannot be null.")
    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 characters long.")
    var userName: String? = null

    @Column(nullable = false)
    @NotNull(message = "The user lastname cannot be null.")
    @Size(min = 3, max = 30, message = "The last name must be between 3 and 30 characters long.")
    var userLastName: String? = null

    @Column(nullable = false)
    @NotNull(message = "The numbers of operations cannot be null.")
    //@Min(value = 0, message = "The number must be equal to or greater than 0")
    var numberOperations: Int? = null
    // TODO: comprobar y validar anotacion @Min

    @Column(nullable = false)
    @Min(value = 0, message = "The number must be equal to or greater than 0")
    @NotNull(message = "The reputation cannot be null.")
    var reputation: Int? = null

    @Column(nullable = false)
    @NotNull(message = "The shipping address cannot be null.")
    @Size(min = 8, max = 22, message = "The shipping address must be 8 or 22 digits long.")
    var shippingAddress: String? = null
    // TODO: verificar e implementar bien la cantidad de digitos segun actionTransaction

    @Column(nullable = false)
    @NotNull(message = "The action cannot be null.")
    var action: ActionTransaction? = null
}