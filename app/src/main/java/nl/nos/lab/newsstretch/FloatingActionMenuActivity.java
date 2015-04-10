package nl.nos.lab.newsstretch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

/**
 * @author Matthijs IJkema (10-04-15).
 */
public abstract class FloatingActionMenuActivity extends ActionBarActivity {

    FloatingActionsMenu menu;
    FloatingActionButton button;
    FloatingActionButton menuItem1, menuItem2, menuItem3, menuItem4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        button = (FloatingActionButton) findViewById(R.id.action_button);
        menu = (FloatingActionsMenu) findViewById(R.id.action_menu);
        menuItem1 = (FloatingActionButton) findViewById(R.id.button1);
        menuItem2 = (FloatingActionButton) findViewById(R.id.button2);
        menuItem3 = (FloatingActionButton) findViewById(R.id.button3);
        menuItem4 = (FloatingActionButton) findViewById(R.id.button4);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        menuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menuItemIs(1)) {
                    startActivity(new Intent(FloatingActionMenuActivity.this, AudioListActivity.class).putExtra("anim", false));
                    finish();
                }
            }
        });
        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menuItemIs(2)) {
                    startActivity(new Intent(FloatingActionMenuActivity.this, TeletekstActivity.class).putExtra("anim", false));
                    finish();
                }
            }
        });
        menuItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menuItemIs(3)) {
                    startActivity(new Intent(FloatingActionMenuActivity.this, ArticleListActivity.class));
                    finish();
                }
            }
        });
        menuItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menuItemIs(4)) {
                    startActivity(new Intent(FloatingActionMenuActivity.this, StoryTellingListActivity.class));
                    finish();
                }
            }
        });
    }

    protected abstract int getContentView();

    protected abstract boolean menuItemIs(int i);
}
