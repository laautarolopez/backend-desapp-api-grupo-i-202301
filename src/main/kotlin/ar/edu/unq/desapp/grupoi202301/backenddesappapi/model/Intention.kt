package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

class Intention {

    @OneToMany(fetch = FetchType.LAZY)
    var intentions: List<Trade>? = null

    @Column(nullable = false)
    @NotNull(message = "The quantity operations cannot be null.")
    var quantityOperations: Int? = null
}