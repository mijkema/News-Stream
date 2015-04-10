package nl.nos.lab.newsstretch;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TeletekstActivity extends FloatingActionMenuActivity {


    ListView contentList;
    private String[] items = new String[] {
            "Kuijt keert terug naar Feyenoord",
            "Kwart jongeren heeft gehoorschade",
            "Na 50 jaar topoverleg Cuba en VS",
            "Deel-cao voor grondpersoneel KLM",
            "\"Clinton kondigt kandidatuur aan\"",
            "Ban Ki-moon:Yarmouk een dodenkamp",
            "Marine Le Pen werkt vader uit FN",
            "3FM Awards voor Kensington en Dotan",
            "NS zet meer mensen in tegen geweld."
    };
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentList = (ListView) findViewById(R.id.content_list);
        mAdapter = new ArrayAdapter<>(this, R.layout.teletekst_item, R.id.title, items);
        contentList.setAdapter(mAdapter);
        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView titleView = (TextView) view.findViewById(R.id.title);
                TextView detailTextView = (TextView) view.findViewById(R.id.details);
                boolean setDetails = false;
                if (titleView.getText().equals("Kwart jongeren heeft gehoorschade")) {
                    setDetails = true;
                    detailTextView.setText(
                            " 25% van de jongeren tussen de 12 en  \n" +
                                    " 25 jaar in Nederland heeft vermoedelijk\n" +
                                    " gehoorschade.Onderzoekers van het AMC  \n" +
                                    " in Amsterdam concluderen dat op basis  \n" +
                                    " van online zelftesten.Tussen 2010 en   \n" +
                                    " 2014 deden 300.000 mensen zo'n test.   \n" +
                                    "                                        \n" +
                                    " De test geeft een indicatie over de    \n" +
                                    " gehoorschade,maar zekerheid daarover   \n" +
                                    " moet een arts geven.Het aantal jongeren\n" +
                                    " met gehoorschade is de laatste jaren   \n" +
                                    " stabiel gebleven.                      \n" +
                                    "                                        \n" +
                                    " Gehoorschade ontstaat door langdurig en\n" +
                                    " vaak naar harde muziek te luisteren via\n" +
                                    " de koptelefoon of bij het uitgaan.Een  \n" +
                                    " richtlijn van de WHO is om dat niet    \n" +
                                    " langer dan vier minuten te doen. "
                    );

                } else if (titleView.getText().equals("Na 50 jaar topoverleg Cuba en VS")) {
                    setDetails = true;
                    detailTextView.setText(
                            " Voor het eerst in ruim een halve eeuw\n" +
                                    " is er topoverleg tussen Cuba en de VS  \n" +
                                    " geweest.Voor het begin van de top van  \n" +
                                    " de Organisatie van Amerikaanse Staten  \n" +
                                    " in Panama spraken de ministers van     \n" +
                                    " Buitenlandse Zaken Kerry en Rodriguez  \n" +
                                    " langdurig met elkaar.Sinds de Cubaanse \n" +
                                    " revolutie was er niet op zo'n hoog     \n" +
                                    " niveau contact.                        \n" +
                                    "                                        \n" +
                                    " President Obama en zijn ambtgenoot Raúl\n" +
                                    " Castro zijn ook in Panama.Mogelijk     \n" +
                                    " spreken zij elkaar ook in de marge van \n" +
                                    " de top.                                \n" +
                                    "                                        \n" +
                                    " De OAS-top is elke drie jaar.Dit is de \n" +
                                    " eerste keer dat de VS geen bezwaar     \n" +
                                    " maakt tegen de Cubaanse aanwezigheid."
                    );
                } else if (titleView.getText().equals("Kuijt keert terug naar Feyenoord")) {
                    setDetails = true;
                    detailTextView.setText(
                            " Dirk Kuijt keert terug op het oude   \n" +
                                    " nest.De 34-jarige aanvaller gaat weer  \n" +
                                    " voor Feyenoord spelen,waar hij tussen  \n" +
                                    " 2003 en 2006 ook voor uitkwam.         \n" +
                                    "                                        \n" +
                                    " Kuijt,die tot de zomer voor het Turkse \n" +
                                    " Fenerbahçe uitkomt,tekent voor één jaar\n" +
                                    " in Rotterdam.\"Ik heb altijd gezegd dat \n" +
                                    " ik terug wilde keren.Ik krijg die kans \n" +
                                    " nu ik vind dat ik nog wat voor de club \n" +
                                    " kan betekenen.Dat maakt me erg trots.\" \n" +
                                    "                                        \n" +
                                    " Kuijt trof in zijn eerste periode voor \n" +
                                    " Feyenoord 71 keer doel in 101 duels.Hij\n" +
                                    " verkaste daarna naar Liverpool,waar hij\n" +
                                    " tot 2012 voor speelde,en vervolgens    \n" +
                                    " verhuisde de 104-voudig international  \n" +
                                    " naar Fenerbahçe.Kuijt komt transfervrij\n" +
                                    " over naar Feyenoord.                   "
                    );
                }

                if (setDetails) {
                    int visibility = detailTextView.getVisibility();
                    detailTextView.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            }
        });

        if (getIntent().getExtras() == null || getIntent().getExtras().getBoolean("anim", true)) {

            button.post(new Runnable() {
                @Override
                public void run() {
                    float middle = button.getMeasuredHeight() / 2;
                    ScaleAnimation scale = new ScaleAnimation(3, 1, 3, 1, middle, middle);
                    float newX = menu.getX() - button.getX();
                    float newY = menu.getBottom() - button.getBottom();
                    TranslateAnimation translateAnimation = new TranslateAnimation(0f, newX, 0f, newY);
                    AnimationSet set = new AnimationSet(true);
                    set.setStartOffset(500);
                    set.setDuration(750);
                    set.setInterpolator(new LinearInterpolator());
                    set.addAnimation(translateAnimation);
                    set.addAnimation(scale);
                    set.setFillAfter(true);
                    set.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            menu.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    button.setAnimation(set);
                }
            });
        } else {
            button.post(new Runnable() {
                @Override
                public void run() {
                    menu.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
                }
            });
        }
    }

    @Override
    protected boolean menuItemIs(int i) {
        return i == 2;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_teletekst;
    }
}
