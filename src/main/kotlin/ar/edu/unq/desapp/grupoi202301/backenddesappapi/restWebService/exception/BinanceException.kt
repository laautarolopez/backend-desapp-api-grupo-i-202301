package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.exception

class BinanceResponseException(val cryptoName: String, val newMessage: String) : RuntimeException(newMessage)

class BinanceServerException(val newMessage: String) : RuntimeException(newMessage)