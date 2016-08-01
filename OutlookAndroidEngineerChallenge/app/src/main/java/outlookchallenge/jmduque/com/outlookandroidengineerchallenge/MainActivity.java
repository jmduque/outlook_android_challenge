package outlookchallenge.jmduque.com.outlookandroidengineerchallenge;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnhiott.darkskyandroidlib.ForecastApi;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.DataBlock;
import com.johnhiott.darkskyandroidlib.models.DataPoint;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import app.dinus.com.itemdecoration.PinnedHeaderDecoration;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.controller.AgendaWeatherInfoController;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.controller.CalendarMonthController;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaEvent;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaHeader;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaItem;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaNoEvent;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.AgendaWeatherInfo;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarDay;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.models.CalendarMonth;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters.AgendaAdapter;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.ui.adapters.CalendarAdapter;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.CollectionUtils;
import outlookchallenge.jmduque.com.outlookandroidengineerchallenge.utils.DateTimeUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Jose Duque
 *         This is the main activity of the app.
 */
public class MainActivity
        extends
        AppCompatActivity
        implements
        View.OnClickListener,
        CalendarDay.DaySelector,
        Callback<WeatherResponse> {

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

    private static final int REQUEST_LOCATION_PERMISSION = 0;

    private final Calendar gregorianCalendar = new GregorianCalendar();

    private View clParent;

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
    private ItemTouchHelper calendarItemTouchHelper;

    //Calendar Month Picker
    private View monthPicker;
    private TextView monthPickerName;
    private ImageView monthPickerArrow;

    //ACTIVITY VIEWS & DATA
    private FloatingActionButton fab;

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        ForecastApi.create("893949a9f8918d134755c749b3bd0ee8");

        setTitle(null);
        initAgendaDemoData();
        initCalendarDemoData();
        bindViews();
        setListeners();

        requestLastKnownLocation();
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
        //Init activity's main layout
        setContentView(R.layout.activity_main);

        //Find root coordinator layout
        clParent = findViewById(R.id.cl_parent);

        //Init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init calendar & agenda related views
        initCalendarViews();
        initAgendaViews();
        initMonthPickerViews();

        //Scroll to today's date
        scrollAgendaToDate(
                new Date()
        );

        //Init add button
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    /**
     * This method generates and initializes the views for the calendar
     */
    private void initCalendarViews() {
        calendar = (RecyclerViewPager) findViewById(R.id.rv_calendar);
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

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper
                .SimpleCallback(
                0,
                ItemTouchHelper.UP
        ) {
            @Override
            public boolean onMove(
                    RecyclerView recyclerView,
                    RecyclerView.ViewHolder viewHolder,
                    RecyclerView.ViewHolder target
            ) {
                return false;
            }

            @Override
            public void onSwiped(
                    RecyclerView.ViewHolder viewHolder,
                    int direction
            ) {
                hideCalendar();
            }
        };

        calendarItemTouchHelper = new ItemTouchHelper(simpleCallback);
        calendarItemTouchHelper.attachToRecyclerView(calendar);

        calendar.setAdapter(
                calendarAdapter
        );
    }

    /**
     * This method generates and initializes the views for the agenda
     */
    private void initAgendaViews() {
        agenda = (RecyclerView) findViewById(R.id.rv_agenda);
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
     * Init views related to the month picker view
     */
    private void initMonthPickerViews() {
        monthPicker = findViewById(R.id.ll_month_picker);
        monthPickerName = (TextView) findViewById(R.id.tv_month_picker_name);
        monthPickerArrow = (ImageView) findViewById(R.id.iv_month_picker_arrow);
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
        agenda.setOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(
                            RecyclerView recyclerView,
                            int dx,
                            int dy
                    ) {
                        super.onScrolled(
                                recyclerView,
                                dx,
                                dy
                        );
                        checkFirstVisibleHeader();
                    }
                }
        );
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

                        monthPickerName.setText(
                                item.getMonthName()
                        );
                    }
                }
        );
        monthPicker.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    /**
     * Checks which is the first view visible within the agenda.
     * If first visible item is an AgendaHeader, we notify a new header has been created
     */
    private void checkFirstVisibleHeader() {
        int firstCompletelyVisibleItemPosition = agendaLinearLayoutManager
                .findFirstCompletelyVisibleItemPosition();
        AgendaItem agendaItem = agendaItems
                .get(firstCompletelyVisibleItemPosition);

        //We only take action if first visible item is an AgendaHeader
        if (agendaItem instanceof AgendaHeader) {
            //If only take action if last-detected header is different from newly detected one
            if (firstVisibleAgendaHeader == agendaItem) {
                return;
            }

            firstVisibleAgendaHeader = (AgendaHeader) agendaItem;
            onNewHeaderDate(
                    agendaItem.getDate()
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(
            Menu menu
    ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater()
                .inflate(
                        R.menu.menu_main,
                        menu
                );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(
            MenuItem item
    ) {
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

    private void handleFabClick(
            @Nullable View view
    ) {
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

    /**
     * Handles the action of the user pressing the month picker
     *
     * @param view the clicked one
     */
    private void handleMonthPickerClick(
            @Nullable View view
    ) {
        if (calendar.getVisibility() == View.VISIBLE) {
            hideCalendar();
        } else {
            showCalendar();
        }
    }

    /**
     * Changes calendar view to hidden status
     */
    private void hideCalendar() {
        calendar.setVisibility(View.GONE);
        //Animates arrow to make it face down again
        Animation animation = AnimationUtils.loadAnimation(
                this,
                R.anim.half_rotation
        );

        animation.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(
                            Animation animation
                    ) {

                    }

                    @Override
                    public void onAnimationEnd(
                            Animation animation
                    ) {
                        monthPickerArrow.setImageResource(
                                R.drawable.ic_expand_more_white_24dp
                        );
                    }

                    @Override
                    public void onAnimationRepeat(
                            Animation animation
                    ) {

                    }
                }
        );

        monthPickerArrow.startAnimation(
                animation
        );
    }

    /**
     * Changes calendar view to visible status
     */
    private void showCalendar() {
        calendar.setVisibility(View.VISIBLE);
        calendarAdapter.notifyDataSetChanged();

        //Need to reattach to avoid last hidden viewHolder to stay hidden
        calendarItemTouchHelper.attachToRecyclerView(null);
        calendarItemTouchHelper.attachToRecyclerView(calendar);

        //Animates the arrow to make it face up
        Animation animation = AnimationUtils.loadAnimation(
                this,
                R.anim.half_rotation
        );

        animation.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(
                            Animation animation
                    ) {

                    }

                    @Override
                    public void onAnimationEnd(
                            Animation animation
                    ) {
                        monthPickerArrow.setImageResource(
                                R.drawable.ic_expand_less_white_24dp
                        );
                    }

                    @Override
                    public void onAnimationRepeat(
                            Animation animation
                    ) {

                    }
                }
        );

        monthPickerArrow.startAnimation(
                animation
        );
    }

    @Override
    public void onClick(
            View view
    ) {
        switch (view.getId()) {
            case R.id.fab: {
                handleFabClick(view);
                break;
            }
            case R.id.ll_month_picker: {
                handleMonthPickerClick(view);
                break;
            }
        }
    }

    @Override
    public void onDayPressed(
            CalendarDay pressedDay
    ) {
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
    public void scrollAgendaToDate(
            Date date
    ) {
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
    public void scrollCalendarToDate(
            Date date
    ) {
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
    public void setHighlightedDay(
            Date date
    ) {
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
    public void setHighlightedDay(
            CalendarDay calendarDay
    ) {
        calendarDay.setHighlighted(true);

        if (previousHighlightedDay != null) {
            previousHighlightedDay.setHighlighted(false);
        }

        previousHighlightedDay = calendarDay;
    }

    /**
     * Check if permission for location has been granted. Both COARSE and FINE location
     * are requested
     *
     * @param permissionRequest will be used as a request code in case of user granting permission
     */
    private boolean mayAccessLocation(final int permissionRequest) {
        //Only needed after Android 6.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        //Check if permission has been granted already
        int coarseLocationSelfPermission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        );
        if (coarseLocationSelfPermission != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            requestCoarseLocation(permissionRequest);
            return false;
        }

        int fineLocationSelfPermission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        );
        if (fineLocationSelfPermission != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            requestFineLocation(permissionRequest);
            return false;
        }

        //If all location permissions available, return true
        return true;
    }

    /**
     * Request permission to the user/system to use coarse location
     *
     * @param permissionRequest tracker for the request
     */
    private void requestCoarseLocation(final int permissionRequest) {
        //Only needed after Android 6.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        //Check if should ask permission to the user
        if (shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_COARSE_LOCATION
        )) {
            Snackbar.make(
                    clParent,
                    R.string.oaec_main_allow_location_access,
                    Snackbar.LENGTH_INDEFINITE
            ).setAction(
                    android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    permissionRequest
                            );
                        }
                    }
            );
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    permissionRequest
            );
        }
    }

    /**
     * Request permission to the user/system to use coarse location
     *
     * @param permissionRequest tracker for the request
     */
    private void requestFineLocation(final int permissionRequest) {
        //Only needed after Android 6.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        //Check if should ask permission to the user
        if (shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION
        )) {
            Snackbar.make(
                    clParent,
                    R.string.oaec_main_allow_location_access,
                    Snackbar.LENGTH_INDEFINITE
            ).setAction(
                    android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    permissionRequest
                            );
                        }
                    }
            );
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    permissionRequest
            );
        }
    }

    /**
     * Uses google services to request the latest known position of the user.
     * If user has no ACCESS_COARSE_LOCATION permission granted (and didn't deny it before),
     * it will ask the user for it.
     * Once latest known non-null location has ben retrieved, we will try to get weather data
     */
    private void requestLastKnownLocation() {
        //Can't request location if user didn't grant location permissions
        if (!mayAccessLocation(REQUEST_LOCATION_PERMISSION)) {
            return;
        }

        LocationManager locationManager =
                (LocationManager) getSystemService(
                        Context.LOCATION_SERVICE
                );

        try {
            Location lastKnownLocation = locationManager.getLastKnownLocation(
                    LocationManager.NETWORK_PROVIDER
            );
            onLocationChanged(lastKnownLocation);
        } catch (SecurityException ignored) {
            //Permission check happens within the mayAccessLocation method,
            //but we still need an exception catcher to avoid lint-errors
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                if (grantResults.length == 1 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLastKnownLocation();
                }
                break;
            }
        }
    }

    @Override
    public void success(
            WeatherResponse weatherResponse,
            Response response
    ) {
        DataBlock dailyData = weatherResponse.getHourly();
        List<DataPoint> nextTwoDays = dailyData.getData();
        //We will use the weather as follows:
        // 10:00 -> Morning
        // 14:00 -> Afternoon
        // 18:00 -> Night
        for (int i = 0; i < CollectionUtils.size(nextTwoDays); i++) {
            DataPoint dataPoint = nextTwoDays.get(i);
            //Creates the object, if not to be visualized, we will get a null object
            AgendaWeatherInfo item = AgendaWeatherInfoController.parseDataPoint(
                    this,
                    dataPoint
            );
            //Add item to agenda
            addAgendaWeatherInfoToAgenda(item);
        }
    }

    /**
     * Adds an <class>AgendaWeatherInfo</class> to the expected position in the list of agenda
     * items. Notice that the expectation is that this method will be called for morning,
     * then afternoon, then evening ones.
     *
     * @param item the weather information to be added to agenda
     */
    private void addAgendaWeatherInfoToAgenda(
            AgendaWeatherInfo item
    ) {
        if (item == null) {
            return;
        }
        String dayOfTheYear = DateTimeUtils
                .dayOfTheYear
                .format(
                        item.getDate()
                );

        AgendaHeader agendaHeader = (AgendaHeader) agendaHeaders.get(
                dayOfTheYear
        );
        int position = agendaItems.indexOf(agendaHeader);
        int addPosition = position + 1;
        AgendaItem nextItem;
        do {
            nextItem = agendaItems.get(addPosition);
            if (nextItem instanceof AgendaNoEvent) {
                //If this had no events, remove the no events holder
                agendaItems.remove(addPosition);
                agendaAdapter.notifyItemRemoved(addPosition);
            } else if (nextItem instanceof AgendaHeader) {
                //If found next header, we use next header position to place the weather info
                //We are gonna place all weather events at the bottom of views related to the
                //weather info dates
                break;
            } else {
                //Keep searching
                addPosition++;
            }
        } while (true);
        agendaItems.add(
                addPosition,
                item
        );
        agendaAdapter.notifyItemInserted(addPosition);
    }

    @Override
    public void failure(
            RetrofitError error
    ) {

    }

    /**
     * Handles the success of retrieving the user's last known location and requests weather data
     * according to user's location
     *
     * @param location current GeoLocation of the user
     */
    public void onLocationChanged(
            Location location
    ) {
        //Can't do anything if user doesn't have location yet
        if (location == null) {
            return;
        }

        RequestBuilder requestBuilder = new RequestBuilder();
        Request request = new Request();
        request.setLat(
                String.valueOf(
                        location.getLatitude()
                )
        );
        request.setLng(
                String.valueOf(
                        location.getLongitude()
                )
        );
        //I like the international metric system :D
        request.setUnits(
                Request.Units.SI
        );
        request.setLanguage(
                Request.Language.ENGLISH
        );
        //We only need the data hourly since we need to display
        //morning, afternoon and night weather
        request.addExcludeBlock(
                Request.Block.DAILY
        );
        request.addExcludeBlock(
                Request.Block.MINUTELY
        );
        request.addExcludeBlock(
                Request.Block.ALERTS
        );
        request.addExcludeBlock(
                Request.Block.CURRENTLY
        );
        request.addExcludeBlock(
                Request.Block.FLAGS
        );
        requestBuilder.getWeather(
                request,
                this
        );
    }
}