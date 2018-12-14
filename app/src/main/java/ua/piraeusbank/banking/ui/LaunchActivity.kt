package ua.piraeusbank.banking.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationMessageHandler
import ua.piraeusbank.banking.R
import ua.piraeusbank.banking.ui.extensions.currentScreen
import ua.piraeusbank.banking.ui.extensions.openScreen
import ua.piraeusbank.banking.ui.navigation.BackMessage
import ua.piraeusbank.banking.ui.navigation.UserHasAuthorizedMessage
import ua.piraeusbank.banking.ui.navigation.ViewAllCardsMessage
import ua.piraeusbank.banking.ui.navigation.ViewBankCardMessage
import ua.piraeusbank.banking.ui.screen.AccountScreen
import ua.piraeusbank.banking.ui.screen.PaymentCardScreen
import ua.piraeusbank.banking.ui.screen.LoginScreen
import ua.piraeusbank.banking.ui.screen.MainScreen
import ua.piraeusbank.banking.ui.screen.base.BackHandler

class LaunchActivity : AppCompatActivity(), NavigationMessageHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.openScreen(LoginScreen(), addToBackStack = false)
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.currentScreen?.let {
            if (it is BackHandler && it.handleBack()) return
        }

        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }
    }

    override fun handleNavigationMessage(message: NavigationMessage): Boolean {

        val sfm = supportFragmentManager

        when (message) {

            is BackMessage -> super.onBackPressed()

            is UserHasAuthorizedMessage -> sfm.openScreen(MainScreen.create(), addToBackStack = false)

            is ViewAllCardsMessage -> sfm.openScreen(AccountScreen.create())

            is ViewBankCardMessage -> sfm.openScreen(PaymentCardScreen.create())

        }
        return true
    }
}