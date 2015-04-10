package nl.nos.lab.newsstretch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class AudioListActivity extends FloatingActionMenuActivity implements AdapterView.OnItemClickListener {

    private String[] itemTitles = new String[] {
            "Kuijt keert terug naar Feyenoord",
            "Kwart jongeren heeft gehoorschade",
            "Na 50 jaar topoverleg Cuba en VS",
            "Deel-cao voor grondpersoneel KLM",
            "\"Clinton kondigt kandidatuur aan\"",
            "Ban Ki-moon: Yarmouk een dodenkamp",
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
                View result = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item, parent, false);
                TextView title = (TextView) result.findViewById(R.id.title);
                title.setText(itemTitles[position]);
                return result;
            }
        });

        contentList.setOnItemClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_audio_list;
    }

    @Override
    protected boolean menuItemIs(int i) {
        return i == 1;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
    }
}
