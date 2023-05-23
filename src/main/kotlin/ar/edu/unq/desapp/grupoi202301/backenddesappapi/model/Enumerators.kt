package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model

enum class CryptoName {
    ALICEUSDT,
    MATICUSDT,
    AXSUSDT,
    AAVEUSDT,
    ATOMUSDT,
    NEOUSDT,
    DOTUSDT,
    ETHUSDT,
    CAKEUSDT,
    BTCUSDT,
    BNBUSDT,
    ADAUSDT,
    TRXUSDT,
    AUDIOUSDT
}

enum class OperationType {
    BUY,
    SALE
}

enum class TransactionStatus {
    CREATED,
    TRANSFERRED,
    CONFIRMED,
    CANCELED
}