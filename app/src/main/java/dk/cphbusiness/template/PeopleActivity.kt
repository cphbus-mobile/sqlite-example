package dk.cphbusiness.template

import android.app.ListActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class PeopleActivity : ListActivity(), AnkoLogger {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_people)
    }

  override fun onResume() {
    super.onResume()
//    PetDbHelper.instance.use {
//      select(DB.PersonTable.tableName).exec {
//        listAdapter = PersonXCursorAdapter(this@PeopleActivity, this, 0)
//        }
//      }
    PetDbHelper.instance.use {
      val cursor = rawQuery("select * from ${DB.PersonTable.tableName}", null)
      listAdapter = PersonCursorAdapter(this@PeopleActivity, cursor, 0)
      //cursor.close()
      }
    }

  override fun onListItemClick(l: ListView?, v: View, position: Int, id: Long) {
    val personId = v.tag
    if (personId is Int)
        startActivity(intentFor<PersonActivity>("person_id" to personId))
    else toast("id is corrupt: ${personId}")
    }

  fun addPersonButtonClicked(view: View) {
    info("Der blev klikket på knappen")
    Log.i("XYZ", "Mere klikkeri")
    startActivity(intentFor<PersonActivity>("person_id" to -1))
    }

  }





