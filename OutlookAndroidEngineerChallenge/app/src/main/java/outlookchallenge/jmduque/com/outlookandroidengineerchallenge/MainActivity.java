package outlookchallenge.jmduque.com.outlookandroidengineerchallenge;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import app.dinus.com.itemdecoration.PinnedHeaderDecoration;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaEvent;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaHeader;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaItem;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaNoEvent;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters.AgendaAdapter;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.DateTimeUtils;

/**
 * @author Jose Duque
 *         This is the main activity of the app.
 */
public class MainActivity
        extends
        AppCompatActivity
        implements
        View.OnClickListener {

    private static final String[] randomEventNames = {
            "China Joy",
            "Comicon",
            "Detroit Car Auto",
            "Computex",
            "GSMA - Barcelona",
            "GSMA - Shanghai"
    };

    private static final String[] randomEventLocations = {
            "",
            "Shanghai",
            "Detroit",
            "Tokyo",
            "Barcelona"
    };

    //AGENDA VIEWS & DATA
    private RecyclerView agenda;
    private AgendaAdapter agendaAdapter;
    private List<AgendaItem> agendaItems = new ArrayList<>();
    private int todayHeaderIndex = 0;

    //CALENDAR VIEWS & DATA
    //TODO

    //ACTIVITY VIEWS & DATA
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDemoData();
        bindViews();
        setListeners();
    }

    /**
     * Creates data for demo proposes spanning between one year ago and one year later
     */
    private void initDemoData() {
        Date today = new Date();
        //STARTING ONE YEAR AGO
        Date date = new Date(
                today.getTime() - DateUtils.YEAR_IN_MILLIS
        );

        Random random = new Random();

        //RUN FOR 2 YEARS
        for (int i = 0; i < 2 * 365; i++) {
            AgendaHeader agendaHeader = new AgendaHeader();
            agendaHeader.setDate(date);
            agendaItems.add(
                    agendaHeader
            );

            if (DateTimeUtils.isSameDay(
                    date,
                    today
            )) {
                todayHeaderIndex = agendaItems.size() - 1;
            }

            //TRY TO CREATE EVENTS FOR ONE EVERY 20 DAYS IN AVERAGE
            if (random.nextInt(20) == 0) {
                agendaItems.add(
                        createRandomEvent(
                                random,
                                date
                        )
                );
            } else {
                agendaItems.add(
                        new AgendaNoEvent()
                );
            }

            date = new Date(
                    date.getTime() + DateUtils.DAY_IN_MILLIS
            );
        }
    }

    private AgendaEvent createRandomEvent(
            Random random,
            Date date
    ) {
        //DATE ACCORDINGLY
        AgendaEvent agendaEvent = new AgendaEvent();
        agendaEvent.setDate(date);

        //SET RANDOM NAME
        int randomNameIndex = random.nextInt(randomEventNames.length);
        agendaEvent.setName(
                randomEventNames[randomNameIndex]
        );

        //SET RANDOM LOCATION
        int randomLocationIndex = random.nextInt(randomEventLocations.length);
        agendaEvent.setLocation(
                randomEventLocations[randomLocationIndex]
        );

        //SET RANDOM DURATION
        int duration = random.nextInt(6);
        Date endTime;
        if (duration == 0) {
            endTime = null;
        } else {
            endTime = new Date(
                    date.getTime() + duration * DateUtils.HOUR_IN_MILLIS
            );
        }
        agendaEvent.setEndTime(
                endTime
        );

        return agendaEvent;
    }

    /**
     * Finds the views within the activity and binds them to local properties
     */
    private void bindViews() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        agenda = (RecyclerView) findViewById(R.id.agenda);
        agenda.setLayoutManager(
                new LinearLayoutManager(this)
        );

        //ADD PINNED BEHAVIOUR TO DAILY HEADERS
        PinnedHeaderDecoration pinnedHeaderDecoration = new PinnedHeaderDecoration();
        pinnedHeaderDecoration.registerTypePinnedHeader(
                AgendaAdapter.HEADER,
                new PinnedHeaderDecoration.PinnedHeaderCreator() {
                    @Override
                    public boolean create(
                            RecyclerView parent,
                            int adapterPosition
                    ) {
                        return true;
                    }
                }
        );
        agenda.addItemDecoration(
                pinnedHeaderDecoration
        );

        agendaAdapter = new AgendaAdapter(
                this,
                agendaItems
        );

        agenda.setAdapter(
                agendaAdapter
        );

        agenda.scrollToPosition(todayHeaderIndex);

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    /**
     * Setups the listeners for the views within the activity
     */
    private void setListeners() {
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleFabClick(View view) {
        Snackbar.make(
                view,
                "Replace with your own action",
                Snackbar.LENGTH_LONG
        ).setAction(
                "Action",
                null
        ).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab: {
                handleFabClick(view);
                break;
            }
        }
    }
}
