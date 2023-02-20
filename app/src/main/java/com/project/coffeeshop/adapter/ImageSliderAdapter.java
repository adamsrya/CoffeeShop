package com.project.coffeeshop.adapter;










import android.annotation.SuppressLint;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.viewpager2.widget.ViewPager2;


import com.bumptech.glide.Glide;
import com.project.coffeeshop.R;
import com.project.coffeeshop.services.ClickListenerImage;
import com.project.coffeeshop.models.ImageData;

import java.util.List;


public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder> {

    private final List<ImageData> imageData;
    private final ViewPager2 viewPager2;
    private final ClickListenerImage clickListenerImage;

    public ImageSliderAdapter(List<ImageData> imageData, ViewPager2 viewPager2, ClickListenerImage clickListenerImage) {
        this.imageData = imageData;
        this.viewPager2 = viewPager2;
        this.clickListenerImage = clickListenerImage;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new SliderViewHolder(
               LayoutInflater.from(parent.getContext()).inflate(
                       R.layout.slideshow,
                       parent,
                       false
               )
       );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(imageData.get(position));
         if(position == imageData.size() ) {
             Glide.with(holder.itemView.getContext()).load(imageData).into(holder.imageView);
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return imageData.toArray().length;
    }
    class SliderViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageslide);
        }

        void setImage(ImageData sliderItem) {
            imageView.setImageResource(sliderItem.getImage());
            imageView.setOnClickListener(v -> clickListenerImage.onPictureClicked(getAdapterPosition()));
        }
    }

    private final Runnable runnable = new Runnable() {

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            imageData.addAll(imageData);
            notifyDataSetChanged();
        }

    /*private final Context context;
    LayoutInflater inflater;
    private int img [] = {
            R.drawable.barista_pic,
            R.drawable.bean_pic,
            R.drawable.extras_pic
    };


    public ImageSliderAdapter(Context context) {
        this.context = context ;


    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view== object);
    }




    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slideshow,container,false);
        ImageView image = view.findViewById(R.id.imageslide);
        Glide.with(context).load(img[position]).into(image);
        image.setOnClickListener(v -> {

        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }*/

    };
}
