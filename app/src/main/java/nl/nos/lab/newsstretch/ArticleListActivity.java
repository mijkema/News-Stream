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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;


public class ArticleListActivity extends FloatingActionMenuActivity implements
    AdapterView.OnItemClickListener {

    FloatingActionsMenu menu;
    FloatingActionButton button;
    ListView contentList;

    private String[] itemTitles = new String[] {
            "Van Geel: mooie dag voor Feyenoord en Legioen",
            "'Strengere aanpak gehoorschade nodig'",
            "Bereikbaarheid huisarts nog onvoldoende, maar wel beter",
            "Vrachtwagenbouwer DAF verhoogt productie",
            "Spectaculaire groei van aantal ijsvogels",
            "Sociale vorming op scholen niet doelgericht",
            "'Omstreden toneelstuk toch van Shakespeare'",
            "Amerikaanse hulpverlener genezen van ebola"
    };

    private int[] imageResources = new int[] {
            R.drawable.s6,
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,
            R.drawable.s5,
            R.drawable.s7,
            R.drawable.s8,
    };

    private String[] urlList = new String[] {
            "http://nos.nl/hybrid/artikel/2029467-van-geel-mooie-dag-voor-feyenoord-en-legioen.html",
            "http://nos.nl/hybrid/artikel/2029480-strengere-aanpak-gehoorschade-nodig.html",
            "http://nos.nl/hybrid/artikel/2029479-bereikbaarheid-huisarts-nog-onvoldoende-maar-wel-beter.html",
            "http://nos.nl/hybrid/artikel/2029478-vrachtwagenbouwer-daf-verhoogt-productie.html",
            "http://nos.nl/hybrid/artikel/2029475-spectaculaire-groei-van-aantal-ijsvogels.html",
            "http://nos.nl/hybrid/artikel/2029460-sociale-vorming-op-scholen-niet-doelgericht.html",
            "http://nos.nl/hybrid/artikel/2029446-omstreden-toneelstuk-toch-van-shakespeare.html",
            "http://nos.nl/hybrid/artikel/2029445-amerikaanse-hulpverlener-genezen-van-ebola.html"
    };

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
                View result = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
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
    protected boolean menuItemIs(int i) {
        return i == 3;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_article_list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, WebviewActivity.class).putExtra("url", urlList[position]));
    }
}
