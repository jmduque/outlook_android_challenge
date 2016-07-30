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
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters.CalendarAdapter;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.viewHolders.CalendarDayViewHolder;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.CollectionUtils;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.DateTimeUtils;

/**
 * @author Jose Duque
 *         This is the main activity of the app.
 */
public class MainActivity
        extends
        AppCompatActivity
        implements
        View.OnClickListener,
        CalendarDayViewHolder.DaySelector {

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
    private LinearLayoutManager agendaLinearLayoutManager;
    private AgendaAdapter agendaAdapter;
    private List<AgendaItem> agendaItems = new ArrayList<>();

    //CALENDAR VIEWS & DATA
    private RecyclerView calendar;
    private CalendarAdapter calendarAdapter;
    private Date highlightedDay = new Date();

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

        initCalendarViews();
        initAgendaViews();

        scrollToDate(
                highlightedDay
        );

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    /**
     * This method generates and initializes the views for the calendar
     */
    private void initCalendarViews() {
        calendar = (RecyclerView) findViewById(R.id.calendar);
        calendar.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

//        calendar.addItemDecoration(
//                new VerticalDividerItemDecoration.Builder(this)
//                        .color(android.R.color.white)
//                        .size(R.dimen.oaec_large_spacing)
//                        .build()
//        );

        calendarAdapter = new CalendarAdapter(
                this,
                this,
                2016,
                7,
                24
        );

        calendar.setAdapter(
                calendarAdapter
        );
    }

    /**
     * This method generates and initializes the views for the agenda
     */
    private void initAgendaViews() {
        agenda = (RecyclerView) findViewById(R.id.agenda);
        agendaLinearLayoutManager = new LinearLayoutManager(this);
        agenda.setLayoutManager(
                agendaLinearLayoutManager
        );

        //ADD PINNED BEHAVIOUR TO DAILY HEADERS
        PinnedHeaderDecoration pinnedHeaderDecoration = new PinnedHeaderDecoration();
        pinnedHeaderDecoration.registerTypePinnedHeader(
                AgendaAdapter.HEADER,
                new PinnedHeaderDecoration.PinnedHeaderCreator() {

                    private int lastAdapterPosition = 0;

                    @Override
                    public boolean create(
                            RecyclerView parent,
                            int adapterPosition
                    ) {
                        if (lastAdapterPosition == adapterPosition) {
                            return true;
                        }

                        lastAdapterPosition = adapterPosition;
                        highlightedDay = agendaAdapter
                                .getItem(adapterPosition)
                                .getDate();
                        calendarAdapter.notifyDataSetChanged();

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
    }

    /**
     * Setups the listeners for the views within the activity
     */
    private void setListeners() {
        agenda.setOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(
                            RecyclerView recyclerView,
                            int dx,
                            int dy
                    ) {
                        super.onScrolled(recyclerView, dx, dy);
                        agendaLinearLayoutManager.get
                    }
                }
        );
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

    @Override
    public boolean isHighlightedDate(Date date) {
        //Mark date as selected if is the same as currently selected one
        return DateTimeUtils.isSameDay(
                highlightedDay,
                date
        );
    }

    @Override
    public void onDayPressed(Date date) {
        highlightedDay = date;
        scrollToDate(date);
    }

    public void scrollToDate(Date date) {
        for (int i = 0; i < CollectionUtils.size(agendaItems); i++) {
            AgendaItem agendaItem = agendaItems.get(i);
            if (!(agendaItem instanceof AgendaHeader)) {
                continue;
            }

            Date itemDate = agendaItem.getDate();
            if (DateTimeUtils.isSameDay(
                    date,
                    itemDate
            )) {
                agenda.scrollToPosition(i);
                return;
            }

            agendaAdapter.notifyDataSetChanged();
            calendarAdapter.notifyDataSetChanged();
        }
    }
}
