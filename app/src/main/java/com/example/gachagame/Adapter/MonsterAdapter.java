package com.example.gachagame.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.gachagame.Models.Monster;

import java.util.List;

public class MonsterAdapter extends BaseAdapter {

    private Context context;
    private List<Monster> monster_list;

    public MonsterAdapter(Context context, List<Monster> monster_list) {
        this.context = context;
        this.monster_list = monster_list;
    }

    @Override
    public int getCount() {
        return monster_list.size();
    }

    @Override
    public Object getItem(int i) {
        return monster_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
