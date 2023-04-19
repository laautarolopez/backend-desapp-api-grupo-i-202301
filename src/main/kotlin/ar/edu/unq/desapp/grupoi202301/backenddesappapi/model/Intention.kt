package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotNull

class Intention {

    @OneToMany(fetch = FetchType.LAZY)
    var intentions: List<Trade>? = null

    @Column(nullable = false)
    @NotNull(message = "The quantity operations cannot be null.")
    var quantityOperations: Int? = null
}