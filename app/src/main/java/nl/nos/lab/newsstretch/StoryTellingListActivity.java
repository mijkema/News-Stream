package nl.nos.lab.newsstretch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class StoryTellingListActivity extends FloatingActionMenuActivity implements AdapterView.OnItemClickListener {

    private String[] itemTitles = new String[] {
            "Kuijt: alles komt nu bij elkaar",
            "'Amerikanen hebben weer interesse in zuiderburen'",
            "Nieuwe beelden opgedoken van fataal schietincident VS",
            "Ook gewone Braziliaan wil kogelwerende auto",
    };

    private int[] imageResources = new int[] {
            R.drawable.xl2,
            R.drawable.xl1,
            R.drawable.xl3,
            R.drawable.xl4,
    };

    private String[] urlList = new String[] {
            "http://nos.nl/hybrid/artikel/2029483-kuijt-alles-komt-nu-bij-elkaar.html",
            "http://nos.nl/hybrid/artikel/2029476-amerikanen-hebben-weer-interesse-in-zuiderburen.html",
            "http://nos.nl/hybrid/artikel/2029440-nieuwe-beelden-opgedoken-van-fataal-schietincident-vs.html",
            "http://nos.nl/hybrid/artikel/2029408-ook-gewone-braziliaan-wil-kogelwerende-auto.html",
    };


    private ListView contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentList = (ListView) findViewById(R.id.content_list);

        contentList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return itemTitles.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View result = LayoutInflater.from(parent.getContext()).inflate(R.layout.storytelling_item, parent, false);
                TextView title = (TextView) result.findViewById(R.id.title);
                ImageView image = (ImageView) result.findViewById(R.id.image);
                title.setText(itemTitles[position]);
                image.setImageResource(imageResources[position]);
                return result;
            }
        });

        contentList.setOnItemClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_story_telling_list;
    }

    @Override
    protected boolean menuItemIs(int i) {
        return i == 4;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, WebviewActivity.class).putExtra("url", urlList[position]));
    }
}
