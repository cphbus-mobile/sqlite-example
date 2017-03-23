package dk.cphbusiness.template

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class PetDbHelper(context: Context = App.instance) : ManagedSQLiteOpenHelper(
    context,
    DB.name,
    null,
    8) {
  companion object {
    val instance by lazy { PetDbHelper() }
    }

  override fun onCreate(db: SQLiteDatabase) {
    db.createTable(DB.PersonTable.tableName, true,
        DB.PersonTable.id to INTEGER + PRIMARY_KEY,
        DB.PersonTable.firstName to TEXT,
        DB.PersonTable.lastName to TEXT,
        DB.PersonTable.email to TEXT
        )
    db.createTable(DB.PetTable.tableName, true,
        DB.PetTable.id to INTEGER + PRIMARY_KEY,
        DB.PetTable.owner to INTEGER,
        DB.PetTable.name to TEXT,
        DB.PetTable.age to INTEGER
        )
    initData(db)
    }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.dropTable(DB.PetTable.tableName)
    db.dropTable(DB.PersonTable.tableName)
    onCreate(db)
    }

  }

object DB {
  val name = "PetDb"
  object PersonTable {
    val tableName = "PEOPLE"
    val id = "_id"
    val firstName = "first_name"
    val lastName = "last_name"
    val email = "email"
    }
  object PetTable {
    val tableName = "PETS"
    val id = "_id"
    val owner = "owner_id"
    val name = "name"
    val age = "age"
    }
  }

fun initData(db: SQLiteDatabase) {
  db.insert(DB.PersonTable.tableName,
      DB.PersonTable.id to 1,
      DB.PersonTable.firstName to "Kurt",
      DB.PersonTable.lastName to "Hansen",
      DB.PersonTable.email to "kurt@hansen.dk"
      )
  db.insert(DB.PersonTable.tableName,
      DB.PersonTable.id to 2,
      DB.PersonTable.firstName to "Sonja",
      DB.PersonTable.lastName to "Jensen",
      DB.PersonTable.email to "sonja@mail.dk"
      )
  db.insert(DB.PersonTable.tableName,
      DB.PersonTable.id to 3,
      DB.PersonTable.firstName to "Ib",
      DB.PersonTable.lastName to "Hansen",
      DB.PersonTable.email to "ibbermand@mail.dk"
      )
  db.insert(DB.PersonTable.tableName,
      DB.PersonTable.id to 4,
      DB.PersonTable.firstName to "Karl",
      DB.PersonTable.lastName to "Kristjansen",
      DB.PersonTable.email to "karl@mail.dk"
      )
  db.insert(DB.PetTable.tableName,
      DB.PetTable.id to 100,
      DB.PetTable.name to "Felix",
      DB.PetTable.age to 3,
      DB.PetTable.owner to 2)
  db.insert(DB.PetTable.tableName,
      DB.PetTable.id to 101,
      DB.PetTable.name to "Misser",
      DB.PetTable.age to 4,
      DB.PetTable.owner to 2)
  }

fun Cursor.getString(columnName: String) =
    this.getString(this.getColumnIndex(columnName))

// Same as:
// fun getString(cursor: Cursor, columnName: String) =
//    cursor.getString(cursor.getColumnIndex(columnName))

fun Cursor.getInt(columnName: String) =
    this.getInt(this.getColumnIndex(columnName))
