package com.example.mywallet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R

/** HomeAdapter 用於管理 RecyclerView 中的項目
 * @param items 顯示在 RecyclerView 中的資料項目列表
 */
class HomeAdapter (private val items: List<MyItem>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    /** 繼承 RecyclerView.ViewHolder，並且引用 itemView 查找資料的型態
     * @param itemView 用於顯示項目內容的 View
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 清單-圖片
        val icon: ImageView = itemView.findViewById(R.id.mtrl_list_item_icon)
        // 清單-主要文字
        val primaryText: TextView = itemView.findViewById(R.id.mtrl_list_item_text)
        // 次要文字
        val secondaryText: TextView = itemView.findViewById(R.id.mtrl_list_item_secondary_text)
    }

    /** 載入 ViewHolder 介面
     * @param parent RecyclerView 的父視圖，即 RecyclerView 自身
     * @param viewType 當前項目的類型(Int)
     * @return 用於顯示項目內容的 MyViewHolder 實例
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // 創建一個新的 View 實例來顯示 RecyclerView 中的項目
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_list_item, parent, false)

        // 返回一個 MyViewHolder 實例，將剛剛建立的 View 傳遞給 MyViewHolder
        return MyViewHolder(view)
    }

    /** 將資料連結至 ViewHolder 並且進行資料控制
     * @param holder 要綁定資料的 MyViewHolder 實例
     * @param position 資料項目在清單中的位置(Int)
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // 從資料清單(RecyclerView)中獲取當前位置的資料項目
        val item = items[position]

        // 使用 item 中的資料來更新 ViewHolder 中的元素
        holder.icon.setImageResource(item.iconResId)
        holder.primaryText.text = item.primaryText
        holder.secondaryText.text = item.secondaryText
    }

    override fun getItemCount() = items.size
}