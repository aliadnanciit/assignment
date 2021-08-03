package com.weather.exception

class ApiNetworkException(error: String): RuntimeException(error)