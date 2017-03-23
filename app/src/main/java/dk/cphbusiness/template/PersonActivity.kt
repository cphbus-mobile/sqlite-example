package dk.cphbusiness.template

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_person.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class PersonActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_person)
    val personId = intent.getIntExtra("person_id", -1)
    val sql = "${DB.PersonTable.id} = {personId}"
    okButton.onClick {
      if (personId < 0) {
        PetDbHelper.instance.use {
          transaction {
            val maxId = select(DB.PersonTable.tableName, "max(_id)").parseSingle(IntParser)
            insert(DB.PersonTable.tableName,
                DB.PersonTable.id to maxId + 1,
                DB.PersonTable.firstName to editFirstName.text.toString(),
                DB.PersonTable.lastName to editLastName.text.toString(),
                DB.PersonTable.email to editEmail.text.toString()
                )
            }
          }
        }
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
            parseSingle(rowParser {
              id: Int, firstName: String, lastName: String, email: String ->
              editFirstName.setText(firstName)
              editLastName.setText(lastName)
              editEmail.setText(email)
              })
            }
        }
      }


  }

}

