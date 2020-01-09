package cn.shopping.itcast.shopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.shopping.itcast.shopping.database.SQLiteHelper;

public class ShoppingActivity extends Activity{
    private ListView mListView;
    SQLiteHelper mSQLiteHelper;
    String id;
    TextView Shopping_name,content;
    //商品名称与价格数据集合
    private String[] titles={
            "桌子","苹果","蛋糕","毛衣","猕猴桃","围巾","小猫","小黄狗","小黄鸭","麻婆豆腐","水煮肉片"
    };
    private  String[] prices={
            "3300元","15元/kg","400元","450"+"元","8元/kg","280元","23元/只","600元/只","5元/只","25元/份","65元/份"
    };
    //图片数据集合
    private int[] icons={R.drawable.table,R.drawable.apple,R.drawable.
            cake,R.drawable.wireclothes,R.drawable.kiwifruit,R.drawable.scarf,R.drawable.cat,R.drawable.siberiankusky,R.drawable.yellowduck,R.drawable.mapoytofu,R.drawable.boiledmeat};
    private android.view.LayoutInflater LayoutInflater;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        ImageView car=(ImageView) findViewById(R.id.car);
        mListView=(ListView) findViewById(R.id.lv);//初始化ListView控件
        MybaseAdapter mAdapter =new MybaseAdapter();//
        mSQLiteHelper = new SQLiteHelper(this);
        mListView.setAdapter(mAdapter);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShoppingActivity.this,ShoppinglistActivity.class);
                startActivityForResult(intent,1);
            }
        });
        initData();
    }
    protected void initData(){
//        mSQLiteHelper=new SQLiteHelper(this);
//        Shopping_name.setText("购物车");
}
    class MybaseAdapter extends BaseAdapter {
            @Override
            public int getCount() { //获取item的总数
                return titles.length;//返回ListView Item条目的总数
            }
            @Override
            public Object getItem(int position) {
                return titles[position];//返回Item的数据对象
            }
            @Override
            public long getItemId(int position) {
                return position; //返回Item的id
            }
            //得到Item的View视图
            class ViewHolder {
                TextView title, price;
                ImageView iv;
                Button addshop;

            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = View.inflate(ShoppingActivity.this, R.layout.list_item, null);
                    holder = new ViewHolder();
                    holder.title =  convertView.findViewById(R.id.title);
                    holder.price = convertView.findViewById(R.id.price);
                    holder.iv =  convertView.findViewById(R.id.iv);
                    holder.addshop = convertView.findViewById(R.id.addshop);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.addshop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean n = mSQLiteHelper.insertData(titles[position],prices[position],icons[position]);
                        if(n){
                            Toast.makeText(ShoppingActivity.this,"加入购物车成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ShoppingActivity.this,"加入购物车失败",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                holder.title.setText(titles[position]);
                holder.price.setText(prices[position]);
                holder.iv.setBackgroundResource(icons[position]);
                return convertView;
            }
        }
    }
