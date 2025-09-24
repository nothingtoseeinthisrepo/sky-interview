package com.test.interview.feature.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(

) : ViewModel() {


}

sealed class ValidationResult {
    data object Valid : ValidationResult()
    sealed class Error : ValidationResult() {
        data object TooShort : Error()
        sealed class RuleUnmatched(rules: List<Rule>) : Error() {
            sealed class Rule {
                data object MissingUpperCase : Error()
                data object MissingLowerCase : Error()
                data object MissingDigit : Error()
                data object MissingSpecialSymbol : Error()
            }
        }
        data object IdenticalToOldPasswords : Error()
    }
}