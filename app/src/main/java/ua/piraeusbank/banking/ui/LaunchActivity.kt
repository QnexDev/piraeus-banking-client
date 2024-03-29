package ua.piraeusbank.banking.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationMessageHandler
import ua.piraeusbank.banking.R
import ua.piraeusbank.banking.ui.extensions.currentScreen
import ua.piraeusbank.banking.ui.extensions.openScreen
import ua.piraeusbank.banking.ui.model.BankServiceKind
import ua.piraeusbank.banking.ui.navigation.*
import ua.piraeusbank.banking.ui.screen.*
import ua.piraeusbank.banking.ui.screen.base.BackHandler
import ua.piraeusbank.banking.ui.screen.base.Screen

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

            is SelectBankServiceMessage -> sfm.openScreen(selectBankServiceScreen(message))

            is ShowMoreBankServicesMessage -> sfm.openScreen(BankServicesScreen.create())

            is StartRegistrationMessage -> sfm.openScreen(RegistrationScreen.create())
        }
        return true
    }

    private fun selectBankServiceScreen(message: SelectBankServiceMessage): Screen<*> {
        return when (message.bankServiceKind) {
            BankServiceKind.CARDS_MENU -> CardMenuScreen.create()
            BankServiceKind.MONEY_TRANSFER -> MoneyTransferScreen.create()
            BankServiceKind.SETTINGS -> AppSettingsScreen.create()
        }
    }
}