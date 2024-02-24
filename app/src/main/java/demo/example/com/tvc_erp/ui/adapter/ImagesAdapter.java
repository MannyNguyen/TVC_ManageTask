package demo.example.com.tvc_erp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.utils.FileHelper;

/**
 * Created by prosoft on 10/20/16.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private Context mContext;

    public ArrayList<Uri> photos = new ArrayList<>();
    private IPhotoAction photoAction;
    public int selectedPosition = 0;

    // Constructor
    public ImagesAdapter(Context c, ArrayList<Uri> photos, IPhotoAction photoAction) {
        mContext = c;
        this.photos = photos;
        this.photoAction = photoAction;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_picker_image_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImagesAdapter.ViewHolder holder, final int position) {

        holder.itemView.setSelected(selectedPosition == position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                photoAction.didSelectAPicture(selectedPosition);

            }
        });

        if (holder.itemView.isSelected()) {
            holder.rlBorder.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);
        } else {
            holder.rlBorder.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }

        Uri uri = photos.get(position);
//        Bitmap bm = FileHelper.getBitMapFromUri(mContext, uri, 200, 200);
////                Bitmap bm = FileHelper.getThumnailVideoPath(mContext, uri);
//        if (bm != null) {
//            holder.image.setImageBitmap(bm);
//
//            holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }

        Glide.with(mContext)
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.black_border_box)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoAction.didDeletePicture(position);
            }
        });


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public Button btnDelete;
        public RelativeLayout rlBorder;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img_property);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
            rlBorder = (RelativeLayout) itemView.findViewById(R.id.rl_border);
        }
    }

    public interface IPhotoAction {
        void didDeletePicture(int index);

        void didSelectAPicture(int index);
    }
}
