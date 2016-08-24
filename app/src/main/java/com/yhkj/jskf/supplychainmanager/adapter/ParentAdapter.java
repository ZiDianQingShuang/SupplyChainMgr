package com.yhkj.jskf.supplychainmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class ParentAdapter<T> extends BaseAdapter {

	protected Context context;
	protected List<T> mList;
	protected LinkedList<T> mLinkedList;
	protected LayoutInflater mInflater;

	public ParentAdapter(Context context) {
		mList = new ArrayList<T>();
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}

	public ParentAdapter(Context context,LinkedList<T> linkedList) {
		super();
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mLinkedList = linkedList;
		if (null == linkedList) {
			linkedList = new LinkedList<T>();
		}
	}

	public ParentAdapter(Context context,List<T> list) {
		super();
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mList = list;
		if (null == list) {
			list = new ArrayList<T>();
		}
	}

	/**
	 * 数据格式
	 * 
	 * @author xingyimin
	 * 
	 */
	public enum TYPE {
		LIST, LINKEDLIST
	}

	public ParentAdapter(Context context,TYPE type) {
		this(context,type, null);
	}

	public ParentAdapter(Context context,TYPE type, List<T> list) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		switch (type) {
		case LIST:
			this.mList = list;
			if (null == list) {
				list = new ArrayList<T>();
			}
			break;
		case LINKEDLIST:
			if (list instanceof LinkedList) {
				this.mLinkedList = (LinkedList<T>) list;
			} else {
				mLinkedList = new LinkedList<T>();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != mList) {
			return mList.size();
		} else if (null != mLinkedList) {
			return mLinkedList.size();
		}

		return 0;
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		if (null != mList) {
			return mList.get(position);
		} else if (null != mLinkedList) {
			return mLinkedList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 设置某相数据
	 * @param t
	 */
	public void set(T t) {
		// if(t != null){
		//
		// }
		if (null != mList) {
			mList.clear();
			mList.add(t);
			notifyDataSetChanged();
		} else if (null != mLinkedList) {
			mLinkedList.clear();
			mLinkedList.add(t);
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 设置数据集合
	 * @param list
	 */
	public void set(List<T> list){
		if(null != mList){
			mList.clear();
			mList.addAll(list);
			notifyDataSetChanged();
		}else if(null != mLinkedList){
			mLinkedList.clear();
			mLinkedList.addAll(list);
			notifyDataSetChanged();
		}
	}

	/**
	 * 增
	 * 
	 * @param t
	 */
	public void add(T t) {
		if (t != null) {
			if (null != mList) {
				mList.add(t);
				notifyDataSetChanged();
			} else if (null != mLinkedList) {
				mLinkedList.add(t);
				notifyDataSetChanged();
			}

		}
	}
	
	/**
	 * 添加数据集合
	 * @param list
	 */
	public void add(List<T> list){
		if(list != null){
			if(null != this.mList){
				this.mList.addAll(list);
				notifyDataSetChanged();
			}else if(null != mLinkedList){
				this.mLinkedList.addAll(list);
				notifyDataSetChanged();
			}
		}
	}

	/**
	 * 增
	 * 
	 * @param t
	 */
	public void remove(T t) {
		if (t != null) {
			if (null != mList && !mList.isEmpty()) {
				mList.remove(t);
				notifyDataSetChanged();
			} else if (null != mLinkedList && !mLinkedList.isEmpty()) {
				mLinkedList.remove(t);
				notifyDataSetChanged();
			}

		}
	}

	/**
	 * 删
	 * 
	 * @param position
	 */
	public void remove(int position) {
		if (null != mList && !mList.isEmpty()) {
			mList.remove(position);
			notifyDataSetChanged();
		} else if (null != mLinkedList && !mLinkedList.isEmpty()) {
			mLinkedList.remove(position);
			notifyDataSetChanged();
		}
	}

	/**
	 * 改
	 * 
	 * @param position
	 * @param t
	 */
	public void update(int position, T t) {
		// TODO Auto-generated method stub
		if (null != mList && !mList.isEmpty()) {
			mList.set(position, t);
			notifyDataSetChanged();
		} else if (null != mLinkedList && !mLinkedList.isEmpty()) {
			mLinkedList.set(position, t);
			notifyDataSetChanged();
		}
	}

	/**
	 * 清空ListView
	 */
	public void clear() {
		if (null != mList && 0 != mList.size()) {
			mList.clear();
			notifyDataSetChanged();
		} else if (null != mLinkedList && 0 != mLinkedList.size()) {
			mLinkedList.clear();
			notifyDataSetChanged();
		}
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
	
//	protected T  getView(View convertView,int id) {
//		if (null == convertView || 0 <= id) {
//			return null;
//		}
//		return (T) (convertView.findViewById(id));
//	}

}
