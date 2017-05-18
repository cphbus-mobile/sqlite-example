package dk.cphbusiness.template

import android.app.Activity
import android.app.ListActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_people_rec.*
import org.jetbrains.anko.*

class PeopleActivity : Activity(), AnkoLogger {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_people_rec)
    peopleList.layoutManager = LinearLayoutManager(this)
    }

  override fun onResume() {
    super.onResume()
//    PetDbHelper.instance.use {
//      select(DB.PersonTable.tableName).exec {
//        listAdapter = PersonXCursorAdapter(this@PeopleActivity, this, 0)
//        }
//      }
    peopleList.adapter = PersonDbAdapter(PetDbHelper.instance.listPeople(), this)
    }


//    override fun onListItemClick(l: ListView?, v: View, position: Int, id: Long) {
//      val personId = v.tag
//      if (personId is Int)
//          startActivity(intentFor<PersonActivity>("person_id" to personId))
//      else toast("id is corrupt: ${personId}")
//      }

    fun onListItemClick(v: View) {
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





