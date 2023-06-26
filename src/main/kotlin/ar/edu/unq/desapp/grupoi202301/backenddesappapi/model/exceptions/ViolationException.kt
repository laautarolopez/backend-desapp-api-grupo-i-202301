package ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.exceptions

open class ViolationException(val attribute: String, message: String) : RuntimeException(message)

class TransactionException(attribute: String, message: String) : ViolationException(attribute, message)

class TradeNonExistentException(attribute: String, message: String) : ViolationException(attribute, message)
class QuoteNonExistentException(attribute: String, message: String) : ViolationException(attribute, message)

class UnauthorizedException(val attribute: String, message: String): RuntimeException(message)