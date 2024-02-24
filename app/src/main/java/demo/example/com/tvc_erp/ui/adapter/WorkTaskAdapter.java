package demo.example.com.tvc_erp.ui.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.ui.activity.BaseActivity;
import demo.example.com.tvc_erp.ui.activity.CreateIssueActivity;
import demo.example.com.tvc_erp.ui.activity.CreateNewsActivity;
import demo.example.com.tvc_erp.ui.activity.CreateOpportunityActivity;
import demo.example.com.tvc_erp.ui.activity.CreateTaskActivity;
import demo.example.com.tvc_erp.ui.activity.DetailActivity;
import demo.example.com.tvc_erp.ui.activity.MainActivity;
import demo.example.com.tvc_erp.ui.enums.ModelEvent;
import demo.example.com.tvc_erp.ui.objects.Task;
import demo.example.com.tvc_erp.ui.view.CirleImageView;
import demo.example.com.tvc_erp.ui.view.ExpandableTextView;
import demo.example.com.tvc_erp.utils.Config;
import demo.example.com.tvc_erp.utils.ImagePickerHelper;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Manh on 9/15/2016.
 */
//public class WorkTaskAdapter extends ArrayAdapter<WorkTask> {
public class WorkTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Task> listWorkTask;
    private BaseActivity context;
    private boolean isLoading = false, isMoreDataAvailable = true;
    private OnLoadMoreListener loadMoreListener;
    public final int TYPE_TASK = 0;
    public final int TYPE_LOAD = 1;
    private int postionFocused;
    private String taskType;
    private View viewFocused;
    private int lastPosition = -1;

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public WorkTaskAdapter(List<Task> listWorkTask, Activity context) {
        this.listWorkTask = listWorkTask;
        this.context = (BaseActivity) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_TASK) {
            return new MyViewHolder(inflater.inflate(R.layout.item_list_task, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }

    }

    @Override

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_TASK) {
            ((MyViewHolder) holder).bindData(listWorkTask.get(position), position);
            setAnimation(holder.itemView, position);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (!listWorkTask.get(position).isLoad()) {
            return TYPE_TASK;
        } else {
            return TYPE_LOAD;
        }
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    @Override
    public int getItemCount() {
        return listWorkTask.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements Observer {
        ImageView tskColor;
        TextView txtTitle, txtTaskName, txtMasterName, txtDate, txtNumCmt, txtDescription;
        LinearLayout ll_done, ll_feature, ll_accept, ll_subtask, ll_alert, ll_del, ll_comment;
        CirleImageView img_avatar;
        Button btn_download;

        public MyViewHolder(View itemView) {
            super(itemView);
            tskColor = (ImageView) itemView.findViewById(R.id.tskColor);
            img_avatar = (CirleImageView) itemView.findViewById(R.id.img_avatar);
            txtTitle = (TextView) itemView.findViewById(R.id.txtWorkName);
            txtTaskName = (TextView) itemView.findViewById(R.id.txtTaskName);
            txtMasterName = (TextView) itemView.findViewById(R.id.txtMastername);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtNumCmt = (TextView) itemView.findViewById(R.id.txtNumCmt);
            txtDescription = (ExpandableTextView) itemView.findViewById(R.id.txtDescription);
            ll_done = (LinearLayout) itemView.findViewById(R.id.ll_done);
            ll_feature = (LinearLayout) itemView.findViewById(R.id.ll_feature);
            ll_accept = (LinearLayout) itemView.findViewById(R.id.ll_accept);
            ll_subtask = (LinearLayout) itemView.findViewById(R.id.ll_subtask);
            ll_alert = (LinearLayout) itemView.findViewById(R.id.ll_alert);
            ll_del = (LinearLayout) itemView.findViewById(R.id.ll_del);
            ll_comment = (LinearLayout) itemView.findViewById(R.id.ll_comment);
            btn_download = (Button) itemView.findViewById(R.id.btn_download);

        }

        public void clearAnimation()
        {
            itemView.clearAnimation();
        }

        public void bindData(final Task workTask, final int currentPostion) {

            setViewItem(workTask.getType(), workTask, this);

            String url_avatar = Config.BASEURL + String.format(Config.API_GET_IMAGE_AVATAR, String.valueOf(workTask.getTaskMaster()));
            String firstLetter;
            if (workTask.getAuthor().equalsIgnoreCase("") || workTask.getAuthor() == null) {
                firstLetter = "N";
            } else {
                firstLetter = String.valueOf(workTask.getAuthor().charAt(0));
            }
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(workTask.getAuthor());

            TextDrawable.IBuilder builder = TextDrawable.builder()
                    .beginConfig()
                    .width(80)
                    .height(80)
                    .withBorder(4)
                    .toUpperCase()
                    .endConfig()
                    .round();
            img_avatar.setBorderColor(color);
            TextDrawable drawable = builder.build(firstLetter, color);
            Glide.with(context)
                    .load(url_avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .placeholder(drawable)
                    .into(img_avatar);

            txtTitle.setText(workTask.getTitle());
            txtMasterName.setText(workTask.getAuthor());
            txtNumCmt.setText(String.valueOf(workTask.getNumCmt()));
            txtDescription.setText(Html.fromHtml(workTask.getDetail()));

            ll_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("task", workTask);
                    context.startActivity(intent);
                }
            });

            ll_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            ll_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model.getInstance().addObserver(MyViewHolder.this);
                    Model.getInstance().callActionTask("P", workTask.getRecordID());
                    taskType = "P";
                    context.getBusyIndicator(R.string.please_wait).show();
                    postionFocused = currentPostion;
                }
            });

            ll_subtask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CreateTaskActivity.class);
                    intent.putExtra("isSubtask", true);
                    intent.putExtra("task", workTask);
                    context.startActivity(intent);

                }

            });

            ll_alert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model.getInstance().addObserver(MyViewHolder.this);
                    byte[] emptyArray = new byte[0];
                    context.getBusyIndicator(R.string.please_wait).show();
                    Model.getInstance().callAddComment("Please report your", listWorkTask.get(postionFocused).getRecordID(), emptyArray);

                }
            });

            ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model.getInstance().addObserver(MyViewHolder.this);
                    Model.getInstance().callActionTask("C", workTask.getRecordID());
                    taskType = "C";
                    context.getBusyIndicator(R.string.please_wait).show();
                    postionFocused = currentPostion;
                }
            });

            ll_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model.getInstance().addObserver(MyViewHolder.this);
                    Model.getInstance().callActionTask("D", workTask.getRecordID());
                    taskType = "D";
                    context.getBusyIndicator(R.string.please_wait).show();
                    postionFocused = currentPostion;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("task", workTask);
                    context.startActivity(intent);
                }
            });

            btn_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.getBusyIndicator(R.string.please_wait).show();
                    Model.getInstance().getDetailAttachment(workTask.getRecordID(), "");
                    setViewFocused(btn_download);
                }
            });
        }

        @Override
        public void update(Observable observable, Object object) {
            if (observable == Model.getInstance()) {
                onModelUpdated((ModelEvent) object);
            }
        }

        protected void onModelUpdated(ModelEvent evt) {

            if (evt == ModelEvent.ADD_ACTION_TASK_SUCCESS) {
                if ("D".equalsIgnoreCase(taskType)) {
                    context.getBusyIndicator(R.string.please_wait).dismiss();
                    ll_done.setVisibility(View.GONE);
                    listWorkTask.get(postionFocused).setStatus("D");
                    Model.getInstance().deleteObserver(this);
                } else if ("C".equalsIgnoreCase(taskType)) {
                    context.getBusyIndicator(R.string.please_wait).dismiss();
                    removeAt(getAdapterPosition());
                    listWorkTask.get(postionFocused).setStatus("C");
                    Model.getInstance().deleteObserver(this);
                } else if ("P".equalsIgnoreCase(taskType)) {
                    byte[] emptyArray = new byte[0];
                    Model.getInstance().callAddComment("I accepted", listWorkTask.get(postionFocused).getRecordID(), emptyArray);
                    listWorkTask.get(postionFocused).setStatus("P");
                }
            } else if (evt == ModelEvent.ADD_MESSAGE_SUCCESS) {
                context.getBusyIndicator(R.string.please_wait).dismiss();
                Model.getInstance().deleteObserver(this);
                if("P".equalsIgnoreCase(taskType)){
                    ll_accept.setVisibility(View.GONE);
                }

            }
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    private void setViewItem(String type, Task currentTask, MyViewHolder holder) {
        holder.ll_subtask.setVisibility(View.GONE);
        holder.ll_done.setVisibility(View.GONE);
        holder.ll_accept.setVisibility(View.GONE);
        holder.ll_alert.setVisibility(View.GONE);
        holder.ll_del.setVisibility(View.GONE);
        holder.btn_download.setVisibility(View.GONE);

        String userId = Model.getInstance().getLoginResponse().result.data.employee;
        String operations = Model.getInstance().getLoginResponse().result.data.operations;

        if (currentTask.getAttachCount() > 0) {
            holder.btn_download.setVisibility(View.VISIBLE);
        }

        holder.txtDate.setText(calculateDateFromPresent(currentTask.getCreateDate()));

        switch (type) {
            case "I":
                holder.tskColor.setBackgroundResource(R.color.issue);
                holder.txtTaskName.setText("Issue");
                if (operations.charAt(174) == 'Y') {
                    holder.ll_subtask.setVisibility(View.VISIBLE);
                    if (!currentTask.getStatus().equalsIgnoreCase("D")) {
                        holder.ll_done.setVisibility(View.VISIBLE);
                    }
                    holder.ll_del.setVisibility(View.VISIBLE);
                    holder.ll_alert.setVisibility(View.VISIBLE);
                }
                if (currentTask.getPic().equals(userId)) {
                    if (!currentTask.getStatus().equalsIgnoreCase("P")) {
                        holder.ll_accept.setVisibility(View.VISIBLE);
                    }
                } else if (currentTask.getTaskMaster().equals(userId)) {
                    if (!currentTask.getStatus().equalsIgnoreCase("D")) {
                        holder.ll_done.setVisibility(View.VISIBLE);
                    }
                    holder.ll_del.setVisibility(View.VISIBLE);
                    holder.ll_alert.setVisibility(View.VISIBLE);
                }
                break;
            case "T":
                holder.tskColor.setBackgroundResource(R.color.task);
                holder.txtTaskName.setText("Task");
                if (operations.charAt(174) == 'Y') {
                    holder.ll_subtask.setVisibility(View.VISIBLE);
                }

                if (currentTask.getPic().equals(userId)) {
                    if (!currentTask.getStatus().equalsIgnoreCase("P")) {
                        holder.ll_accept.setVisibility(View.VISIBLE);
                    }
                } else if (currentTask.getTaskMaster().equals(userId)) {
                    if (!currentTask.getStatus().equalsIgnoreCase("D")) {
                        holder.ll_done.setVisibility(View.VISIBLE);
                    }
                    holder.ll_del.setVisibility(View.VISIBLE);
                    holder.ll_alert.setVisibility(View.VISIBLE);
                }
                break;

            case "N":
                holder.tskColor.setBackgroundResource(R.color.news);
                holder.txtTaskName.setText("News");
                if (currentTask.getTaskMaster().equals(userId)) {
                    holder.ll_del.setVisibility(View.VISIBLE);
                }
                break;
            case "O":
                holder.txtTaskName.setText("Opportunity");
                holder.tskColor.setBackgroundResource(R.color.opportunity);
                if (currentTask.getTaskMaster().equals(userId)) {
                    holder.ll_del.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private String calculateDateFromPresent(String createDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date currentDate = new Date(System.currentTimeMillis());
        String dateInString = format.format(currentDate);
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateInString);
            d2 = format.parse(createDate);

            //in milliseconds
            long diff = d1.getTime() - d2.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            if (diffDays > 0 && diffDays < 2) {
                return String.valueOf(diffDays) + " day";
            } else if (diffDays >= 2) {
                return String.valueOf(diffDays) + " days";
            } else if (diffHours > 0 && diffHours < 2) {
                return String.valueOf(diffHours) + " hour";
            } else if (diffHours >= 2) {
                return String.valueOf(diffHours) + " hours";
            } else if (diffMinutes > 0 && diffMinutes < 2) {
                return String.valueOf(diffMinutes) + " minute";
            } else if (diffMinutes >= 2) {
                return String.valueOf(diffMinutes) + " minutes";
            } else if (diffSeconds > 0 && diffSeconds < 2) {
                return "present";
            } else {
                return String.valueOf(diffSeconds) + " seconds";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public interface OnLoadMoreListener {
        void onLoadMore();

    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void removeAt(int position) {
        listWorkTask.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listWorkTask.size());
    }

    public View getViewFocused() {
        return viewFocused;
    }

    public void setViewFocused(View viewFocused) {
        this.viewFocused = viewFocused;
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > getLastPosition())
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            setLastPosition(position);
        }
    }

    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder holder)
    {
        if(holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).clearAnimation();
        }
    }
}
