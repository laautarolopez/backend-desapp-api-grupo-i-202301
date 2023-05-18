package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity(name = "transactions")
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(nullable = false)
    @NotNull(message = "The amount of operation cannot be null.")
    @DecimalMin(value = "0.0", message = "The amount of operation cannot be negative.")
    var amountOperation: Double? = null
    // TODO: Revisar si atributo esta de mas

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The trade cannot be null.")
    var trade: Trade? = null

    @Column(nullable = false)
    @NotNull(message = "The shipping address cannot be null.")
    @Size(min = 8, max = 22, message = "The shipping address must be 8 or 22 digits long.")
    var shippingAddress: String? = null

    @Column(nullable = false)
    @NotNull(message = "The action cannot be null.")
    var action: ActionTransaction? = null
    // TODO: validar tipo de operacion
}