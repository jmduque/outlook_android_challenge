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

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.text.ParseException;
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
        Date date = agendaItems
                .get(0)
                .getDate();

        Date lastDate = null;
        for (int i = 1; lastDate == null; i++) {
            lastDate = agendaItems
                    .get(
                            agendaItems.size() - i
                    )
                    .getDate();
        }

        int startYear = getYear(date);
        int startMonth = getMonth(date);

        int endYear = getYear(lastDate);
        int endMonth = getMonth(lastDate);

        int year = startYear;
        int month = startMonth;

        CalendarMonthController calendarMonthController = new CalendarMonthController();

        for (int i = 0;
             !(year == endYear && month == endMonth);
             i++) {

            month = startMonth + i;
            year = startYear;
            year += month / 12;
            month = month % 12;

            int nextMonth = startMonth + i + 1;
            int nextYear = startYear;
            nextYear += nextMonth / 12;
            nextMonth = nextMonth % 12;

            CalendarMonth calendarMonth = new CalendarMonth();
            calendarMonth.setYear(year);
            calendarMonth.setMonth(month);
            try {
                Date firstDayOfTheMonth = DateTimeUtils.dayOfTheYear.parse(
                        getString(
                                R.string.oaec_calendar_month_first_day,
                                year,
                                month + 1
                        )
                );

                calendarMonth.setFirstDayOfTheMonth(
                        firstDayOfTheMonth
                );
                calendarMonth.setMonthName(
                        DateTimeUtils.monthName.format(firstDayOfTheMonth)
                );
                calendarMonth.setFirstWeekDayOfTheMonth(
                        getFirstDayOfWeek(firstDayOfTheMonth)
                );

                long firstDayOfNextMonthTime = DateTimeUtils.dayOfTheYear.parse(
                        getString(
                                R.string.oaec_calendar_month_first_day,
                                nextYear,
                                nextMonth + 1
                        )
                ).getTime();
                //Last day of this month is 1ms earlier than the begining of next month
                calendarMonth.setLastDayOfTheMonth(
                        new Date(firstDayOfNextMonthTime - 1)
                );

                calendarMonthController.generateCalendarDays(
                        calendarMonth
                );
            } catch (ParseException ignored) {
            }

            String key = calendarKeyFormatter(
                    year,
                    month
            );
            calendarItemsMap.put(
                    key,
                    calendarMonth
            );
            calendarItems.add(calendarMonth);
        }
    }

    public int getYear(Date date) {
        gregorianCalendar.setTime(date);
        return gregorianCalendar.get(Calendar.YEAR);
    }

    /**
     * @return 0-based month of the year
     */
    public int getMonth(Date date) {
        gregorianCalendar.setTime(date);
        return gregorianCalendar.get(Calendar.MONTH);
    }

    /**
     * @return returns the value of the day of the month
     */
    public int getDay(Date date) {
        gregorianCalendar.setTime(date);
        return gregorianCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getFirstDayOfWeek(Date date) {
        gregorianCalendar.setTime(date);
        return gregorianCalendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

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

        agendaAdapter = new AgendaAdapter(
                this,
                agendaItems
        );

        agenda.setAdapter(
                agendaAdapter
        );
    }

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

    private void checkFirstVisibleHeader() {
        int firstCompletelyVisibleItemPosition =
                agendaLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
        AgendaItem agendaItem = agendaItems.get(firstCompletelyVisibleItemPosition);
        if (agendaItem instanceof AgendaHeader) {
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
    public void onDayPressed(CalendarDay pressedDay) {
        scrollAgendaToDate(
                pressedDay.getDate()
        );
        checkFirstVisibleHeader();
    }

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

    public void setHighlightedDay(Date date) {
        gregorianCalendar.setTime(date);

        int year = getYear(date);
        int month = getMonth(date);
        int day = getDay(date);

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

    public void setHighlightedDay(CalendarDay calendarDay) {
        calendarDay.setHighlighted(true);

        if (previousHighlightedDay != null) {
            previousHighlightedDay.setHighlighted(false);
        }

        previousHighlightedDay = calendarDay;
    }

}
