package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.lang.RuntimeException

@Entity
class WalletAdress() {
    @Id
    private var id: Long? = null
    private var walletAdress: String? = null

    constructor(walletAdress: String) : this() {
        changeWalletAdress(walletAdress)
    }

    fun walletAdress(): String = this.walletAdress!!

    fun changeWalletAdress(walletAdress: String) {
        if(!isNumeric(walletAdress)) {
            throw RuntimeException("The wallet adress must only have digits.")
        }
        else if(isDifferentFrom(walletAdress,8)) {
            throw RuntimeException("The wallet address must be 8 digits long.")
        }
        this.walletAdress = walletAdress
    }

    private fun isNumeric(s: String): Boolean {
        return s.chars().allMatch { Character.isDigit(it) }
    }

    private fun isDifferentFrom(cvu: String, cantidad: Int) : Boolean = cvu.length != cantidad
}