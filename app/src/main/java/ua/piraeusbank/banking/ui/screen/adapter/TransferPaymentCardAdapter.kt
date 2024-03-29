package ua.piraeusbank.banking.ui.screen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.bank_card_layout.view.*
import ua.piraeusbank.banking.R
import ua.piraeusbank.banking.ui.model.PaymentCard

class TransferPaymentCardAdapter(context: Context,
                                 private val viewResourceId: Int,
                                 private val dropDownViewResourceId: Int,
                                 private val cards: List<PaymentCard>) :
    ArrayAdapter<PaymentCard>(context, viewResourceId, cards) {

    override fun getCount(): Int {
        return cards.size
    }

    override fun getItem(position: Int): PaymentCard {
        return cards[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup
    ) = getCustomView(convertView, parent, position, viewResourceId)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup) =
        getCustomView(convertView, parent, position, dropDownViewResourceId)

    private fun getCustomView(
        convertView: View?,
        parent: ViewGroup,
        position: Int,
        viewResourceId: Int
    ): View {
        val viewHolder: ViewHolder
        val view = if (convertView == null) {
            val innerView = LayoutInflater
                .from(parent.context)
                .inflate(viewResourceId, parent, false)
            viewHolder = ViewHolder(innerView)
            innerView.tag = viewHolder
            innerView
        } else {
            viewHolder = convertView.tag as ViewHolder
            convertView
        }

        viewHolder.cardName.text = cards[position].cardName
        viewHolder.cardNumber.text = "*${cards[position].cardNumber}"
        viewHolder.cardType?.setImageResource(
            when (cards[position].cardType) {
                PaymentCard.Type.VISA -> R.drawable.ic_visa
                PaymentCard.Type.MASTERCARD -> R.drawable.ic_mastercard
            }
        )
        viewHolder.moneyAmount.text = cards[position].moneyAmount

        return view
    }

    private class ViewHolder(view: View) {
        val cardName: TextView = view.cardName
        val cardNumber: TextView = view.cardNumber
        val cardType: ImageView? = view.cardType
        val moneyAmount: TextView = view.moneyAmount
    }

}
