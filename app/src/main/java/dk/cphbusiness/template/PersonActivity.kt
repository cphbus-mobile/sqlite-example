package dk.cphbusiness.template

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_person.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class PersonActivity : Activity() {
  lateinit var person: Person;

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
//    Disse linier beskriver en inner join (ikke særligt smukt...)
//    PetDbHelper.instance.use {
//      val data =
//          select(DB.PersonTable.tableName+","+DB.PetTable.tableName,
//              DB.PersonTable.firstName,
//              DB.PetTable.name)
//            .where("${DB.PersonTable.tableName}.${DB.PersonTable.id} = ${DB.PetTable.owner}")
//            .parseList(rowParser { fn: String, n: String -> Pair(fn, n) })
//      longToast("${data}")
//      }
    if (personId < 0) {
      toast("New person")
      }
    else {
      PetDbHelper.instance.use {
        // val cursor = rawQuery("select * from PEOPLE where _id = ${personId}")
//        select(DB.PersonTable.tableName)
//          .where(sql, "personId" to personId)
//          .exec {
//            parseSingle(rowParser {
//              id: Int, firstName: String, lastName: String, email: String ->
//              editFirstName.setText(firstName)
//              editLastName.setText(lastName)
//              editEmail.setText(email)
//              })
//            }
        person = select(DB.PersonTable.tableName)
            .where(sql, "personId" to personId)
            .parseSingle(
//                classParser<Person>())
                rowParser {
                  id: Int, firstName: String, lastName: String, email: String ->
                  Person(id, firstName, lastName, email)
                  }
                )
            }
        editFirstName.setText(person.firstName)
        editLastName.setText(person.lastName)
        editEmail.setText(person.email)
        }
      }


  }



