package demo.example.com.tvc_erp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.api.object_response_api.GetCommentTaskResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetListResponse;
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

/**
 * Created by prosoft on 10/4/16.
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GetCommentTaskResponse.Data> commentList;
    private Task task;
    private DetailActivity context;
    public final int TYPE_TASK = 0;
    public final int TYPE_COMMENT = 1;

    public DetailAdapter(DetailActivity context, Task task, List<GetCommentTaskResponse.Data> commentList) {
        this.task = task;
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_TASK) {
            return new DetailAdapter.MyViewHolder(inflater.inflate(R.layout.item_detail, parent, false));
        } else {
            return new DetailAdapter.LoadHolder(inflater.inflate(R.layout.item_comment, parent, false));
        }

    }

    @Override

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_COMMENT) {
            ((DetailAdapter.LoadHolder) holder).bindData(commentList.get(position - 1));
        }

        if (getItemViewType(position) == TYPE_TASK) {
            ((DetailAdapter.MyViewHolder) holder).bindData(task);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TASK;
        } else {
            return TYPE_COMMENT;
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtTaskName, txtMasterName, txtDate, txtNumCmt, txtDescription;
        CirleImageView img_avatar;
        ImageView img_edit_detail;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_avatar = (CirleImageView) itemView.findViewById(R.id.img_avatar);
            txtTitle = (TextView) itemView.findViewById(R.id.txtWorkName);
            txtTaskName = (TextView) itemView.findViewById(R.id.txtTaskName);
            txtMasterName = (TextView) itemView.findViewById(R.id.txtMastername);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            img_edit_detail = (ImageView) itemView.findViewById(R.id.img_edit_detail);
        }

        public void bindData(final Task workTask) {
            final String type = workTask.getType();
            setViewItem(type, workTask, this);

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
            txtDescription.setText(Html.fromHtml(workTask.getDetail()));

            img_edit_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    switch (type) {
                        case "I":
                            intent = new Intent(context, CreateIssueActivity.class);
                            intent.putExtra("task", workTask);
                            context.startActivity(intent);

                            break;
                        case "T":
                            intent = new Intent(context, CreateTaskActivity.class);
                            intent.putExtra("task", workTask);
                            context.startActivity(intent);
                            break;
                        case "N":
                            intent = new Intent(context, CreateNewsActivity.class);
                            intent.putExtra("task", workTask);
                            context.startActivity(intent);

                            break;
                        case "O":
                            intent = new Intent(context, CreateOpportunityActivity.class);
                            intent.putExtra("task", workTask);
                            context.startActivity(intent);

                            break;
                    }
                }
            });

        }
    }

    class LoadHolder extends RecyclerView.ViewHolder implements Observer {
        TextView txt_username, txt_comment, txt_date, txt_edit, txt_del;
        CirleImageView img_avatar;
        Button btn_download;

        public LoadHolder(View itemView) {
            super(itemView);

            img_avatar = (CirleImageView) itemView.findViewById(R.id.img_avatar);
            txt_username = (TextView) itemView.findViewById(R.id.txt_username);
            txt_comment = (TextView) itemView.findViewById(R.id.txt_comment);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_edit = (TextView) itemView.findViewById(R.id.txt_edit);
            txt_del = (TextView) itemView.findViewById(R.id.txt_del);
            btn_download = (Button) itemView.findViewById(R.id.btn_download);
        }

        public void bindData(final GetCommentTaskResponse.Data comment) {
            btn_download.setVisibility(View.GONE);
            txt_edit.setVisibility(View.GONE);
            txt_del.setVisibility(View.GONE);

            String userId = Model.getInstance().getLoginResponse().result.data.operatorid;
            if (comment.operatorid.equals(userId)) {
                txt_edit.setVisibility(View.VISIBLE);
                txt_del.setVisibility(View.VISIBLE);
            }

            String url_avatar = Config.BASEURL + String.format(Config.API_GET_IMAGE_AVATAR, String.valueOf(comment.employee));

            String firstLetter = "A";
            if (!comment.title.equalsIgnoreCase("") && comment.title != null) {
                firstLetter = String.valueOf(comment.title.charAt(0));
            }

            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(comment.title);

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

            txt_comment.setText(comment.comments);
            txt_username.setText(comment.title);
            if (comment.attachCount > 0) {
                btn_download.setVisibility(View.VISIBLE);
            }

            btn_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.getBusyIndicator(R.string.please_wait).show();
                    Model.getInstance().getDetailAttachment(task.getRecordID(), String.valueOf(comment.linenum));
                }
            });

            txt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.showEditView(comment);
                }
            });

            txt_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model.getInstance().addObserver(LoadHolder.this);
                    context.getBusyIndicator(R.string.please_wait).show();
                    Model.getInstance().callDeleteComment(String.valueOf(comment.linenum), task.getRecordID());
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
            if (evt == ModelEvent.DELETE_MESSAGE_SUCCESS) {
                context.getBusyIndicator(R.string.please_wait).dismiss();
                removeAt(getAdapterPosition() - 1);
                Model.getInstance().deleteObserver(this);
            }
        }
    }

    private void setViewItem(String type, Task currentTask, DetailAdapter.MyViewHolder holder) {

        holder.img_edit_detail.setVisibility(View.GONE);

        String userId = Model.getInstance().getLoginResponse().result.data.operatorid;
        if (currentTask.getTaskMaster().equals(userId)) {
            if (type.equalsIgnoreCase("T") || type.equalsIgnoreCase("I")) {
                holder.img_edit_detail.setVisibility(View.VISIBLE);
            }
        }

        holder.txtDate.setText(calculateDateFromPresent(currentTask.getCreateDate()));

        switch (type) {
            case "I":
                holder.txtTaskName.setText("Issue");
                break;
            case "T":
                holder.txtTaskName.setText("Task");

                break;
            case "N":
                holder.txtTaskName.setText("News");
                break;
            case "O":
                holder.txtTaskName.setText("Opportunity");
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

    public void removeAt(int position) {
        commentList.remove(position);
        notifyItemRemoved(position + 1);
        notifyItemRangeChanged(position + 1, commentList.size() + 1);
    }

    public void addItem(int position) {
        commentList.remove(position);
    }
}