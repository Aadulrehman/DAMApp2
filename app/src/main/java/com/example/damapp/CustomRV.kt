package com.example.damapp

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.damapp.databinding.SimpleListBinding

class CustomRV(val listofdets:ArrayList<DataClass>,val context : Context):RecyclerView.Adapter<CustomRV.RVViewHolder>()
{
    inner class RVViewHolder(val binding:SimpleListBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun binditems(details : DataClass)
        {
            binding.actTitleRv.text = details.activityTitle
            binding.actDetRv.text = details.activityDetails
            var urii : Uri = Uri.parse(details.picture)
            binding.imgtv.setImageURI(urii)
        }

        // var titleofact: TextView = singleRow.findViewById(R.id.act_title_rv)
        // var detailact: TextView = singleRow.findViewById(R.id.act_det_rv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val binding= SimpleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RVViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.binditems(listofdets[position])
    }
    override fun getItemCount(): Int {
        return listofdets.size
    }


}