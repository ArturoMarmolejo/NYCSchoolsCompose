package com.arturomarmolejo.nycschoolscompose.utils

class NullSchoolListResponse(message: String = "School List response is null"): Exception(message)
class NullSatScoreResponse(message: String = "Sat Score Response is null"): Exception(message)
class FailureResponse(message: String?): Exception(message)