package dk.cphbusiness.template

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_person.*
import kotlinx.android.synthetic.main.item_person.*
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class PersonActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_person)
    val personId = intent.getIntExtra("person_id", -1)
    val sql = "${DB.PersonTable.id} = {personId}"
    okButton.onClick {
      if (personId < 0) { }
      else {
        PetDbHelper.instance.use {
          update(DB.PersonTable.tableName,
              DB.PersonTable.firstName to editFirstName.text.toString(),
              DB.PersonTable.lastName to editLastName.text.toString(),
              DB.PersonTable.email to editEmail.text.toString()
              )
              .where(sql, "personId" to personId)
              .exec()
          }
        }
      finish()
      }

    if (personId < 0) {
      toast("New person")
      }
    else {
      PetDbHelper.instance.use {

        select(DB.PersonTable.tableName)
          .where(sql, "personId" to personId)
          .exec {
            moveToFirst()
            editFirstName.setText(getString(getColumnIndex(DB.PersonTable.firstName)))
            editLastName.setText(getString(getColumnIndex(DB.PersonTable.lastName)))
            editEmail.setText(getString(getColumnIndex(DB.PersonTable.email)))
            }
        }
      }


  }

}
