package dk.cphbusiness.template

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_person.view.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class PersonDbAdapter(val people: List<Person>, val activiy: PeopleActivity) :
    RecyclerView.Adapter<PersonDbAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
    val view = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.item_person, parent, false)
    val viewHolder = ViewHolder(view)
    return viewHolder
    }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    // Log.i("DBA", "pos $position")
    val person = people[position]
    holder.view.tag = person._id
    holder.view.textFirstName.text = person.firstName
    holder.view.textLastName.text = person.lastName
    holder.view.textEmail.text = person.email
    holder.view.onClick { activiy.onListItemClick(holder.view) }
    }

  override fun getItemCount() = people.size

  class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

  }