package dk.cphbusiness.template

import android.app.ListActivity
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_people.*
import kotlinx.android.synthetic.main.item_person.view.*
import org.jetbrains.anko.db.select
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onItemClick
import org.jetbrains.anko.toast

class PeopleActivity : ListActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_people)
    }

  override fun onResume() {
    super.onResume()
    PetDbHelper.instance.use {
      select(DB.PersonTable.tableName).exec {
        listAdapter = PersonCursorAdapter(this@PeopleActivity, this, 0)
        }
      }
//    PetDbHelper.instance.use {
//      val cursor = rawQuery("select * from ${DB.PersonTable.tableName}", null)
//      listAdapter = PersonCursorAdapter(this@PeopleActivity, cursor, 0)
//    }
    }

  override fun onListItemClick(l: ListView?, v: View, position: Int, id: Long) {
    val personId = v.tag
    if (personId is Int)
        startActivity(intentFor<PersonActivity>("person_id" to personId))
    else toast("id is corrupt: ${personId}")

    }

  }


class PersonCursorAdapter(context: Context, cursor: Cursor, flags: Int) :
    CursorAdapter(context, cursor, flags) {

  override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
    return LayoutInflater
        .from(context)
        .inflate(R.layout.item_person, parent, false)
    }

  override fun bindView(view: View, context: Context, cursor: Cursor) {
    val idIndex = cursor.getColumnIndex(DB.PersonTable.id)
    view.tag = cursor.getInt(idIndex)
    val firstNameIndex = cursor.getColumnIndex(DB.PersonTable.firstName)
    view.textFirstName.setText(cursor.getString(firstNameIndex))
    val lastNameIndex = cursor.getColumnIndex(DB.PersonTable.lastName)
    view.textLastName.setText(cursor.getString(lastNameIndex))
    val emailIndex = cursor.getColumnIndex(DB.PersonTable.email)
    view.textEmail.setText(cursor.getString(emailIndex))
    }

  }




