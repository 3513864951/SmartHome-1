package com.example.smarthome.View.DeleteRecyclerViewDiy;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.RecyclerAdapter;
import com.example.smarthome.Database.Helper.AddSmartHelper;

import java.util.List;

/**
 * 项目名称：LayoutManager
 * 类描述：滑动删除的效果
 * 作者：峰哥
 * 创建时间：2016/12/24 16:35
 * 邮箱：470794349@qq.com
 * 修改简介：
 */
public class MoveDeleteTouchCallback extends ItemTouchHelper.SimpleCallback {

    private RecyclerAdapter recyclerAdapter;
    private List<AddSmartHelper> lists;

    public MoveDeleteTouchCallback(int dragDirs, int swipeDirs, RecyclerAdapter recyclerAdapter, List<AddSmartHelper> lists) {
        super(dragDirs, swipeDirs);
        this.recyclerAdapter = recyclerAdapter;
        this.lists = lists;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        recyclerAdapter.notifyDataSetChanged();
//        lists.remove(viewHolder.getLayoutPosition());


    }

}