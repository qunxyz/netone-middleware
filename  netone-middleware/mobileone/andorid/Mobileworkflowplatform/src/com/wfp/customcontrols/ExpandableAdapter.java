package com.wfp.customcontrols;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.wfp.activity.R;
public class ExpandableAdapter extends BaseExpandableListAdapter { 
                
        private List<String> groupArray; 
        private List<List<String>> childArray; 
        private Activity activity; 
        public ExpandableAdapter(Activity a,List<String> groupArray,List<List<String>> childArray) { 
                activity = a; 
                this.groupArray = groupArray; 
                this.childArray = childArray; 
        } 
        
        @Override 
        public Object getChild(int groupPosition, int childPosition) { 
                // TODO Auto-generated method stub 
                return childArray.get(groupPosition).get(childPosition); 
        } 
        @Override 
        public long getChildId(int groupPosition, int childPosition) { 
                // TODO Auto-generated method stub 
                return childPosition; 
        } 
        @Override 
        public View getChildView(int groupPosition, int childPosition, 
                        boolean isLastChild, View convertView, ViewGroup parent) { 
                // TODO Auto-generated method stub 
        		String catname = childArray.get(groupPosition).get(childPosition);
                return getGenericView("• "+catname, "child"); 
        } 
        @Override 
        public int getChildrenCount(int groupPosition) { 
                // TODO Auto-generated method stub 
				return childArray.get(groupPosition).size(); 
        } 
        @Override 
        public Object getGroup(int groupPosition) { 
                // TODO Auto-generated method stub 
                return groupArray.get(groupPosition); 
        } 
        @Override 
        public int getGroupCount() { 
                // TODO Auto-generated method stub 
                return groupArray.size(); 
        } 
        @Override 
        public long getGroupId(int groupPosition) { 
                // TODO Auto-generated method stub 
                return groupPosition; 
        } 
        @Override 
        public View getGroupView(int groupPosition, boolean isExpanded, 
                        View convertView, ViewGroup parent) { 
                // TODO Auto-generated method stub 
                String string = groupArray.get(groupPosition); 
                return getGenericView(string, "group"); 
        } 
        @Override 
        public boolean hasStableIds() { 
                // TODO Auto-generated method stub 
                return false; 
        } 
        @Override 
        public boolean isChildSelectable(int groupPosition, int childPosition) { 
                // TODO Auto-generated method stub 
                return true; 
        } 
        
/****************************************以下为自定义方法*********************************************/        
        /** 
         * Children 's View 
         * @param string 
         * @return 
         */ 
        public TextView getGenericView(String string ,String flag) { 
        		TextView text = text = new TextView(activity); 
        		AbsListView.LayoutParams layoutParams = null;
        		if(flag.equals("group")) {
	                layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 50);
	                // Set the text starting position 
	                text.setPadding(35, 0, 0, 0); 
	                text.setTextSize(18);
	                text.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.channel_tree_group_bg));
        		} else {
        			layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 40);
	                // Set the text starting position 
	                text.setPadding(70, 0, 0, 0); 
	                text.setTextSize(16);
        		}
        		text.setLayoutParams(layoutParams); 
        		// Center the text vertically 
                text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        		text.setTextColor(R.drawable.gray);
        		text.setText(string); 
                return text; 
        } 
}