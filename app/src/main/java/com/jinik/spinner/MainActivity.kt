package com.jinik.spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jinik.spinner.databinding.ActivityMainBinding
import com.jinik.spinner.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) { //엔트리 포인트
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 1. 데이터를 불러온다.
        val data = loadData()
        // 2. 아답터를 생성한다.
        val customAdapter = CustomerAdapter(data)
        // 3. 화면의 RecyclerView와 연결
        binding.recyclerView.adapter = customAdapter
        // 4. 레이아웃 매니저 설정
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun loadData() : MutableList<Memo>{
        val memoList = mutableListOf<Memo>()

        for(no in 1..100){
            val title = "이것이 안드로이드다--$no"
            val date = System.currentTimeMillis()
            val memo = Memo(no, title, date)
            memoList.add(memo)
        }
        return memoList
    }
}

class CustomerAdapter(val listData: MutableList<Memo>): RecyclerView.Adapter<CustomerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false) //항상 이렇게 씀.
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 1. 사용할 데이터를 꺼내고
        val memo = listData.get(position)
        // 2. 홀더에 데이터를 전달
        holder.setMemo(memo)
    }

    override fun getItemCount() = listData.size

    class Holder(val binding:ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        lateinit var currentMemo: Memo
        // 클릭 처리는 init에서만 해야한다.
        init{
            binding.root.setOnClickListener{
                val title = binding.textTitle.text
                Toast.makeText(binding.root.context,"클릭된 아이템: ${currentMemo.title}", Toast.LENGTH_SHORT).show()
            }
        }
        // 3. 받은 데이터를 화면에 출력한다.
        fun setMemo(memo: Memo){

            currentMemo = memo

            with(binding){
                textNo.text = "${memo.no}"
                textTitle.text = memo.title

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val foramttedDate = sdf.format(memo.timestamp)
                textDate.text = foramttedDate
            }
        }
    }
}