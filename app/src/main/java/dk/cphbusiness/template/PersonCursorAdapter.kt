package dk.cphbusiness.template

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import kotlinx.android.synthetic.main.item_person.view.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class PersonCursorAdapter(context: Context, cursor: Cursor, flags: Int) :
    CursorAdapter(context, cursor, flags) {

  override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
    return LayoutInflater
        .from(context)
        .inflate(R.layout.item_person, parent, false)
    }

  override fun bindView(view: View, context: Context, cursor: Cursor) {
    view.tag = cursor.getInt(DB.PersonTable.id)
    // view.textFirstName.setText(cursor.getString(cursor.getColumnIndex(DB.PersonTable.firstName)))
    view.textFirstName.setText(cursor.getString(DB.PersonTable.firstName))
    view.textLastName.setText(cursor.getString(DB.PersonTable.lastName))
    view.textEmail.setText(cursor.getString(DB.PersonTable.email))
//    view.theButton.onClick {
//      context.toast("${view.textFirstName.text} is cute")
//      }
    }

  }

