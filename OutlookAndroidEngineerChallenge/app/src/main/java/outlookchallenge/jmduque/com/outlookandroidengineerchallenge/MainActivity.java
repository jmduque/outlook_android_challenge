package outlookchallenge.jmduque.com.outlookandroidengineerchallenge;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import app.dinus.com.itemdecoration.PinnedHeaderDecoration;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.controller.CalendarMonthController;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaEvent;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaHeader;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaItem;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaNoEvent;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarDay;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarMonth;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters.AgendaAdapter;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters.CalendarAdapter;
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
        CalendarDay.DaySelector {

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

    private final Calendar gregorianCalendar = new GregorianCalendar();

    //AGENDA VIEWS & DATA
    private RecyclerView agenda;
    private LinearLayoutManager agendaLinearLayoutManager;
    private AgendaAdapter agendaAdapter;
    private List<AgendaItem> agendaItems = new ArrayList<>();
    private HashMap<String, AgendaItem> agendaHeaders = new HashMap<>();
    private AgendaHeader firstVisibleAgendaHeader;

    //CALENDAR VIEWS & DATA
    private RecyclerViewPager calendar;
    private CalendarAdapter calendarAdapter;
    private CalendarDay previousHighlightedDay;
    private List<CalendarMonth> calendarItems = new ArrayList<>();
    private HashMap<String, CalendarMonth> calendarItemsMap = new HashMap<>();

    //ACTIVITY VIEWS & DATA
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAgendaDemoData();
        initCalendarDemoData();
        bindViews();
        setListeners();
    }

    /**
     * Creates data for demo proposes spanning between one year ago and one year later
     */
    private void initAgendaDemoData() {
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

            agendaHeaders.put(
                    DateTimeUtils.dayOfTheYear.format(date),
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

    /**
     * Creates demo data for the calendar view spanning one year ago to one year later
     */
    private void initCalendarDemoData() {
        //Use the first AgendaItem (assumed as header) as starting date
        Date date = agendaItems
                .get(0)
                .getDate();

        //Use the last AgendaHeader to define last month that the use can visualize
        Date lastDate = null;
        for (int i = 1; lastDate == null; i++) {
            lastDate = agendaItems
                    .get(agendaItems.size() - i)
                    .getDate();
        }

        //Define first visible month by year and month
        int startYear = DateTimeUtils.getYear(date);
        int startMonth = DateTimeUtils.getMonth(date);

        //Define last visible month by year and month
        int endYear = DateTimeUtils.getYear(lastDate);
        int endMonth = DateTimeUtils.getMonth(lastDate);

        int year = startYear;
        int month = startMonth;

        CalendarMonthController calendarMonthController = new CalendarMonthController();

        //From the starting year/month to the end year/month
        for (int i = 0;
             !(year == endYear && month == endMonth);
             i++) {

            month = startMonth + i;
            year = startYear;
            year += month / 12;
            month = month % 12;
            CalendarMonth calendarMonth = calendarMonthController.generateCalendarMonth(
                    this,
                    year,
                    month
            );

            //Add to a map for access proposes
            String key = calendarKeyFormatter(
                    year,
                    month
            );
            calendarItemsMap.put(
                    key,
                    calendarMonth
            );

            //Add to the array for visualization proposes
            calendarItems.add(calendarMonth);
        }
    }

    /**
     * @return String to be used as accessibility key for the calendarMap
     */
    private String calendarKeyFormatter(
            int year,
            int month
    ) {
        return getString(
                R.string.oaec_calendar_key_format,
                year,
                month
        );
    }

    /**
     * @param random seed to use to create the events
     * @param date   date to be associated to the created event
     */
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

        scrollAgendaToDate(
                new Date()
        );

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    /**
     * This method generates and initializes the views for the calendar
     */
    private void initCalendarViews() {
        calendar = (RecyclerViewPager) findViewById(R.id.calendar);
        calendar.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        calendarAdapter = new CalendarAdapter(
                this,
                this,
                calendarItems
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

        //Add basic animation for adding/removing items
        agenda.setItemAnimator(
                new DefaultItemAnimator()
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
     * To be invoked once a NewHeader has been detected. We consider a new header as an
     * AgendaHeader on top of the list. Other AgendaHeaders will be ignored.
     */
    private void onNewHeaderDate(Date date) {
        setHighlightedDay(
                date
        );

        scrollCalendarToDate(
                date
        );

        calendarAdapter.notifyDataSetChanged();
    }

    /**
     * Setups the listeners for the views within the activity
     */
    private void setListeners() {
        agenda.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                checkFirstVisibleHeader();
            }
        });
        calendar.addOnPageChangedListener(
                new RecyclerViewPager.OnPageChangedListener() {
                    @Override
                    public void OnPageChanged(
                            int previousPosition,
                            int newPosition
                    ) {
                        if (!CollectionUtils.isValidPosition(
                                calendarItems,
                                newPosition
                        )) {
                            return;
                        }

                        CalendarMonth item = calendarItems.get(newPosition);
                        if (item == null) {
                            return;
                        }

                        setTitle(item.getMonthName());
                    }
                }
        );
        fab.setOnClickListener(this);
    }

    /**
     * Checks which is the first view visible within the agenda.
     * If first visible item is an AgendaHeader, we notify a new header has been created
     */
    private void checkFirstVisibleHeader() {
        int firstCompletelyVisibleItemPosition =
                agendaLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
        AgendaItem agendaItem = agendaItems.get(firstCompletelyVisibleItemPosition);
        //We only take action if first visible item is an AgendaHeader
        if (agendaItem instanceof AgendaHeader) {
            //If only take action if last-detected header is different from newly detected one
            if (firstVisibleAgendaHeader == agendaItem) {
                return;
            }

            firstVisibleAgendaHeader = (AgendaHeader) agendaItem;
            onNewHeaderDate(agendaItem.getDate());
        }
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
        if (firstVisibleAgendaHeader == null) {
            return;
        }

        Snackbar.make(
                view,
                "Created Random Agenda Event on Current Date",
                Snackbar.LENGTH_LONG
        ).setAction(
                android.R.string.ok,
                null
        ).show();

        //Get position of the header
        int headerPosition = agendaItems.indexOf(
                firstVisibleAgendaHeader
        );
        //Create new calendar event
        AgendaEvent agendaEvent = createRandomEvent(
                new Random(),
                firstVisibleAgendaHeader.getDate()
        );

        //If current view after header is a non-event view, remove it
        int expectedPosition = headerPosition + 1;
        AgendaItem nextAgendaItem = agendaItems.get(expectedPosition);
        if (nextAgendaItem instanceof AgendaNoEvent) {
            agendaItems.remove(expectedPosition);
            agendaAdapter.notifyItemRemoved(
                    expectedPosition
            );
        }

        //Add event to the list below the header
        agendaItems.add(
                expectedPosition,
                agendaEvent
        );

        //Notify Adapter item has been added
        agendaAdapter.notifyItemInserted(
                expectedPosition
        );

        //Make sure day's header is first view
        agendaLinearLayoutManager.scrollToPosition(
                headerPosition
        );
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
    public void onDayPressed(CalendarDay pressedDay) {
        scrollAgendaToDate(
                pressedDay.getDate()
        );
        checkFirstVisibleHeader();
    }

    /**
     * Scrolls the agenda to make an AgendaHeader that matches the provided date be the first view
     * of the agenda.
     *
     * @param date expected date of the first visible item in the agenda
     */
    public void scrollAgendaToDate(Date date) {
        String dayOfTheYear = DateTimeUtils.dayOfTheYear.format(date);
        AgendaItem agendaItem = agendaHeaders.get(dayOfTheYear);
        agendaLinearLayoutManager.scrollToPositionWithOffset(
                agendaItems.indexOf(
                        agendaItem
                ),
                0
        );
    }

    /**
     * Scrolls the calendar view to match with the provided date
     *
     * @param date to be contained within the month we want to scroll
     */
    public void scrollCalendarToDate(Date date) {
        gregorianCalendar.setTime(date);

        int year = gregorianCalendar.get(Calendar.YEAR);
        int month = gregorianCalendar.get(Calendar.MONTH);

        String key = calendarKeyFormatter(
                year,
                month
        );

        CalendarMonth calendarMonth = calendarItemsMap.get(
                key
        );

        if (calendarMonth == null) {
            return;
        }

        calendar.scrollToPosition(
                calendarItems.indexOf(
                        calendarMonth
                )
        );
    }

    /**
     * Set highlighted day by a provided date. It will use the date to find the appropriated
     * CalendarMonth and the CalendarDay within the month.
     */
    public void setHighlightedDay(Date date) {
        gregorianCalendar.setTime(date);

        int year = DateTimeUtils.getYear(date);
        int month = DateTimeUtils.getMonth(date);
        int day = DateTimeUtils.getDay(date);

        String key = calendarKeyFormatter(
                year,
                month
        );

        CalendarMonth calendarMonth = calendarItemsMap.get(
                key
        );

        if (calendarMonth == null) {
            return;
        }

        int position = day + calendarMonth.getFirstWeekDayOfTheMonth() - 1;
        setHighlightedDay(
                calendarMonth
                        .getDays()
                        .get(position)
        );
    }

    /**
     * Updates the highlighted calendar day. Last known-highlighted one
     * will be marked as non-highlighted
     */
    public void setHighlightedDay(CalendarDay calendarDay) {
        calendarDay.setHighlighted(true);

        if (previousHighlightedDay != null) {
            previousHighlightedDay.setHighlighted(false);
        }

        previousHighlightedDay = calendarDay;
    }

}
